package crawler;

import java.util.LinkedList;
import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyListener;

public class JListener {

	private LinkedList<Event> events;

	public JListener(String corpus, LinkedList<Event> events) {
		this.events = events;
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

	public boolean containsEvent(LinkedList<Event> events, Event event) {
		for (Event e : events)
			if (e.equals(event))
				return true;
		return false;
	}

	class Listener implements JNotifyListener {
		public void fileCreated(int wd, String rootPath, String name) {
			String path = rootPath + "/" + name;
			events.add(new Event('C', null, path));
		}
		public void fileDeleted(int wd, String rootPath, String name) {
			String path = rootPath + "/" + name;
			events.add(new Event('D', null, path));
			int index;
			do {
				index = events.indexOf((new Event('C', null, path)));
				if (index != -1)
					events.remove(index);
			} while (index != -1);
		}
		public void fileModified(int wd, String rootPath, String name) {
			String path = rootPath + "/" + name;
			if (!containsEvent(events, new Event('M', null, path)))
				events.add(new Event('M', null, path));
		}
		public void fileRenamed(int wd, String rootPath, String oldName, String newName) {
			String oldPath = rootPath + "/" + oldName;
			String newPath = rootPath + "/" + newName;
			events.add(new Event('R', oldPath, newPath));
		}
	}

}
