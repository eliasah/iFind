package proxy.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import database.trigram.Trigram;


public class TrigramTest {
	private Trigram ts;
	private Trigram tf;

	@Before
	public void setUp() throws Exception {
		ts = new Trigram("toto");
		tf = new Trigram("example2.txt");
	}

	@Test
	public void testTrigramString() {
		assertTrue(ts.containsKey("tot"));
		assertTrue(ts.containsKey("oto"));
		assertFalse(ts.containsKey("tat"));
	}

	@Test
	public void testTrigramFile() {
		assertTrue(tf.containsKey("abc"));
		assertTrue(tf.containsKey("bcd"));
		assertTrue(tf.containsKey("cde"));
		assertTrue(tf.containsKey("def"));
		assertTrue(tf.containsKey("efg"));
		assertTrue(tf.containsKey("fgh"));
		assertTrue(tf.containsKey("ghi"));
		assertTrue(tf.containsKey("hij"));
		assertTrue(tf.containsKey("ijk"));
		assertTrue(tf.containsKey("jkl"));
		assertTrue(tf.containsKey("klm"));
		assertTrue(tf.containsKey("lmn"));
		assertTrue(tf.containsKey("mno"));
		assertTrue(tf.containsKey("nop"));
		assertTrue(tf.containsKey("opq"));
		assertTrue(tf.containsKey("pqr"));
		assertTrue(tf.containsKey("qrs"));
		assertTrue(tf.containsKey("rst"));
		assertTrue(tf.containsKey("stu"));
		assertTrue(tf.containsKey("tuv"));
		assertTrue(tf.containsKey("uvw"));
		assertTrue(tf.containsKey("vwx"));
		assertTrue(tf.containsKey("wxy"));
		assertTrue(tf.containsKey("xyz"));
		assertTrue(tf.containsKey("yza"));
		
		
		
}

}
