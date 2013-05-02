package crawler;

import java.util.LinkedList;
import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyListener;

public class JObserver {

	private LinkedList<String> createdFiles;
	private LinkedList<String> deletedFiles;
	private LinkedList<String> modifiedFiles;
	private LinkedList<String> renamedFiles;

	public JObserver(String corpus,	LinkedList<String> createdFiles, 
			LinkedList<String> deletedFiles, LinkedList<String> modifiedFiles, 
			LinkedList<String> renamedFiles) {
		try {
			sample(corpus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sample(String path) throws Exception {
		int mask = JNotify.FILE_CREATED
				| JNotify.FILE_DELETED
				| JNotify.FILE_MODIFIED 
				| JNotify.FILE_RENAMED;
		boolean watchSubtree = true;
		JNotify.addWatch(path, mask, watchSubtree, new Listener());
	}

	class Listener implements JNotifyListener {
		public void fileCreated(int wd, String rootPath, String name) {
			String value = rootPath + "/" + name;
			if (!createdFiles.contains(value))
				createdFiles.add(value);
		}
		public void fileDeleted(int wd, String rootPath, String name) {
			String value = rootPath + "/" + name;
			if (!deletedFiles.contains(value))
				deletedFiles.add(value);
		}
		public void fileModified(int wd, String rootPath, String name) {
			String value = rootPath + "/" + name;
			if (!modifiedFiles.contains(value))
				modifiedFiles.add(value);
		}
		public void fileRenamed(int wd, String rootPath, String oldName, String newName) {
			String value = rootPath + "/" + oldName + "|" + rootPath + "/" + newName;
			if (!renamedFiles.contains(value))
				renamedFiles.add(value);
		}
	}

}
