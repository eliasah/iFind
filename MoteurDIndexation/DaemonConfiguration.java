package MoteurDIndexation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import javax.swing.JFileChooser;

/*
 * ouvrir une JFrame montrant les paramètres actuels
 * 
 * avec des boutons + et - pour modifier le corpus
 * (cliquer sur le + appelle chooseFile) 
 * 
 * pour les types à exclure, voir avec Elias
 * 
 */

/**
 * TODO documentation
 * 
 * @author isabelle
 *
 */
public class DaemonConfiguration {

	// chooses a path for the corpus
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

	// check if this path is included in "$HOME/.." 
	public static boolean isValidPath(String path) {
		String homeParent = new File(System.getenv().get("HOME")).getParent();
		return path.startsWith(homeParent);
	}

	// check if the current user has rights on path
	public static boolean hasRights(String path) {
		boolean hasRights = false;
		Set<PosixFilePermission> rights;
		Path p = Paths.get(path);
		try {
			rights = Files.getPosixFilePermissions(p);
			hasRights = rights.contains(PosixFilePermission.OWNER_EXECUTE)
					&& rights.contains(PosixFilePermission.OWNER_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return hasRights;
	}

	// valid paths choosen by user are added to config file
	public static boolean configureCorpus() {
		BufferedWriter bw;
		boolean configured = false;
		String path = choosePath();
		if (path != null && isValidPath(path) && hasRights(path)) {
			try {
				// writing configuration file for the corpus to watch
				bw = new BufferedWriter(new FileWriter("corpus.dat"));
				bw.write(path);
				bw.newLine();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			configured = true;
		}
		return configured;
	}

	// valid types choosen by user are added to config file
	public static boolean configureExcludedTypes() {
		// writing configuration file for the types to exclude in indexation
		// bw = new BufferedWriter(new FileWriter("excluded_types.dat"));
		// bw.write("*~");
		// bw.newLine();
		// bw.write("*.swp");
		// bw.newLine();
		// bw.write(".*");
		// bw.newLine();
		// bw.close();
		return false;
	}

	public static void main(String[] args) {
		if (!configureCorpus()) {
			System.out.println("Aucune modification n'a été apportée au corpus.");
		}
	}

}
