package proxy.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TrigramTest {

	@Before
	public void setUp() throws Exception {
		Trigram t = new Trigram("toto");
	}

	@Test
	public void testTrigramString() {
		Trigram t = new Trigram("toto");
	}

	@Test
	public void testTrigramFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintMap() {
		fail("Not yet implemented");
	}

}
