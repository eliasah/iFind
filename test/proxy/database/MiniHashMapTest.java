package proxy.database;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

public class MiniHashMapTest {

    @Test
    public void testRemove() {
        final MiniHashMap<String, Integer> map = new MiniHashMap<String, Integer>();
        map.put("Lars", 1);
        map.put("Günther", 12);
        map.put("Max", 2);

        assertEquals(null, map.get("Markus"));
        assertEquals(1, map.get("Lars"), 0);
        assertEquals(2, map.get("Max"), 0);
        assertEquals(12, map.get("Günther"), 0);

        map.remove("Max");
        assertEquals(null, map.get("Markus"));
        assertEquals(1, map.get("Lars"), 0);
        assertEquals(null, map.get("Max"));
        assertEquals(12, map.get("Günther"), 0);

        assertEquals(true, map.remove("Lars"));
        assertEquals(false, map.remove("Lars"));
        assertEquals(null, map.get("Markus"));
        assertEquals(null, map.get("Lars"));
        assertEquals(null, map.get("Max"));
        assertEquals(12, map.get("Günther"), 0);

        map.put("Lars", 1);
        map.put("Günther", 12);
        map.put("Max", 2);
        assertEquals(true, map.remove("Günther"));

    }

    @Test
    public void testStandard() {
        final MiniHashMap<String, Integer> map = new MiniHashMap<String, Integer>();
        map.put("Lars", 1);
        map.put("Günther", 12);
        map.put("Max", 2);

        assertEquals(null, map.get("Markus"));
        assertEquals(1, map.get("Lars"), 0);
        assertEquals(2, map.get("Max"), 0);
        assertEquals(12, map.get("Günther"), 0);

    }

    @Test
    public void testDoublePut() {
        final MiniHashMap<String, Integer> map = new MiniHashMap<String, Integer>();
        map.put("Lars", 1);
        map.put("Günther", 12);

        assertEquals(1, map.get("Lars"), 0);
        assertEquals(12, map.get("Günther"), 0);

        map.put("Lars", 14);
        map.put("Günther", 122);
        map.put("Fred", 11111);
        assertEquals(14, map.get("Lars"), 0);
        assertEquals(122, map.get("Günther"), 0);
    }

    @Test
    public void testBigNumber() {
        final MiniHashMap<String, Integer> map = new MiniHashMap<String, Integer>();
        final int maxIndex = 33; // set this to big number 
        for (int i = 0; i < maxIndex; i++) {
            map.put("Tom" + String.valueOf(i), i);
        }

        for (int i = 0; i < maxIndex; i++) {
            assertEquals(map.get("Tom" + String.valueOf(i)), i, 0);
        }

    }

    @Test
    public void testRemoveBigNumber() {
        final MiniHashMap<String, Integer> map = new MiniHashMap<String, Integer>();
        final int maxIndex = 1000;
        for (int i = 0; i < maxIndex; i++) {
            final String key = "Tom" + String.valueOf(i);
            map.put(key, i);
        }

        for (int i = maxIndex / 3; i < (maxIndex / 2); i++) {
            assertTrue(map.remove("Tom" + String.valueOf(i)));
        }

        for (int i = 0; i < maxIndex; i++) {
            if ((i >= (maxIndex / 3)) && (i < (maxIndex / 2))) {
                assertEquals(null, map.get("Tom" + String.valueOf(i)));
            } else {
                assertEquals(map.get("Tom" + String.valueOf(i)), i, 0);
            }
        }
    }

    @Test
    public void testRandom() {
        final MiniHashMap<String, Integer> map = new MiniHashMap<String, Integer>(997);
        final HashMap<String, Integer> mapJdK = new HashMap<String, Integer>(997);

        final int maxIndex = 10000;
        for (int i = 0; i < maxIndex; i++) {
            final String key = "Tom" + String.valueOf((int) (Math.random() * maxIndex));
            map.put(key, i);
            mapJdK.put(key, i);
            assertEquals(map.get(key), mapJdK.get(key), 0);
        }

        for (int i = 0; i < maxIndex; i++) {
            final String key = "Tom" + String.valueOf((int) (Math.random() * maxIndex));
            map.put(key, i);
            mapJdK.put(key, i);
            assertEquals(map.get(key), mapJdK.get(key), 0);
        }

        for (int i = 0; i < maxIndex; i++) {
            final String key = "Tom" + String.valueOf((int) (Math.random() * maxIndex));
            if ((null != map.get(key)) || (null != mapJdK.get(key))) {
                assertEquals(map.get(key), mapJdK.get(key), 0);
            }
        }

        for (int i = 0; i < maxIndex; i++) {
            final String key = "Tom" + String.valueOf((int) (Math.random() * maxIndex));
            map.remove(key);
            mapJdK.remove(key);
            assertNull(map.get(key));
            assertNull(mapJdK.get(key));
        }
    }
}