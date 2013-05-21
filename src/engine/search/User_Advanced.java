
package MoteurDeRecherche;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/*
 * Auteur : Rouach Jérémie
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
 		
		
		// Top Panel : Presentation de l'interface avancée
		
		top_panel.setBackground(Color.LIGHT_GRAY);
		JLabel lab = new JLabel("                          ");
		JLabel lab2 = new JLabel("Bienvenue sur l'interface avancée du logiciel iFind.");
		JLabel lab3 = new JLabel("                         ");
 		
 		lab.setHorizontalAlignment(JLabel.CENTER);
 		top_panel.add(lab,BorderLayout.NORTH);
		
 		lab2.setHorizontalAlignment(JLabel.CENTER);
 		top_panel.add(lab2,BorderLayout.CENTER);
		
 		lab3.setHorizontalAlignment(JLabel.CENTER);
 		top_panel.add(lab3,BorderLayout.SOUTH);
				
 		 
 		
 		
 		
 		// Mid Panel : Champs de recherche et bouton 
 		
 		JLabel lab4 = new JLabel("Une partie ou l'ensemble du fichier.");
 		JLabel lab5 = new JLabel("Chemin d'Accès");
 		JLabel lab6 = new JLabel("Date de Création");
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

 	
 		
  		
  		
  		
  		

 		// bot Panel : Tableau des résultats
		
		
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
 			   	
 			   //	tabS[0]= new Search(compteur,tab[0],false,chemin,null,extension,null);
 			   	
 			   	for(int i=0;i<tabS.length;i++){
 	 			   	tabS[i]= new Search(compteur,tab[i],false,chemin,null,extension,null);
 			   	}
 			   	
				JOptionPane.showMessageDialog(frame, "Envoi des informations.\nVeuillez patientez s'il vous plaît.");

 			   	
				//SearchClient des que Mik a fini.
 			   	// puis zou
				
				
			}
		});
	   	

	   
 		
		frame.pack();
		frame.setBounds(190,100,910,762);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}

}
