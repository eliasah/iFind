package engine.crawler;

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
import java.util.Scanner;
import java.util.Set;

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
	 * Vérifie qu'un répertoire est un sous-répertoire du dossier HOME
	 * de l'utilisateur.
	 * @param Le chemin du répertoire à analyser.
	 * @return True si le répertoire est valide, False sinon.
	 */
	public static boolean isValidPath(String path) {
		String homeParent = new File(System.getenv().get("HOME")).getParent();
		return !path.equals(homeParent) && path.startsWith(homeParent);
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

	//	/**
	//	 * Ouvre un JFileChooser permettant à l'utilisateur de choisir un
	//	 * répertoire.
	//	 * @return Le répertoire sélectionné par l'utilisateur.
	//	 */
	//	public static String choosePath() {
	//		String path = null;
	//		JFileChooser chooser = 
	//				new JFileChooser(System.getenv().get("HOME")+"/..");
	//		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	//		chooser.setAcceptAllFileFilterUsed(false);
	//		int returnVal = chooser.showSaveDialog(null);
	//		if (returnVal == JFileChooser.APPROVE_OPTION)
	//			path = chooser.getSelectedFile().toString();
	//		return path;
	//	}
	//
	//	/**
	//	 * Demande à l'utilisateur de choisir un répertoire à ajouter au fichier 
	//	 * de configuration des corpus à surveiller, et l'ajoute uniquement si ce 
	//	 * répertoire est valide.
	//	 * @return True si au moins un répertoire a été ajouté au fichier du corpus,
	//	 * False sinon. 
	//	 * @throws IOException 
	//	 */
	//	public static boolean configureCorpus() throws IOException {
	//		BufferedWriter bw;
	//		boolean configured = false;
	//		String path = choosePath();
	//		if (path != null && isValidPath(path) && hasRights(path)) {
	//			// écrit dans le fichier de configuration des corpus à 
	//			// surveiller le nouveau chemin sélectionné
	//			bw = new BufferedWriter(new FileWriter("config/corpus.dat"));
	//			bw.write(path);
	//			bw.newLine();
	//			bw.close();
	//			configured = true;
	//		}
	//		return configured;
	//	}
	//
	//	/**
	//	 * Ajoute un type au fichier de configuration des 
	//	 * types de fichiers à exclure.
	//	 * @return True si au moins un type a été ajouté, False sinon.
	//	 * False sinon. 
	//	 * @throws IOException 
	//	 */
	//	public static boolean configureExcludedTypes() throws IOException {
	//		// TODO
	//		BufferedWriter bw;
	//		boolean configured = false;
	//		// writing configuration file for the types to exclude in indexation
	//		bw = new BufferedWriter(new FileWriter("config/excluded_types.dat"));
	//		bw.write(".*~"); // fichiers de sauvegarde
	//		bw.newLine();
	//		bw.write(".*\\.swp"); // fichiers swap
	//		bw.newLine();
	//		bw.write("\\..*"); // fichiers cachés
	//		bw.newLine();
	//		bw.close();
	//		return configured;
	//	}
	//
	//	/**
	//	 * Lit un fichier et crée un tableau à partir de ses lignes.
	//	 * Le tableau est destiné à être utilisé dans une JTable.
	//	 * @param file Le fichier à parser.
	//	 * @return Le tableau contenant le contenu du fichier.
	//	 * @throws IOException
	//	 */
	//	public static Object[][] readFile(File file) throws IOException {
	//		BufferedReader br;
	//		int countLines = 0;
	//		String line;
	//		LinkedList<String> list = new LinkedList<String>();
	//		br = new BufferedReader(new FileReader(file));
	//		while ((line = br.readLine()) != null) {
	//			list.add(line);
	//			countLines++;
	//		}
	//		br.close();
	//		Object[] o = list.toArray();
	//		Object[][] ret = new Object[countLines][1];
	//		for (int i=0; i<o.length; i++) {
	//			ret[i][0] = o[i];
	//		}
	//		return ret;
	//	}
	//
	//	// TODO revoir entièrement l'interface graphique ;
	//	// ajouter un JTextField pour ajouter un type à exclure
	//	/**
	//	 * Lance l'interface graphique de la configuration du démon d'iFind.
	//	 * @throws IOException
	//	 */
	//	public static void createGUI() throws IOException {
	//		File file;
	//
	//		// panel de configuration du corpus
	//		JPanel panelCorpus = new JPanel(new BorderLayout());
	//		file = new File("config/corpus.dat");
	//		if (!file.exists()) file.createNewFile();
	//		Object[] titlesCorpus = {"Répertoires"};
	//		JButton bAddCorpus = new JButton("+");
	//		bAddCorpus.addActionListener(new ActionListener() {
	//			@Override
	//			public void actionPerformed(ActionEvent arg0) {
	//				try {
	//					configureCorpus();
	//				} catch (IOException e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//		panelCorpus.add(new JLabel("Modification du corpus"), 
	//				BorderLayout.EAST);
	//		panelCorpus.add(new JTable(readFile(file), titlesCorpus), 
	//				BorderLayout.WEST);
	//		panelCorpus.add(bAddCorpus);
	//
	//		// panel de configuration des types de fichiers à exclure
	//		JPanel panelTypes = new JPanel(new BorderLayout());
	//		file = new File("config/excluded_types.dat");
	//		if (!file.exists()) file.createNewFile();
	//		Object[] titlesTypes = {"Fichiers"};
	//		JButton bAddType = new JButton("+");
	//		bAddType.addActionListener(new ActionListener() {
	//			@Override
	//			public void actionPerformed(ActionEvent arg0) {
	//				try {
	//					configureExcludedTypes();
	//				} catch (IOException e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//		panelTypes.add(new JLabel("Modification des fichiers à exclure"),
	//				BorderLayout.EAST);
	//		panelTypes.add(new JTable(readFile(file), titlesTypes),
	//				BorderLayout.WEST);
	//		panelTypes.add(bAddType);
	//
	//		// panel principal
	//		JPanel mainPanel = new JPanel(new BorderLayout());
	//		mainPanel.add(panelCorpus, BorderLayout.NORTH);
	//		mainPanel.add(panelTypes, BorderLayout.SOUTH);
	//
	//		// fenêtre principale
	//		JFrame f = new JFrame("Configuration du démon iFind");
	//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//		f.add(mainPanel);
	//		f.pack();
	//		f.setVisible(true);
	//	}
	
	/**
	 * Lit un fichier et renvoie chacune de ses lignes sous forme de liste.
	 * @param filePath Le chemin du fichier à lire.
	 * @return La liste des lignes du fichier.
	 * @throws IOException
	 */
	public static LinkedList<String> readFile(String filePath) throws IOException {
		BufferedReader br;
		String line;
		LinkedList<String> list = new LinkedList<String>();
		br = new BufferedReader(new FileReader(filePath));
		while ((line = br.readLine()) != null) {
			list.add(line);
		}
		br.close();
		return list;
	}
	
	/**
	 * Ecrit le contenu d'une liste dans un fichier, en utilisant le saut de 
	 * ligne comme séparateur.
	 * @param filePath Le chemin du fichier où écrire.
	 * @param list La liste à écrire dans le fichier.
	 * @throws IOException
	 */
	public static void writeFile(String filePath, LinkedList<String> list) 
			throws IOException {
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter(filePath));
		for (String s : list) {
			bw.write(s);
			bw.newLine();
		}
		bw.close();
	}

	/**
	 * Demande à l'utilisateur de choisir une action parmi celles proposées
	 * à l'écran, puis l'exécute.
	 * @param in L'entrée où lire au clavier.
	 * @param corpus La liste des répertoires à surveiller.
	 * @param types La liste des types à exclure.
	 * @return Le choix de l'utilisateur
	 * @throws IOException
	 */
	public static int printMenu(Scanner in, LinkedList<String> corpus,
			LinkedList<String> types) throws IOException {
		String line;
		int c = -1; // le choix de l'utilisateur

		// Impression des configurations actuelles
		System.out.println("CORPUS A SURVEILLER :");
		for (String s : corpus)
			System.out.println("\t" + s);
		System.out.println("TYPES A EXCLURE :");
		for (String s : types)
			System.out.println("\t" + s);

		// Impression du menu
		System.out.println("Veuillez entrer votre choix :");
		System.out.println("-------------------------------------------------");
		System.out.println("0 - fin");
		System.out.println("1 - ajout d'un répertoire au corpus");
		System.out.println("2 - suppression d'un répertoire du corpus");
		System.out.println("3 - ajout d'un type à exclure");
		System.out.println("4 - suppression d'un type à exclure");
		System.out.println("-------------------------------------------------");
		System.out.println();

		// Lecture du choix utilisateur
		c = Integer.parseInt(in.nextLine());

		// Traitement du choix utilisateur
		switch(c){
		case 1 : 
			// TODO
			System.out.println("Entrez le chemin du répertoire à surveiller :");
			line = in.nextLine();
			if (isValidPath(line) && hasRights(line))
				if (!corpus.contains(line)) 
					corpus.add(line);
				else
					System.out.println("Ce répertoire est déjà surveillé !");
			else
				System.out.println("Répertoire non ajouté car non valide !");
			break;
		case 2 :
			System.out.println("Entrez le chemin du répertoire à supprimer ");
			line = in.nextLine();
			corpus.remove(line);
			System.out.println("Répertoire supprimé !");
			break;
		case 3 :
			System.out.println("Entrez le type de fichier à exclure :");
			line = in.nextLine();
			if (!types.contains(line))
				types.add(line);
			else
				System.out.println("Ce type est déjà exclus !");
			break;
		case 4 :
			System.out.println("Entrez le type de fichier à ne plus ignorer :");
			line = in.nextLine();
			types.remove(line);
			System.out.println("Type supprimé !");
			break;
		case 0 : 
			System.out.println("FIN");
			break;
		default : 
			System.out.println("ERREUR!");
		}
		return c;
	}

	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			LinkedList<String> corpus = readFile("config/corpus.dat");
			LinkedList<String> types = readFile("config/excluded_types.dat");

			// Impression du menu. Pour finir, tapez 0
			int c = -1;
			while(c != 0){
				c = printMenu(in, corpus, types);
				if (c != 0){
					System.out.println("Appuyez sur entree.");
					System.in.read();
				}
			}

			// Sauvegarde de la nouvelle configuration
			in.close();
			writeFile("config/corpus.dat", corpus);
			writeFile("config/excluded_types.dat", types);
			System.out.println("Fichiers modifiés!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
