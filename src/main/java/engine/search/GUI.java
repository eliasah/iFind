package engine.search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.text.StyledEditorKit;


/*
 * Auteur : Rouach Jeremie
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
	SearchClient client;
	Result retourServ;
	
	
	
	public GUI (){
		int[] ports = {30000,30001,30002};
		SearchDBServer server = new SearchDBServer(ports);
		server.start();
		
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
		
		 
		
		
		
		//Label de presentation du TxtField + TxtField	qui appartiennent au panel_north
		
	    search = new JLabel("Une partie ou l ensemble du nom du document : ");
		panel_north.add(search,BorderLayout.NORTH);
		
		searchField = new JTextField();
		searchField.setColumns(20);
 		
		panel_north.add(searchField,BorderLayout.NORTH);
		
		// bouton pour lancer la recherche
		
		
		JButton envoyer = new JButton("Rechercher");
		panel_north.add(envoyer,BorderLayout.CENTER);
		panel_north.setBorder(new LineBorder(new Color(0,0,0)));

		tableauRes = new ResultTable();
		
		
	       //bouton pour passer a l interface avancee
	       
		JButton advanced = new JButton("Utilisateur Avancee ?");	
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
				// Recuperation du nom + compteur incr pour l id + creation de(s) objet(s) Search (un mot par objet Search)
				
				compteur++;
				
				searchtxt = searchField.getText();
				
				String[] tab = searchtxt.split(" ");// dans le cas ou on ecrit : toto titi 
 			   	Search[] tabS = new Search[tab.length];// on cree un tableau d objets Search en consequence
 			   	
  			   	
 			   	for(int i=0;i<tabS.length;i++){
 	 			   	tabS[i]= new Search(compteur,tab[i],false,null,null,null,null);
 			   	}
 			   	
 			   	client = new SearchClient();
 			   	client.Connect();
 			   	client.Demande(tabS);
 			   	retourServ = client.EcouteReponse(); 
 			   	
 				Object[][] data = new Object[retourServ.getFiles().size()][3];
 				for(int i=0;i<retourServ.getFiles().size();i++){
 					data[i][0] = retourServ.getFiles().get(i).getName();
 					data[i][1] = retourServ.getFiles().get(i).getPath();
 					data[i][2] = retourServ.getFiles().get(i).getSize();
 				}

 			   	tableauRes.setData(data);
 			   	
				JOptionPane.showMessageDialog(frame, "Envoi des informations.\nVeuillez patientez s il vous plait.");
 			}
			
		});
		
 

		mbar.add(menu);
		mbar.add(menu2);
		mbar.add(menu3);
		frame.setJMenuBar(mbar);		

		
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		
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
