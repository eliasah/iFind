import java.awt.*;
import javax.swing.*;

public class RechercheSimple {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						(new RechercheSimple()).createGUI();
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
		JTextField text = new JTextField("entrez les termes de votre recherche ...");
		panelSearch.add(text, BorderLayout.WEST);
		JButton go = new JButton("Chercher");
		panelSearch.add(go, BorderLayout.EAST);

		// panel des resultats
		JPanel panelResults = new JPanel();
		panelResults.setLayout(new BorderLayout(margin, margin));
		String[] names = {"Nom", "Date de création", "Date de modification", "Type"};
		Object[][] foo = {{"toto", "10/12/12", "02/01/13", "jpg"},
				{"abc", "01/04/03", "15/03/06", "txt"}};
		JTable tableResults = new JTable(foo, names);
		JScrollPane results = new JScrollPane(tableResults);
		Dimension minimumSize = new Dimension(150, 150);
		results.setMinimumSize(minimumSize);
		results.setPreferredSize(minimumSize);
		panelResults.add(results, BorderLayout.NORTH);
		JLabel labelIndexation = new JLabel("La dernière indexation a eu lieu "
				+ "le 10/12/12 à 23h12.");
		panelResults.add(labelIndexation, BorderLayout.SOUTH);

		// ajout des panels internes au panel principal
		panel.add(panelSearch, BorderLayout.NORTH);
		panel.add(panelResults, BorderLayout.SOUTH);

		frame.pack();	
		frame.setVisible(true);
	}
}
