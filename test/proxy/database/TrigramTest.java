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

	}

}
