import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class RechercheAvancee {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						(new RechercheAvancee()).createGUI();
					}
				});
	}
	
	private static int margin = 5;

	public void createGUI() {
		JFrame frame = new JFrame("titre");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// panel principal
		JPanel panel = new JPanel();
		frame.setContentPane(panel);
		panel.setLayout(new BorderLayout(margin, margin));

		// panel de recherche
		JPanel panelSearch = new JPanel();
		panelSearch.setLayout(new BorderLayout(margin, margin));
		JButton go = new JButton("Recherche avancee");
		go.setPreferredSize(new Dimension(20, 50));
		panelSearch.add(go, BorderLayout.NORTH);
		JPanel panelAvance = new JPanel();
		panelAvance.setLayout(new BorderLayout(margin, margin));
		JLabel titre = new JLabel("Recherche Avancee");
		panelAvance.add(titre, BorderLayout.NORTH);
		JPanel avanceWest = new JPanel();
		avanceWest.setLayout(new GridLayout(4, 1));
		
		//Menu avancee gauche avec les different type (music Document etc..)
		JButton music = new JButton("Music");
		music.setBackground(Color.gray);
		JButton document = new JButton("Document");
		JButton image = new JButton("Image");
		JButton autre = new JButton("Autre");
		avanceWest.add(music);
		avanceWest.add(document);
		avanceWest.add(image);
		avanceWest.add(autre);
		panelAvance.add(avanceWest, BorderLayout.WEST);
		
		//Menu central, music dans notre cas
		JPanel avanceMusic = new JPanel();
		avanceMusic.setLayout(new GridLayout(4,2));
		JLabel titreMusic = new JLabel("Titre");
		JTextField chercheTitre = new JTextField("");
		JLabel auteur = new JLabel("Auteur");
		JTextField chercheAuteur = new JTextField("");
		JLabel genre = new JLabel("Genre");
		JTextField chercheGenre = new JTextField("");
		JButton chercher = new JButton("Chercher");
		avanceMusic.add(titreMusic);
		avanceMusic.add(chercheTitre);
		avanceMusic.add(auteur);
		avanceMusic.add(chercheAuteur);
		avanceMusic.add(genre);
		avanceMusic.add(chercheGenre);
		avanceMusic.add(chercher);
		
		panelAvance.add(avanceMusic, BorderLayout.CENTER);
		panelSearch.add(panelAvance, BorderLayout.CENTER);
		
		

		// panel des resultats
		JPanel panelResults = new JPanel();
		panelResults.setLayout(new BorderLayout(margin, margin));
		String[] names = {"Nom", "Date de creation", "Date de modification", "Type"};
		Object[][] foo = {{"toto", "10/12/12", "02/01/13", "jpg"},
				{"abc", "01/04/03", "15/03/06", "txt"}};
		JTable tableResults = new JTable(foo, names);
		JScrollPane results = new JScrollPane(tableResults);
		Dimension minimumSize = new Dimension(150, 150);
		results.setMinimumSize(minimumSize);
		results.setPreferredSize(minimumSize);
		panelResults.add(results, BorderLayout.NORTH);
		JLabel labelIndexation = new JLabel("La derniere indexation a eu lieu "
				+ "le 10/12/12 � 23h12.");
		panelResults.add(labelIndexation, BorderLayout.SOUTH);

		// ajout des panels internes au panel principal
		panel.add(panelSearch, BorderLayout.NORTH);
		panel.add(panelResults, BorderLayout.SOUTH);

		frame.pack();	
		frame.setVisible(true);
	}


}
