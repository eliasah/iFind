package engine.crawler;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * Cette classe permet de configurer le démon d'iFind.
 * Au lancement, une fenêtre montrant les paramètres actuels apparaît;
 * il est ensuite possible de retirer ou d'ajouter des répertoires à surveiller
 * et des types de fichiers à exclure.
 * Par défaut, il existe déjà trois types de fichiers à exclure et qui ne 
 * peuvent pas être retirés : les fichiers cachés, les fichiers de sauvegarde 
 * et les fichiers swap.
 * 
 * @author isabelle
 *
 */
public class DaemonConfiguration {

	/**
	 * Ouvrre un JFileChooser permettant à l'utilisateur de choisir un
	 * répertoire.
	 * @return Le répertoire sélectionné par l'utilisateur.
	 */
	public static String choosePath() {
		String path = null;
		JFileChooser chooser = 
				new JFileChooser(System.getenv().get("HOME")+"/..");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		int returnVal = chooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			path = chooser.getSelectedFile().toString();
		return path;
	}

	/**
	 * Vérifie qu'un répertoire est un sous-répertoire du dossier HOME
	 * de l'utilisateur.
	 * @param Le chemin du répertoire à analyser.
	 * @return True si le répertoire est valide, False sinon.
	 */
	public static boolean isValidPath(String path) {
		String homeParent = new File(System.getenv().get("HOME")).getParent();
		return path.startsWith(homeParent);
	}

	/**
	 * Vérifie que l'utilisateur a les droits en lecture et en exécution
	 * sur un répertoire; si ce n'est pas le cas, le répertoire ne pourra pas 
	 * être surveillé correctement.
	 * @param path Le chemin du répertoire à analyser.
	 * @return True si le répertoire est lisible par l'utilisateur.
	 * @throws IOException 
	 */
	public static boolean hasRights(String path) throws IOException {
		boolean hasRights = false;
		Set<PosixFilePermission> rights;
		Path p = Paths.get(path);
		rights = Files.getPosixFilePermissions(p);
		hasRights = rights.contains(PosixFilePermission.OWNER_EXECUTE)
				&& rights.contains(PosixFilePermission.OWNER_READ);
		return hasRights;
	}

	/**
	 * Demande à l'utilisateur de choisir un répertoire à ajouter au fichier 
	 * de configuration des corpus à surveiller, et l'ajoute uniquement si ce 
	 * répertoire est valide.
	 * @return True si au moins un répertoire a été ajouté au fichier du corpus,
	 * False sinon. 
	 * @throws IOException 
	 */
	public static boolean configureCorpus() throws IOException {
		BufferedWriter bw;
		boolean configured = false;
		String path = choosePath();
		if (path != null && isValidPath(path) && hasRights(path)) {
			// écrit dans le fichier de configuration des corpus à 
			// surveiller le nouveau chemin sélectionné
			bw = new BufferedWriter(new FileWriter("config/corpus.dat"));
			bw.write(path);
			bw.newLine();
			bw.close();
			configured = true;
		}
		return configured;
	}

	/**
	 * Ajoute un type au fichier de configuration des 
	 * types de fichiers à exclure.
	 * @return True si au moins un type a été ajouté, False sinon.
	 * False sinon. 
	 * @throws IOException 
	 */
	public static boolean configureExcludedTypes() throws IOException {
		// TODO
		BufferedWriter bw;
		boolean configured = false;
		// writing configuration file for the types to exclude in indexation
		bw = new BufferedWriter(new FileWriter("config/excluded_types.dat"));
		bw.write(".*~"); // fichiers de sauvegarde
		bw.newLine();
		bw.write(".*\\.swp"); // fichiers swap
		bw.newLine();
		bw.write("\\..*"); // fichiers cachés
		bw.newLine();
		bw.close();
		return configured;
	}

	/**
	 * Lit un fichier et crée un tableau à partir de ses lignes.
	 * Le tableau est destiné à être utilisé dans une JTable.
	 * @param file Le fichier à parser.
	 * @return Le tableau contenant le contenu du fichier.
	 * @throws IOException
	 */
	public static Object[][] readFile(File file) throws IOException {
		BufferedReader br;
		int countLines = 0;
		String line;
		LinkedList<String> list = new LinkedList<String>();
		br = new BufferedReader(new FileReader(file));
		while ((line = br.readLine()) != null) {
			list.add(line);
			countLines++;
		}
		br.close();
		Object[] o = list.toArray();
		Object[][] ret = new Object[countLines][1];
		for (int i=0; i<o.length; i++) {
			ret[i][0] = o[i];
		}
		return ret;
	}

	// TODO revoir entièrement l'interface graphique ;
	// ajouter un JTextField pour ajouter un type à exclure
	/**
	 * Lance l'interface graphique de la configuration du démon d'iFind.
	 * @throws IOException
	 */
	public static void createGUI() throws IOException {
		File file;

		// panel de configuration du corpus
		JPanel panelCorpus = new JPanel(new BorderLayout());
		file = new File("config/corpus.dat");
		if (!file.exists()) file.createNewFile();
		Object[] titlesCorpus = {"Répertoires"};
		JButton bAddCorpus = new JButton("+");
		bAddCorpus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					configureCorpus();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		panelCorpus.add(new JLabel("Modification du corpus"), 
				BorderLayout.EAST);
		panelCorpus.add(new JTable(readFile(file), titlesCorpus), 
				BorderLayout.WEST);
		panelCorpus.add(bAddCorpus);

		// panel de configuration des types de fichiers à exclure
		JPanel panelTypes = new JPanel(new BorderLayout());
		file = new File("config/excluded_types.dat");
		if (!file.exists()) file.createNewFile();
		Object[] titlesTypes = {"Fichiers"};
		JButton bAddType = new JButton("+");
		bAddType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					configureExcludedTypes();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		panelTypes.add(new JLabel("Modification des fichiers à exclure"),
				BorderLayout.EAST);
		panelTypes.add(new JTable(readFile(file), titlesTypes),
				BorderLayout.WEST);
		panelTypes.add(bAddType);

		// panel principal
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(panelCorpus, BorderLayout.NORTH);
		mainPanel.add(panelTypes, BorderLayout.SOUTH);

		// fenêtre principale
		JFrame f = new JFrame("Configuration du démon iFind");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(mainPanel);
		f.pack();
		f.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			createGUI();
		} catch (IOException e) {
			System.err.println("Erreur pendant la configuration du démon.");
		}
	}

}
