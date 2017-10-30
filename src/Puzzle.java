import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Puzzle {

	private ArrayList<String> solutions;
	private ArrayList<Integer> numPrefsMet;
	private ArrayList<Person> people;
	private String tableDisplay;
	private String puzNum;
	
	public Puzzle(String roundID)
	{
		solutions = new ArrayList<String>();
		numPrefsMet = new ArrayList<Integer>();
		puzNum = roundID;
	}
	
	public void play() throws SQLException{
		retrievePeople();
		retrieveTableDisplay();
		retrieveSolutions();
	}
	
	public ArrayList<Integer> retrievePeopleIDs() throws SQLException
	{
		ArrayList<Integer> peopleIDs = new ArrayList<Integer>();
		String query = "use ShabbosTable select PersonID from PersonRound where RoundID =" + puzNum;
		Statement stmt = Game.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			peopleIDs.add(rs.getInt("PersonID"));
		}
	
		return peopleIDs;	
	}
	
	public void retrievePeople() throws SQLException
	{
		ArrayList<Integer> peopleIDs = retrievePeopleIDs();
		people = new ArrayList<Person>();
		for(Integer p : peopleIDs)
			people.add(new Person(p));		
	}
	
	public ArrayList<String> retrieveSolutions() throws SQLException
	{
		String query = "use ShabbosTable select SolutionDescription, NumPrefsMet from Solutions where RoundID = " + puzNum;
		Statement stmt = Game.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			solutions.add(rs.getString("SolutionDescription"));
			numPrefsMet.add(rs.getInt("NumPrefsMet"));
		}
	
		rs.close();
		stmt.close();
		return solutions;
	}
	
	public void retrieveTableDisplay() throws SQLException
	{
		String query = "use ShabbosTable select TableSetup from Rounds where RoundID = " + puzNum;
		Statement stmt = Game.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		tableDisplay =  rs.getString("TableSetup");
	}
	
	public ArrayList<String> getSolutions()
	{
		return this.solutions;
	}
	
	public int getNumPrefsMet(int sNum)
	{
		return this.numPrefsMet.get(sNum);
	}
	
	public ArrayList<Person> getPeople()
	{
		return people;
	}
	
	public String getTableDisplay()
	{
		return tableDisplay;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		for(Person p : getPeople())
			sb.append(p.toString());
		sb.append("\n");
		sb.append(getTableDisplay());
		return sb.toString();
	}

}
