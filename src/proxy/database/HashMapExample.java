package proxy.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMapExample {
  public static void main(String[] args) {
    Map<Integer, String> map = new HashMap<Integer, String>();

    map.put(new Integer(1), "One");
    map.put(new Integer(2), "Two");
    map.put(new Integer(3), "Three");
    map.put(new Integer(4), "Four");
    map.put(new Integer(5), "Five");

    System.out.println("Map Values Before: ");
    Set keys = map.keySet();
    for (Iterator i = keys.iterator(); i.hasNext();) {
      Integer key = (Integer) i.next();
      String value = (String) map.get(key);
      System.out.println(key + " = " + value);
    }

    System.out.println("\nRemove element with key 6");
    map.remove(new Integer(6));

    System.out.println("\nMap Values After: ");
    keys = map.keySet();
    for (Iterator i = keys.iterator(); i.hasNext();) {
      Integer key = (Integer) i.next();
      String value = (String) map.get(key);
      System.out.println(key + " = " + value);
    }
  }
}