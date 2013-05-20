package engine.gui;

import java.awt.EventQueue;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import javax.swing.JLabel;

import database.proxy.Database;
import database.proxy.Proxy_PgSQL;
import database.trigram.Trigram;
import engine.search.Search;
import engine.search.TimeSlot;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginDB {

	private JFrame frmLogin;
	private JPasswordField pass;
	private JTextField user;
	private JLabel lbluser;
	private JLabel lblpass;
	private JButton quit;
	private JButton connect;
	private JButton create;
	private JButton disconnect;
	private Database conn;
	private Search search;
	private JButton btnDropDatabase;
	private JButton btnInsertTest;
	private JButton btnSearchTest;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginDB window = new LoginDB();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginDB() {
		initialize();
	}

	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 459, 234);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		lbluser = new JLabel("User");
		lblpass = new JLabel("Password");
		lbluser.setBounds(12, 12, 69, 20);
		lblpass.setBounds(12, 44, 97, 20);
		
		
		user = new JTextField();
		user.setBounds(119, 12, 196, 20);
		pass = new JPasswordField(15);
		pass.setBounds(119, 44, 196, 20);
				
		quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			System.exit(0);
			}
		});
		quit.setBounds(327, 44, 109, 20);
		
		connect = new JButton("Connect");
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value1 = user.getText();
				String value2 = pass.getText();

				conn = new Proxy_PgSQL(value1, value2);

				if (conn.isconnected()) {
					JOptionPane.showMessageDialog(frmLogin, "Connection Establish","Success",JOptionPane.INFORMATION_MESSAGE, null);
					create.setVisible(true);
					disconnect.setVisible(true);
					btnInsertTest.setVisible(true);
					btnSearchTest.setVisible(true);
					btnDropDatabase.setVisible(true);
				}
				else {	
					JOptionPane.showMessageDialog(frmLogin, "Incorrect login or password","Error",JOptionPane.ERROR_MESSAGE, null);
				}

			}
		});
		connect.setBounds(327,12,109,20);
		
		create = new JButton("Create Database");
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (conn.isconnected()) {
					conn.createDatabase();
					System.out.println("Database created");
				}
			}
		});
		create.setBounds(12,105,187,25);
		create.setVisible(false);

		disconnect = new JButton("Disconnect");
		disconnect.setBounds(12, 76, 187, 25);
		disconnect.setVisible(false);

		btnInsertTest = new JButton("Insert Test");
		btnInsertTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (conn.isconnected())
					conn.insert("exemple");
			}
		});
		btnInsertTest.setBounds(211, 76, 145, 25);
		btnInsertTest.setVisible(false);
		btnSearchTest = new JButton("Search Test");
		btnSearchTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (conn.isconnected())
					search = new Search(1, "exemple", true, "", "", "txt", new TimeSlot());
					conn.request(search);

			}
		});
		btnSearchTest.setBounds(211, 105, 145, 25);
		btnSearchTest.setVisible(false);
		
		btnDropDatabase = new JButton("Drop Database");
		btnDropDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (conn.isconnected())
					conn.resetDatabase();
			}
		});
		btnDropDatabase.setBounds(12, 132, 187, 25);
		btnDropDatabase.setVisible(false);
		
		frmLogin.getContentPane().add(lbluser);
		frmLogin.getContentPane().add(lblpass);
		frmLogin.getContentPane().add(user);
		frmLogin.getContentPane().add(pass);
		frmLogin.getContentPane().add(quit);
		frmLogin.getContentPane().add(connect);
		frmLogin.getContentPane().add(create);
		frmLogin.getContentPane().add(disconnect);
		frmLogin.getContentPane().add(btnInsertTest);
		frmLogin.getContentPane().add(btnSearchTest);
		
		frmLogin.getContentPane().add(btnDropDatabase);
	}
}
