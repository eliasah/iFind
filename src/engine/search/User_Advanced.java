
package engine.search;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.StyledEditorKit;

/*
 * Auteur : Rouach Jeremie
 * 
 */


public class User_Advanced implements Runnable{

	 JFrame frame;
	 JPanel master_panel;
	 JPanel top_panel;
	 JPanel mid_panel;
	 JPanel empty_panel;
	 JPanel field_panel;
	 JPanel button_panel;
	 JPanel bot_panel;
	 
	 JTextField field4 ;
	 JTextField field5;
	 JTextField field6;
	 JTextField field7;
	 JTextField field8;
	 
	 ResultTable tableauRes;
	
	 String name;
	 String chemin;
	 String date;
	 String content;
	 String extension;
	 
	 Search searchObj ;
	 int compteur = 0;
	 
	 
	 
	public User_Advanced(){
		super();
	}
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new User_Advanced());

	}

	@Override
	public void run() {
		
		
		frame = new JFrame();
		master_panel = new JPanel(new BorderLayout());
		
		frame.setContentPane(master_panel);
		
		GridLayout grid_mid = new GridLayout(6,2);
		grid_mid.setVgap(11);
		grid_mid.setHgap(13);
		
		
		top_panel = new JPanel(new BorderLayout());
		
		mid_panel = new JPanel(new BorderLayout());
		field_panel = new JPanel(grid_mid);
		button_panel = new JPanel(new FlowLayout());
		empty_panel = new JPanel(new FlowLayout());
		
		bot_panel = new JPanel(new BorderLayout());
		
		
		top_panel.setBorder(new LineBorder(new Color(0,0,0)));
		mid_panel.setBorder(new LineBorder(new Color(0,0,0)));
		bot_panel.setBorder(new LineBorder(new Color(0,0,0)));
		
		
		master_panel.add(top_panel,BorderLayout.NORTH);
		master_panel.add(mid_panel,BorderLayout.CENTER);
		master_panel.add(bot_panel,BorderLayout.SOUTH);
		
		JLabel vide3 = new JLabel("");
		empty_panel.add(vide3);
		
		mid_panel.add(empty_panel,BorderLayout.NORTH);
		mid_panel.add(field_panel,BorderLayout.CENTER);
 		
		
		// Top Panel : Presentation de l interface avancee
		
		top_panel.setBackground(Color.LIGHT_GRAY);
		JLabel lab = new JLabel("                          ");
		JLabel lab2 = new JLabel("Bienvenue sur l interface avancee du logiciel iFind.");
		JLabel lab3 = new JLabel("                         ");
 		
 		lab.setHorizontalAlignment(JLabel.CENTER);
 		top_panel.add(lab,BorderLayout.NORTH);
		
 		lab2.setHorizontalAlignment(JLabel.CENTER);
 		top_panel.add(lab2,BorderLayout.CENTER);
		
 		lab3.setHorizontalAlignment(JLabel.CENTER);
 		top_panel.add(lab3,BorderLayout.SOUTH);
				
 		 
 		
 		
 		
 		// Mid Panel : Champs de recherche et bouton 
 		
 		JLabel lab4 = new JLabel("Une partie ou l ensemble du fichier.");
 		JLabel lab5 = new JLabel("Chemin d'Acces");
 		JLabel lab6 = new JLabel("Date de Creation");
 		JLabel lab7 = new JLabel("Contenu");
 		JLabel lab8 = new JLabel("Extension");
 		JLabel lab9 = new JLabel("");
 		
 		
 		JButton button = new JButton("Rechercher");
 		
  		button_panel.add(button);
 		
 		
 		
 		lab4.setHorizontalAlignment(JLabel.CENTER);
 		lab5.setHorizontalAlignment(JLabel.CENTER);
 		lab6.setHorizontalAlignment(JLabel.CENTER);
 		lab7.setHorizontalAlignment(JLabel.CENTER);
 		lab8.setHorizontalAlignment(JLabel.CENTER);
 		lab9.setHorizontalAlignment(JLabel.CENTER);

 		
 		field4 = new JTextField();
 		field5 = new JTextField();
 		field6 = new JTextField();
 		field7 = new JTextField();
 		field8 = new JTextField();
 		
 		field4.setColumns(10);
 		
 		field_panel.add(lab4);
 		field_panel.add(lab5);

 		field_panel.add(field4);
 		field_panel.add(field5);
		
 		field_panel.add(lab6);
 		field_panel.add(lab7);

 		field_panel.add(field6);
 		field_panel.add(field7);
 		
 		field_panel.add(lab8);
 		field_panel.add(lab9);
 		
 		field_panel.add(field8);
		field_panel.add(button);

 	
 		
  		
  		
  		
  		

 		// bot Panel : Tableau des resultats
		
		
		tableauRes = new ResultTable();
		FlowLayout flowLayout = (FlowLayout) tableauRes.getLayout();
		flowLayout.setAlignment(FlowLayout.CENTER);
		tableauRes.setBounds(190, 100, 535, 507);

		bot_panel.add(tableauRes);
		
		JButton switcher = new JButton("Utilisateur Basique ?");
		switcher.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				GUI now = new GUI();
				now.run();
			}
		});
		
		top_panel.add(switcher,BorderLayout.EAST);
		


	   	
	   	button.addActionListener(new ActionListener() {
			
	   		//	public Search(int i, String w, boolean cont, String path, String permission, String ext, TimeSlot t){

	   		
			
			public void actionPerformed(ActionEvent arg0) {
				compteur++;
				
				name = field4.getText();
			   	chemin = field5.getText();
			   	date = field6.getText();
			   	content = field7.getText();
			   	extension = field8.getText();
				
 			   	String[] tab = name.split(" ");
 			   	Search[] tabS = new Search[tab.length];
 			   	
  			   	
 			   	for(int i=0;i<tabS.length;i++){
 	 			   	tabS[i]= new Search(compteur,tab[i],false,chemin,null,extension,null);
 			   	}
 			   	
				JOptionPane.showMessageDialog(frame, "Envoi des informations.\nVeuillez patientez s il vous plait.");
 			  				
				
			}
		});
	   	
JMenuBar mbar = new JMenuBar();
		
		JMenu menu = new JMenu("Fichier");
		
  		JMenuItem quit = new JMenuItem("Quitter");
		
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
				JOptionPane.showMessageDialog(frame, "Le logiciel iFind permet de rechercher un fichier dans un ensemble de repertoires " +
						"ciblees du systeme.\nCette recherche peut se faire soit en indiquant le nom du fichier, soit en " +
						"donnant une liste de mots contenus dans ce fichier.");
			}
		});
		
		version.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Version alpha : 05/2013  \n Tous droits reserv�s.");
			}
		});
				
		 
		
		// Gestion du menu quitter

		
		
		quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		 
		mbar.add(menu);
		mbar.add(menu2);
		mbar.add(menu3);
		frame.setJMenuBar(mbar);
 		
		frame.pack();
		frame.setBounds(190,100,910,762);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}

}
