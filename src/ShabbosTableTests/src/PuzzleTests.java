import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

public class PuzzleTests 
{

	// Is this the right way to do this?
	@Test(expected = IndexOutOfBoundsException.class)
	public void checkConstructorSolutionsArrayListInitializesProperly()  
	{
		Puzzle p = new Puzzle("11");
		assertNotNull(p.getSolutions());
		p.getSolutions().get(0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void checkConstructorNumPrefsMetArrayListInitializesProperly() 
	{
		Puzzle p = new Puzzle("11");
		// we don't have access to numPrefsMet
		p.getNumPrefsMet(1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void checkConstructorPeopleArrayListInitializesProperly() 
	{
		Puzzle p = new Puzzle("11");
		assertNotNull(p.getPeople());
		p.getPeople().get(0);
	}

	@Test
	public void checkRetrievePeopleIDsRetrievesCorrectPeopleIDs() throws SQLException 
	{
		ArrayList<Integer> pIDs = new ArrayList<Integer>();
		pIDs.add(1);
		pIDs.add(2);
		pIDs.add(9);
		pIDs.add(10);
		pIDs.add(11);
		pIDs.add(12);
		pIDs.add(13);
		pIDs.add(14);
		Puzzle p = new Puzzle("11");
		/*
		 * for(Integer i: pIDs) System.out.println(i); for(Integer i:
		 * p.retrievePeopleIDs()) System.out.println(i);
		 */
		assertEquals(8, p.retrievePeopleIDs().size());
		for (int i = 0; i < pIDs.size(); i++) {
			assertTrue(p.retrievePeopleIDs().contains(pIDs.get(i)));
		}

	}

	@Test
	public void retrievePeopleAddsAllPeople() throws SQLException  
	{
		Puzzle p = new Puzzle("11");
		p.retrievePeople();
		assertEquals(8, p.getPeople().size());
		// Once we know that retrievePeopleIDs() got the right people, we know
		// That it will instantiate the right people
	}

	@Test
	public void retrieveTableDisplayRetrievesCorrectDisplay() throws SQLException
	{
		String tableDisplay = " Windows" + "\n _ _ __" + "\n      _ _" + "\n _ _ __";
		Puzzle p = new Puzzle("11");
		p.retrieveTableDisplay();
		assertEquals(tableDisplay, p.getTableDisplay());
	}

	@Test
	public void retrieveSolutionsRetrievesAllCorrectSolutions() throws SQLException
	{
		ArrayList<String> solutions = new ArrayList<>();
		solutions.add("1-2-13-11-12-14-9-10");
		solutions.add("1-2-11-14-10-9-13-12");
		solutions.add("1-12-13-11-2-14-9-10");
		solutions.add("1-2-14-11-12-13-9-10");
		solutions.add("1-2-11-13-10-9-14-12");
		solutions.add("1-12-14-11-2-13-9-10");
		Puzzle p = new Puzzle("11");
		p.retrieveSolutions();
		assertEquals(6, p.getSolutions().size());
		for (int i = 0; i < solutions.size(); i++) {
			assertTrue(p.getSolutions().contains(solutions.get(i)));
		}
	}

}