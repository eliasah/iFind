package engine.search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.StyledEditorKit;


/*
 * Auteur : Rouach J�r�mie
 * 
 */

 
public class GUI implements Runnable{
	final JFrame frame;
	final JPanel panel;
	BorderLayout layout;
	
	JPanel panel_north;
	final JPanel user_advanced;
	JPanel UAdv_sub;
	
	
	JLabel search;
	JTextField searchField;

	
	int compteur = 0;
	String searchtxt;
	
	ResultTable tableauRes;
	
	
	
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
		frame.setBounds(200, 100, 910, 750);
		
		
		// Creation de la barre de menu avec tous les sous menu
		
		JMenuBar mbar = new JMenuBar();
		
		JMenu menu = new JMenu("Fichier");
		
	//	JMenuItem open = new JMenuItem("Ouvrir...");
 		JMenuItem quit = new JMenuItem("Quitter");
		
 	//	menu.add(open);
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
		
		 
		
		
		
		//Label de presentation du TxtField + TxtField	qui appartiennent au panel_north
		
	    search = new JLabel("Une partie ou l'ensemble du nom du document : ");
		panel_north.add(search,BorderLayout.NORTH);
		
		searchField = new JTextField();
		searchField.setColumns(20);
 		
		panel_north.add(searchField,BorderLayout.NORTH);
		
		// bouton pour lancer la recherche
		
		
		JButton envoyer = new JButton("Rechercher");
		panel_north.add(envoyer,BorderLayout.CENTER);
		panel_north.setBorder(new LineBorder(new Color(0,0,0)));

		tableauRes = new ResultTable();
		
		
	       //bouton pour passer � l'interface avanc�e
	       
		JButton advanced = new JButton("Utilisateur Avanc� ?");	
		panel_north.add(advanced,BorderLayout.SOUTH);
	 	       
 	       advanced.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
 				User_Advanced u = new User_Advanced();
				u.run();
				frame.setVisible(false);
				
			}
		});
	       
	    panel.add(tableauRes,BorderLayout.CENTER);
	    panel.setBorder(new LineBorder(new Color(0,0,0)));
	         
		
		envoyer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// R�cup�ration du nom + compteur incr pour l'id + creation de(s) objet(s) Search (un mot par objet Search)
				
				compteur++;
				
				searchtxt = searchField.getText();
				
				String[] tab = searchtxt.split(" ");// dans le cas ou on ecrit : toto titi 
 			   	Search[] tabS = new Search[tab.length];// on cree un tableau d objets Search en consequence
 			   	
  			   	
 			   	for(int i=0;i<tabS.length;i++){
 	 			   	tabS[i]= new Search(compteur,tab[i],false,null,null,null,null);
 			   	}
 			   	
				JOptionPane.showMessageDialog(frame, "Envoi des informations.\nVeuillez patientez s'il vous pla�t.");
 			}
			
		});
		
 

		mbar.add(menu);
		mbar.add(menu2);
		mbar.add(menu3);

		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.setJMenuBar(mbar);		
		
 		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
 
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new GUI());
		 
		
	}

	@Override
	public void run() {
		
		
		
	}

}
