package proxy.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PgSQL_DBTest {
	private PgSQL_DB conn;

	@Before
	public void setUp() throws Exception {
		conn = new PgSQL_DB("abou","x55efviq");
	}
	
	@Test
	public void testPgSQL_DB() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsconnected() {
		assertTrue(conn.isconnected());
	}

	@Test
	public void testClose() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateDB() {
		fail("Not yet implemented");
	}

	@Test
	public void testSuppressionTable() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertionTuplesPredefinis() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuery() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertionTuplesUtilisateur() {
		fail("Not yet implemented");
	}

	@Test
	public void testRequest() {
		fail("Not yet implemented");
	}

}
