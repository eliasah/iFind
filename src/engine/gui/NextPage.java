package engine.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NextPage extends JFrame {
	NextPage() {
		JButton quit = new JButton("Quit");
		quit.setBounds(277, 235, 109, 25);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Welcome");
		setSize(400, 301);
		getContentPane().setLayout(null);
		getContentPane().add(quit);
		
		JButton btnResetDatabase = new JButton("Reset Database");
		btnResetDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnResetDatabase.setBounds(151, 235, 117, 25);
		getContentPane().add(btnResetDatabase);
	}	
}