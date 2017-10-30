import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Scanner;

public class Level {

	private int levelNum;
	private Round currentRound;
	private int levelScore;
	
	private static int numRounds = 1; //CHANGED
	private final int ROUND_MAX_TRIES = 3;
	
	public Level(int level) throws SQLException {
		this.levelNum = level;
		this.currentRound = new Round(levelNum, 1);
	}

	public void playLevel() throws SQLException
	{
		Scanner keyboard = new Scanner(System.in);
		while(currentRound.getRoundNum() <= numRounds)
		{
			boolean roundWon = false;
			do{
				System.out.println(currentRound.playRound());
				String answer = keyboard.nextLine();
				LocalTime endTime = LocalTime.now();
				roundWon = currentRound.afterPuzzle(answer, endTime);
			}
			while(roundWon == false && currentRound.getTryNum() <=  ROUND_MAX_TRIES);
			if(roundWon == false)
			{
				System.out.println("You will be demoted");
				if(currentRound.getRoundNum() != 1)
				currentRound = new Round(levelNum, currentRound.getRoundNum()-1);
			}
			else
			{
				levelScore += currentRound.getRoundScore();
				currentRound = new Round(levelNum, currentRound.getRoundNum()+1);
			}
			
		}
	}

	public void GUIPlayLevel() throws SQLException
	{
		while(currentRound.getRoundNum() <= numRounds)
		{
			boolean roundWon = false;
			do{
				currentRound.playRound();
				Scanner keyboard = new Scanner(System.in);
				String answer = keyboard.nextLine();
				LocalTime endTime = LocalTime.now();
				roundWon = currentRound.afterPuzzle(answer, endTime);
			}
			while(roundWon == false && currentRound.getTryNum() <=  ROUND_MAX_TRIES);
			levelScore += currentRound.getRoundScore();
			
			currentRound = new Round(levelNum, currentRound.getRoundNum()+1);
		}
	}
	public int getLevelScore()
	{
		return this.levelScore;
	}
	
	public int getLevelNum(){
		return this.levelNum;
	}

	public Round getCurrentRound(){
		return this.currentRound;
	}
	
	public int getNumRounds()
	{
		return Level.numRounds;
	}

}
