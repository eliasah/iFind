package MoteurDeRecherche;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.StyledEditorKit;

public class GUI implements Runnable{
	final JFrame frame;
	final JPanel panel;
	BorderLayout layout;
	
	JPanel panel_north;
	final JPanel user_advanced;
	JPanel UAdv_sub;
	
	
	
	public GUI (){
		
		frame = new JFrame("Moteur de Recherche Simple");
		panel = new JPanel();
		frame.setContentPane(panel);
		
		layout = new BorderLayout();
		panel.setLayout(layout);
 		
		panel_north = new JPanel();
		panel.add(panel_north,BorderLayout.NORTH);
		
		
		user_advanced = new JPanel(new BorderLayout());
		
 		frame.setResizable(true);
		frame.setBounds(200, 100, 910, 610);
		
		
		// Creation de la barre de menu avec tous les sous menu
		
		JMenuBar mbar = new JMenuBar();
		
		JMenu menu = new JMenu("Fichier");
		
		JMenuItem open = new JMenuItem("Ouvrir...");
 		JMenuItem quit = new JMenuItem("Quitter");
		
 		menu.add(open);
 		menu.add(quit);
		
		
		JMenu menu3 = new JMenu("Aide");
		
		JMenuItem apropos = new JMenuItem("A Propos");
		JMenuItem version = new JMenuItem("Version");
 		
		menu3.add(apropos);
		menu3.add(version);
		
		JMenu menu2 =new JMenu("Edition");
		 
		
		// Gestion automatique du menu d Edition : couper/copier/coller
		
		Action copier=new StyledEditorKit.CopyAction();
		Action coller = new StyledEditorKit.PasteAction();
		Action couper = new StyledEditorKit.CutAction();
 		
		//puis ajout
		
		menu2.add(copier);
		menu2.add(coller);
		menu2.add(couper);
		
		//Gestion des menus a propos, version
		
		apropos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "Le logiciel iFind permet de rechercher un fichier dans un ensemble de repertoires ciblees du systeme.\nCette recherche peut se faire soit en indiquant le nom du fichier, soit en donnant une liste de mots contenus dans ce fichier.");
			}
		});
		
		version.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Version alpha : 05/05/2013  \n Tous droits reserves.");
			}
		});
				
		 
		
		// Gestion des menus ouvrir, enregistrer et fermer
		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				if(arg0.getActionCommand() == "Ouvrir..."){
					int returnVal = jfc.showOpenDialog(jfc);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = jfc.getSelectedFile();  
			        } 
				}
			}
		});
		
		
		quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		 
		
		
		
		//Label de presentation du TxtField + TxtField	qui appartiennent au panel_north
		
		JLabel search = new JLabel("Une partie ou l'ensemble du nom du document : ");
		panel_north.add(search,BorderLayout.NORTH);
		
		JTextField searchField = new JTextField();
		searchField.setColumns(20);
		
		panel_north.add(searchField,BorderLayout.NORTH);
		
		// bouton pour lancer la recherche
		
		
		JButton envoyer = new JButton("Rechercher");
		panel_north.add(envoyer,BorderLayout.CENTER);

	
		//tableau de donn�es de la JTable
		
		final String[] entetes = {"Nom ","Auteur","Date de Creation","Type"};
		  final Object [][] donnees = new Object[][]{
	                {"Nom", "Auteur", "Date de Cr�ation", "Type"},
	                {" ", " ", " ", " "},
	                {"Nicolas", "Van de Kampf", "01/01/2013", true},
	                {"Damien", "Cuthbert", "01/01/2013", true},
	                {"Corinne", "Valance", "01/01/2013", false},
	                {"Emilie", "Schr�dinger", "01/01/2013",true},
	                {"Delphine", "Duke", "01/01/2013", false},
	                {"Eric", "Trump", "01/01/2013", true},
	        };
		  
		  //creation de la structure de la table
	       TableModel dataModel = new AbstractTableModel() {
	           public int getColumnCount() { return entetes.length; }
	           public String getColumnName(int columnIndex) { return entetes[columnIndex];}
	           public Object getValueAt(int row, int col) { return donnees[row][col]; }
			@Override
			public int getRowCount() {
			
				return donnees.length;
			}
 			
		
	     };
	       
	     //creation de la Jtable
	       JTable table = new JTable(dataModel);
	       table.setPreferredSize(new Dimension(100,100));
	
	       //bouton pour passer a l interface avanc�e
	       
 	       JButton advanced = new JButton("Utilisateur Avanc� ?");
 	       panel_north.add(advanced,BorderLayout.SOUTH);
 	       
 	       advanced.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				panel.setVisible(false);
				User_Advanced u = new User_Advanced();
				u.run();
				frame.setVisible(false);
 				//frame.setContentPane();
				
			}
		});
	       
	       panel.add(table,BorderLayout.CENTER);
	         
		
		envoyer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// connexion a la BDD
				
				JOptionPane.showMessageDialog(frame, "Erreur : \nLe Moteur n'arrive pas � joindre la BDD.\nVeuillez v�rifiez vos connexions.");
 			}
			
		});
		
 

		
       

		mbar.add(menu);
		mbar.add(menu2);
		mbar.add(menu3);

		//frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.setJMenuBar(mbar);		
		
 		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void GUI_Adv(){
		
		frame.setVisible(false);// disparition de l interface simple
	/*	
		JFrame advFrame = new JFrame("Interface Avanc�e");
		
		advFrame.setBounds(100,100,910,610);
		advFrame.setResizable(true);
		
		JMenuBar mbar = new JMenuBar();
		JMenu menu = new JMenu("Fichier");
		JMenuItem open = new JMenuItem("Ouvrir...");
 		JMenuItem quit = new JMenuItem("Quitter");
		menu.add(open);
 		menu.add(quit);
		
		
		JMenu menu3 = new JMenu("Aide");
		JMenuItem apropos = new JMenuItem("A Propos");
		JMenuItem version = new JMenuItem("Version");
 		menu3.add(apropos);
		menu3.add(version);
		
		JMenu menu2 =new JMenu("Edition");
		 
		advFrame.add(mbar);
		
		// Gestion automatique du menu d Edition : couper/copier/coller
		Action copier=new StyledEditorKit.CopyAction();
		Action coller = new StyledEditorKit.PasteAction();
		Action couper = new StyledEditorKit.CutAction();
 		
		//puis ajout
		menu2.add(copier);
		menu2.add(coller);
		menu2.add(couper);
		
		//Gestion des menus a propos, version
		
		apropos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "Le logiciel iFind permet de rechercher un fichier dans un ensemble de repertoires ciblees du systeme.\nCette recherche peut se faire soit en indiquant le nom du fichier, soit en donnant une liste de mots contenus dans ce fichier.");
			}
		});
		
		version.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Version alpha : 05/05/2013  \n Tous droits reserves.");
			}
		});
				
		 
		
		// Gestion des menus ouvrir, enregistrer et fermer
		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				if(arg0.getActionCommand() == "Ouvrir..."){
					int returnVal = jfc.showOpenDialog(jfc);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = jfc.getSelectedFile();  
			        } 
				}
			}
		});
		
		
		quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
	
		//ajout de la barre de menu a advFrame
		advFrame.setJMenuBar(mbar);		

		/*
		JLabel searchAdvLabel = new JLabel("Une partie ou l'ensemble du nom du document : ");
		JTextField searchAdv = new JTextField();
		searchAdv.setColumns(10);
 		
		
		JCheckBox att1 = new JCheckBox("Auteur");
		JCheckBox att2 = new JCheckBox("Date");
		JCheckBox att3 = new JCheckBox("Type");
		JCheckBox att4 = new JCheckBox("Contenu"); */
		
	 
		
		
	/*	UAdv_sub = new JPanel(new GridLayout(2,2));
		user_advanced.add(UAdv_sub,BorderLayout.NORTH);  
		UAdv_sub.add(att2);
		UAdv_sub.add(att3);
		UAdv_sub.add(att4); */

		
		
	/*	JPanel between = new JPanel();
		BorderLayout layout_between = new BorderLayout();
		between.setLayout(layout_between);
		frame.add(between,BorderLayout.CENTER); */
	}
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new GUI());
		 
		
	}

	@Override
	public void run() {
		
		
		
	}

}
