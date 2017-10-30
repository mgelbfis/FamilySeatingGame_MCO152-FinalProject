import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class Game {  //serializable

	private String[] gameRules;
	private Integer gameScore;
	private Level currentLevel;
	private String userName;
	
	private static Connection dbConnection;
	
	private final int numLevels = 2; //CHANGED
	
	//New Game Constructor
	public Game() throws SQLException {
		connect();
		currentLevel = new Level(1);
		gameScore = 0;
		gameRules = new String[3];  //get the rules from TT
		instantiateGameRules();
	}

	public Game(String userName)
	{
		//TODO
	}
	
	public void playGame() throws SQLException
	{
		createUser();
		System.out.println(getNamesAndScores());
		while(currentLevel.getLevelNum() <= numLevels)
		{
			currentLevel.playLevel();
			gameScore += currentLevel.getLevelScore();
			updateScoreInDB();
			currentLevel = new Level(currentLevel.getLevelNum()+1);	
		}
		
	}
	
	public void GUIPlayGame() throws SQLException
	{
		while(currentLevel.getLevelNum() <= numLevels)
		{
			currentLevel.GUIPlayLevel();
			gameScore += currentLevel.getLevelScore();
			updateScoreInDB();
			currentLevel = new Level( currentLevel.getLevelNum()+1);	
		}
	}
	
	public void connect() throws SQLException
	{
		final String DATABASE_URL = "jdbc:sqlserver://DESKTOP-588999M\\SQLEXPRESS:63506;" + "databaseName=ShabbosTable"; //does the same thing regardless of whether or not the ; is there after the db name

		Game.dbConnection= DriverManager.getConnection(DATABASE_URL, "ShabbosTableLogin", "TableShabbos");
		Game.dbConnection.setAutoCommit(true);
	}
	
	public ArrayList<Integer> retrieveUserScores() throws SQLException
	{
		ArrayList<Integer> userScores = new ArrayList<Integer>();
		
		String query = "use ShabbosTable select userscore from Users order by userName";
		Statement stmt = Game.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			userScores.add(rs.getInt("userscore"));
		}
		return userScores;
	}
	
	public void updateScoreInDB() throws SQLException
	{
		PreparedStatement ps = Game.getConnection().prepareStatement("use ShabbosTable update Users set UserScore = ?  where UserName = ?");
		ps.setInt(1, this.gameScore);
		ps.setString(2, this.userName);
		ps.executeUpdate();
		ps.close();
	}
	
	public ArrayList<String> retrieveUserNames() throws SQLException
	{
		ArrayList<String> userNames = new ArrayList<String>();
		
		Statement stmt = Game.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("use ShabbosTable select username from Users order by userName");
		while(rs.next())
		{
			userNames.add(rs.getString("username"));
		}
		return userNames;
	}
	
	public void createUser() throws SQLException
	{
		boolean success = false;
		do
		{
		System.out.println("Please enter a username");
		Scanner keyboard = new Scanner(System.in);
		String userName = keyboard.nextLine();
		setUsername(userName);
		try{
		String query = "use shabbostable insert into Users (userName, Userscore) values(?,?)";
		PreparedStatement preparedStmt = Game.getConnection().prepareStatement(query);
		preparedStmt.setString(1, this.userName);
		preparedStmt.setInt(2, 0);
		preparedStmt.executeUpdate();
		preparedStmt.close();
		success = true;
		}
		catch(SQLServerException e )
		{
			System.out.println("Invalid user name. Please make sure to enter a name that does not yet exist");
		}
		}
		while(!success);
	
}
	
	public void GUICreateUser(String username) throws SQLException
	{
		setUsername(username);
		String query = "use shabbostable insert into Users (userName, Userscore) values(?,?)";
		PreparedStatement preparedStmt = Game.getConnection().prepareStatement(query);
		preparedStmt.setString(1, this.userName);
		preparedStmt.setInt(2, 0);
		preparedStmt.executeUpdate();
		preparedStmt.close();
	}
	
	
	public String getNamesAndScores() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
				
		for(int i = 0; i < retrieveUserNames().size(); i++)
		{
			sb.append(retrieveUserNames().get(i).toUpperCase());
			sb.append(": ");
			sb.append(retrieveUserScores().get(i));
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public String getGUINamesAndScores() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		
		for(int i = 0; i < retrieveUserNames().size(); i++)
		{
			sb.append(retrieveUserNames().get(i).toUpperCase());
			sb.append(": ");
			sb.append(retrieveUserScores().get(i));
			sb.append("<br>");
		}
		
		sb.append("</html>");
		
		return sb.toString();
	}
	
	private void instantiateGameRules() {
		gameRules[0] = "Males and females over age 9 cannot sit next to each other";
  		gameRules[1] = "Children under age 1 must sit next to a parent or grandparent";
  		gameRules[2] = "Any couple within the first year of marriage must sit together";
	}
	
	public void setUsername(String user)
	{
		this.userName = user;
	}
	public int getScore() { 
		int score =  this.gameScore;
		return score;
	}
	
	public Level getCurrentLevel(){
		return this.currentLevel;
	}
	
	public static Connection getConnection()
	{
		return Game.dbConnection;
	}

	public String getGameRules() {
		
		StringBuffer s = new StringBuffer();
		s.append("<html><strong>Object of the Game</em> </strong> <br>You will see a list of people, their specifications "
				+ "<br>and a table format, you must figure out a way to seat them around the table.<br> <strong> Game general rules </strong> <br>");
		for(String st : gameRules)
		{
			s.append(st);
			s.append("<br>");
		}
		s.append("</html>");
		return s.toString();
	}
	}