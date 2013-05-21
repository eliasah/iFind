
package MoteurDeRecherche;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;


public class User_Advanced implements Runnable{

	 JFrame frame;
	 JPanel master_panel;
	 JPanel top_panel;
	 JPanel mid_panel;
	 JPanel empty_panel;
	 JPanel field_panel;
	 JPanel button_panel;
	 JPanel bot_panel;
	
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
		
		GridLayout grid_mid = new GridLayout(5,2);
		grid_mid.setVgap(11);
		grid_mid.setHgap(17);
		
		
		top_panel = new JPanel(new BorderLayout());
		
		mid_panel = new JPanel(new BorderLayout());
		field_panel = new JPanel(grid_mid);
		button_panel = new JPanel(new FlowLayout());
		empty_panel = new JPanel(new FlowLayout());
		
		bot_panel = new JPanel(new BorderLayout());
		
		master_panel.add(top_panel,BorderLayout.NORTH);
		master_panel.add(mid_panel,BorderLayout.CENTER);
		master_panel.add(bot_panel,BorderLayout.SOUTH);
		
		JLabel vide3 = new JLabel("");
		empty_panel.add(vide3);
		
		mid_panel.add(empty_panel,BorderLayout.NORTH);
		mid_panel.add(field_panel,BorderLayout.CENTER);
		mid_panel.add(button_panel,BorderLayout.SOUTH);
		
		
		// Top Panel : Presentation de l'interface avancée
		
		top_panel.setBackground(Color.LIGHT_GRAY);
		JLabel lab = new JLabel("Bienvenue sur l'interface avancée.");
		JLabel lab2 = new JLabel("Vous avez la possibilité d'ajouter plusieurs critères à votre recherche.");
		JLabel lab3 = new JLabel("Appuyez sur le bouton pour lancer la recherche.");
 		
 		lab.setHorizontalAlignment(JLabel.CENTER);
 		top_panel.add(lab,BorderLayout.NORTH);
		
 		lab2.setHorizontalAlignment(JLabel.CENTER);
 		top_panel.add(lab2,BorderLayout.CENTER);
		
 		lab3.setHorizontalAlignment(JLabel.CENTER);
 		top_panel.add(lab3,BorderLayout.SOUTH);
				
 		 
 		// Mid Panel : Champs de recherche et bouton 
 		
 		JLabel lab4 = new JLabel("Une partie ou l'ensemble du fichier.");
 		JLabel lab5 = new JLabel("Type");
 		JLabel lab6 = new JLabel("Date de Création");
 		JLabel lab7 = new JLabel("Contenu");
 		
 		lab4.setHorizontalAlignment(JLabel.CENTER);
 		lab5.setHorizontalAlignment(JLabel.CENTER);
 		lab6.setHorizontalAlignment(JLabel.CENTER);
 		lab7.setHorizontalAlignment(JLabel.CENTER);

 		
 		JTextField field4 = new JTextField();
 		JTextField field5 = new JTextField();
 		JTextField field6 = new JTextField();
 		JTextField field7 = new JTextField();
 		
 		field4.setColumns(10);
 		
 		field_panel.add(lab4);
 		field_panel.add(lab5);

 		field_panel.add(field4);
 		field_panel.add(field5);
		
 		field_panel.add(lab6);
 		field_panel.add(lab7);

 		field_panel.add(field6);
 		field_panel.add(field7);
 	
 		
  		JButton button = new JButton("Rechercher");
 		
  		button_panel.add(button);
 		
 		
 		
 		
 		
 		
 		
 		// bot Panel : Tableau des résultats
 		
 		final String[] entetes = {"Nom ","Auteur","Date de Creation","Type"};
		  final Object [][] donnees = new Object[][]{
	                {"Nom", "Auteur", "Date de Création", "Type"},
	                {" ", " ", " ", " "},
	                {"Nicolas", "Van de Kampf", "01/01/2013", true},
	                {"Damien", "Cuthbert", "01/01/2013", true},
	                {"Corinne", "Valance", "01/01/2013", false},
	                {"Emilie", "Schrödinger", "01/01/2013",true},
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
	       
	     //creation de la Jtable contenant le tableau des résultats
	     
	       JTable table = new JTable(dataModel);
	      
	       table.setPreferredSize(new Dimension(810,160));
	       
	       JLabel vide2 = new JLabel("");
	       bot_panel.add(vide2,BorderLayout.NORTH);
	       bot_panel.add(table,BorderLayout.CENTER);
	       
 		
 		
 		
		frame.pack();
		frame.setBounds(190,100,910,610);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}

}
