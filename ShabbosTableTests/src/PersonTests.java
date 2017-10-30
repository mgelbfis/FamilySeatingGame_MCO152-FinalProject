import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.EmptyStackException;

import org.junit.*;

public class PersonTests {
	
	Person p;		

	@BeforeClass
	public static void setUpConnection() throws SQLException
	{
		Game g = new Game();
		g.connect();
	}
	
	@Before
	public void setPersonToNull()
	{
		p = null;
	}
	

	@Test
	public void retrievePersonRetrievesCorrectPersonIDBasedOnID() throws SQLException
	{
		p = new Person(1);
		assertEquals(1, p.getPersonID());
	}
	
	@Test
	public void retrievePersonRetrievesCorrectFirstNameBasedOnID() throws SQLException
	{
		p = new Person(1);
		assertEquals("Abba", p.getFirstName());
	}
	
	@Test
	public void retrievePersonRetrievesCorrectLastNameBasedOnID() throws SQLException
	{
		p = new Person(1);
		assertEquals("Levy", p.getLastName());
	}
	
	@Test
	public void retrievePersonRetrievesCorrectAgeBasedOnID() throws SQLException
	{
		p = new Person(1);
		assertEquals(30, p.getAge());
	}
	
	@Test
	public void retrievePersonRetrievesCorrectGenderBasedOnID() throws SQLException
	{
		p = new Person(1);
		assertEquals('M', p.getGender());
	}
	
	@Test
	public void retrievePersonRetrievesCorrectSpouseIDBasedOnID() throws SQLException
	{
		p = new Person(1);
		assertEquals((Integer)2, p.getSpouseID());
	}

	@Test
	public void retrievePersonRetrievesCorrectOneRestrictionBasedOnID() throws SQLException
	{
		p = new Person(1);
		assertEquals("Must sit at the head of the table", p.getRestrictions().get(0));
	}
	
	@Test
	public void retrievePersonRetrievesCorrectManyRestrictionsBasedOnID() throws SQLException
	{
		p = new Person(23);
		assertTrue(p.getRestrictions().contains("Big baalas middos; only cousin who agreed to sit on the stool"));
		assertTrue(p.getRestrictions().contains("Wants to sit next to Cousin Batsheva"));
	}
	
	@Test
	public void retrievePersonRetrievesCorrectOnePreferenceBasedOnID() throws SQLException
	{
		p = new Person(30);
		assertTrue(p.getPreferences().contains("Likes to sit next to Donny because he is easily bossed around."));	
	}
	
	@Ignore //as of now, don't have this scenario
	@Test
	public void retrievePersonRetrievesCorrectManyPreferencesBasedOnID()
	{
		
	}
	
	@Test(expected = SQLException.class)
	public void wrongIDThrowsException() throws SQLException
	{
		p = new Person(1000);
		p.getAge();
	}
//Any reason to test the constructor? No, all it does is retrieve, which we already tested. Also, we don't have setters so there is no way to test it (Person p = new Person(2); assertEquals(p, new Person(2));)
}
