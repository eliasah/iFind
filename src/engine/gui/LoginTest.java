package engine.gui;

import javax.swing.*;

import database.proxy.Database;
import database.proxy.Proxy_PgSQL;
import database.trigram.Trigram;
import engine.search.Search;
import engine.search.TimeSlot;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

class Login extends JFrame implements ActionListener {
	JButton connect,quit;
	JPanel panel;
	JLabel label1,label2;
	final JTextField  text1,text2;

	Login() {
		label1 = new JLabel();
		label1.setText("Username:");
		text1 = new JTextField(15);

		label2 = new JLabel();
		label2.setText("Password:");
		text2 = new JPasswordField(15);

		connect = new JButton("Connect");
		quit    = new JButton("Quit");
		
		panel   = new JPanel(new GridLayout(3,1));
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(connect);
		panel.add(quit);
		
		add(panel,BorderLayout.CENTER);
		connect.addActionListener(this);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		setTitle("LOGIN FORM");
	}

	public void actionPerformed(ActionEvent ae) {
		String value1=text1.getText();
		String value2=text2.getText();

		Database conn = new Proxy_PgSQL(value1, value2);

		if (conn.isconnected()) {
			System.out.println("Connection Established");
			NextPage page = new NextPage();
			page.setVisible(true);
			JLabel label = new JLabel("Connexion to database established, welcome "+value1);
			page.getContentPane().add(label);

			// conn.createDatabase();
			Search s = new Search(1, "exemple", true, "", "", "txt", new TimeSlot());
			Trigram t = new Trigram("exemple");
			//System.out.println(t.toString());
			conn.insert("exemple");
			conn.request(s);

		}
		else {	
			System.out.println("enter the valid username and password");
			JOptionPane.showMessageDialog(this,"Incorrect login or password","Error",JOptionPane.ERROR_MESSAGE);
		}
	
	}

}

class LoginTest {
	public static void main(String arg[]) {
		try {
			Login frame=new Login();
			frame.setSize(382,455);
			frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			JPanel panel = new JPanel();
			frame.getContentPane().add(panel);
			
			JButton btnCreateDatabase = new JButton("Create Database");
			btnCreateDatabase.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			panel.add(btnCreateDatabase);
			
			JButton btnResetDatabase = new JButton("Reset Database");
			panel.add(btnResetDatabase);
			frame.setVisible(true);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}