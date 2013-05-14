package MoteurDeRecherche;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

public class GUI {

	
	public static void main(String[] args) {

		final JFrame frame = new JFrame("Moteur de Recherche");
		JPanel panel = new JPanel();
		
		BorderLayout layout = new BorderLayout();
		panel.setLayout(layout);
		frame.add(panel);
		
		JPanel panel_north = new JPanel();
		panel.add(panel_north,BorderLayout.NORTH);
		
		frame.setResizable(false);
		frame.setBounds(100, 100, 710, 610);
		
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
		
		// pour save, je me tate en fait, parce que la on est dans un moteur de recherche, donc on cherche des docs
		// et si on les trouve, on les ouvre, sinon bah on recommence avec une nouvelle indexation mais dans aucun cas 
		// on a besoin d'enregistrer le resultat... ex : Terminal...
		
				
		JLabel search = new JLabel("Une partie ou l'ensemble du nom du document : ");
		panel_north.add(search,BorderLayout.NORTH);
		
		JTextField searchField = new JTextField();
		searchField.setColumns(20);
		panel_north.add(searchField,BorderLayout.NORTH);
		
		JButton envoyer = new JButton("Rechercher");
		panel_north.add(envoyer,BorderLayout.CENTER);

	
		// Le probleme de la JTable() c'est qu'on peut pas la centrer dans le conteneur, elle ecrase tout.

		
		final String[] entetes = {"Nom ","Auteur","Date de Création","Type","Taille"};
	       TableModel dataModel = new AbstractTableModel() {
	           public int getColumnCount() { return entetes.length; }
	           public String getColumnName(int columnIndex) { return entetes[columnIndex];}
	           public Object getValueAt(int row, int col) { return new Integer(row*col); }
			@Override
			public int getRowCount() {
			
				return 0;
			}
 			
		
	     };
	       
	       
	      
	       JTable table = new JTable(dataModel);
	
       
	       // table.setAutoCreateRowSorter(true);
  	       //set size car bcp trop grande, elle cache le bouton et le txtfield 
 	       panel.add(table,BorderLayout.CENTER);
	         
		
		envoyer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// connexion a la BDD
				
				JOptionPane.showMessageDialog(frame, "Erreur : \nLe Moteur n'arrive pas à joindre la BDD.\nVeuillez vérifiez vos connexions.");
 			}
			
		});
		// Le pb de ce scrollPane/JTable , c'est qu'il cache la vue du textField et du bouton rechercher.


		
       

		mbar.add(menu);
		mbar.add(menu2);
		mbar.add(menu3);

		
		frame.setJMenuBar(mbar);		
		
 		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		
		
	}

}
