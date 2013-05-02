import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyListener;
import java.util.*;

public class JNotifyTest {

	static int open = 0;

	static class MyThread extends Thread {
		String myaction, myrootPath, myoldName, mynewName;

		MyThread(String action, String rootPath, String oldName, String newName) {
			myaction = action;
			myrootPath = rootPath;
			myoldName = oldName;
			mynewName = newName;
		}

		public void run() {
			open++;
			try { 
				Thread.sleep(1000);
			} catch(Exception e) {}
			open--;
			if(open == 0) {
				System.out.println(open);
				System.out.println(myaction + myrootPath + " : " + myoldName + " -> " + mynewName);
			}
		}
	}

	static public void sample(String path) throws Exception {
		// watch mask, specify events you care about,
		// or JNotify.FILE_ANY for all events.
		int mask = JNotify.FILE_CREATED  | 
				JNotify.FILE_DELETED  | 
				JNotify.FILE_MODIFIED | 
				JNotify.FILE_RENAMED;
		// watch subtree?
		boolean watchSubtree = true;
		// add actual watch
		int watchID = JNotify.addWatch(path, mask, watchSubtree, new Listener());
	}

	static void printaction(String action,String rootPath,String oldName,String newName) {
		(new MyThread(action,rootPath,oldName,newName)).start();
	}

	static class Listener implements JNotifyListener {
		public void fileRenamed(int wd, String rootPath, String oldName, String newName) {
			printaction("renamed ",rootPath,oldName,newName);
		}
		public void fileModified(int wd, String rootPath, String name) {
			printaction("modified ", rootPath,"",name);
		}
		public void fileDeleted(int wd, String rootPath, String name) {
			printaction("deleted ", rootPath,"",name);
		}
		public void fileCreated(int wd, String rootPath, String name) {
			printaction("created ", rootPath,"",name);
		}
		void print(String msg) {
			System.err.println(msg);
		}
	}

	public static void main(String[] args) throws Exception {
		if(args.length > 0) {
			System.out.println(args[0]);
			sample(args[0]);
			while(true) {
				Thread.sleep(1000000);
			}
		}
	}
}
