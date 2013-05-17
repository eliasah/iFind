package MoteurDeRecherche;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.StyledEditorKit;

 
public class GUI implements Runnable{

	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new GUI());

		
	}

	// la GUI se définit comme telle :
    // une interface de base contenant une menubar habituelle avec fichier, edition et aide... de plus on a un champ de recherche de type textfield et un bouton (rechercher), ainsi qu'un bouton advanced  qui permet d afficher l interface de l utilisateur avancé. Il y a egalement une JTable qui fait office de tableau des résultats de la recherche.
    
	public void run() {
		final JFrame frame = new JFrame("Moteur de Recherche");
		final JPanel panel = new JPanel();
		frame.setContentPane(panel);
		BorderLayout layout = new BorderLayout();
		panel.setLayout(layout);
 		
		JPanel panel_north = new JPanel();
		panel.add(panel_north,BorderLayout.NORTH);
		
		final JPanel user_advanced = new JPanel();
		
		JLabel searchAdvLabel = new JLabel("Une partie ou l'ensemble du nom du document : ");
		JTextField searchAdv = new JTextField();
 		JCheckBox att1 = new JCheckBox("Auteur");
		JCheckBox att2 = new JCheckBox("Date");
		JCheckBox att3 = new JCheckBox("Type");
		JCheckBox att4 = new JCheckBox("Contenu");
		
		user_advanced.add(searchAdvLabel,BorderLayout.EAST);
		user_advanced.add(searchAdv,BorderLayout.CENTER);
		
		
		JPanel UAdv_sub = new JPanel(new GridLayout(2,2));
		user_advanced.add(UAdv_sub,BorderLayout.CENTER); // Probleme de BorderLayout pas a la bonne place... car textfield presk invible
		UAdv_sub.add(att1);
		UAdv_sub.add(att2);
		UAdv_sub.add(att3);
		UAdv_sub.add(att4);

		
 
		
		
 		frame.setResizable(true);
		frame.setBounds(100, 100, 910, 610);
		
		JMenuBar mbar = new JMenuBar();
		JMenu menu = new JMenu("Fichier");
		JMenuItem open = new JMenuItem("Ouvrir...");
		JMenuItem save = new JMenuItem("Enregistrer...");
		JMenuItem quit = new JMenuItem("Quitter");
		menu.add(open);
		menu.add(save);
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
		
		
				
		JLabel search = new JLabel("Une partie ou l'ensemble du nom du document : ");
		panel_north.add(search,BorderLayout.NORTH);
		
		JTextField searchField = new JTextField();
		searchField.setColumns(20);
		panel_north.add(searchField,BorderLayout.NORTH);
		
	
		
		
		JButton envoyer = new JButton("Rechercher");
		panel_north.add(envoyer,BorderLayout.CENTER);

	
		// Le probleme de la JTable() c'est qu'on peut pas la centrer dans le conteneur, elle ecrase tout.

		
		final String[] entetes = {"Nom ","Auteur","Date de Creation","Type"};
		  final Object [][] donnees = new Object[][]{
	                {"Jonathan", "Sykes", "01/01/2013", true},
	                {"Nicolas", "Van de Kampf", "01/01/2013", true},
	                {"Damien", "Cuthbert", "01/01/2013", true},
	                {"Corinne", "Valance", "01/01/2013", false},
	                {"Emilie", "Schrödinger", "01/01/2013",true},
	                {"Delphine", "Duke", "01/01/2013", false},
	                {"Eric", "Trump", "01/01/2013", true},
	        };
		  
	       TableModel dataModel = new AbstractTableModel() {
	           public int getColumnCount() { return entetes.length; }
	           public String getColumnName(int columnIndex) { return entetes[columnIndex];}
	           public Object getValueAt(int row, int col) { return donnees[row][col]; }
			@Override
			public int getRowCount() {
			
				return donnees.length;
			}
 			
		
	     };
	       
	       JTable table = new JTable(dataModel);
	       table.setPreferredSize(new Dimension(100,100));
	
			user_advanced.add(table);

	     
 	       JButton advanced = new JButton("Utilisateur Avancé ?");
 	       panel_north.add(advanced,BorderLayout.SOUTH);
 	       
 	       advanced.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				panel.setVisible(false);
				frame.setContentPane(user_advanced);
				
			}
		});
	       
	       panel.add(table,BorderLayout.CENTER);
	         
		
		envoyer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// connexion a la BDD
				
				JOptionPane.showMessageDialog(frame, "Erreur : \nLe Moteur n'arrive pas à joindre la BDD.\nVeuillez vérifiez vos connexions.");
 			}
			
		});
		
		// La connexion a la BD a lieu dans le moteur de recherche (fichiers Serveur_Mdr / ServerThread )
        
        // l'interface n'est pas finie.


		
       

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

}
