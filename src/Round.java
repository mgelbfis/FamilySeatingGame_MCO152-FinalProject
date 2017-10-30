import java.sql.SQLException;
import java.time.LocalTime;

public class Round {

	private Puzzle puzzle;
	private int levelNum;
	private int roundNum;
	private int regSeconds; // how much time it should take
	private int tryNum;
	private int roundScore; // for this round. Level will add this to level's score at the end of the round. At the end of the level, Game will add it to its score
	private LocalTime startTime;
	private int solutionNum;

	public Round(int level, int round) {
		this.roundNum = round;
		this.levelNum = level;
		this.tryNum = 1; 
		roundScore = 100; //this is the base score
		puzzle = new Puzzle(generateRoundID());
	}
	
	public String playRound() throws SQLException
	{
		startTime = LocalTime.now();
		puzzle.play();
		return puzzle.toString() + "\n" + toString();
	}
	
	public String generateRoundID()
	{
		String puzNum = "" + levelNum + "" + roundNum;
		return puzNum;
	}

	public boolean afterPuzzle(String answer, LocalTime endTime)
	{
		if(checkSolution(answer))
		{
			calculateScore(endTime);
			return true;
		}
		else
		{
			tryNum ++;
			return false;
		}
	}
	
	public boolean checkSolution(String answer) {
		for(int i = 0; i < puzzle.getSolutions().size(); i++){
			if(answer.equals(puzzle.getSolutions().get(i))){
				this.solutionNum = i;
				return true;
			}
		}

		return false;
	}

	public void calculateScore(LocalTime endTime)
	{
		//Part 1: Based on Level
		roundScore *= levelNum;
		
		//Part 2: Based on Solution
		roundScore += (puzzle.getNumPrefsMet(solutionNum) * 10);
		
		//Part 3: Based on TryNum
		roundScore -= ((tryNum-1) * 5);
		
		//Part 4: Based on Time
		int timing = regSeconds - calculateSeconds(endTime);
		roundScore += timing * 5; //if timing is positive, will gain points; if negative, will lose points
		
	}
	
	public int calculateSeconds(LocalTime endTime) //this will be passed in along with the solution
	{
		return (startTime.toSecondOfDay() - endTime.toSecondOfDay()); //what happens if play from 11:59 at night to 12:02???
	}
	
	public int getRoundNum() {
		return roundNum;
	}

	public int getLevelNum() {
		return this.levelNum;
	}
	
	public int getRoundScore()
	{
		return this.roundScore;
	}
	
	//for GUI
	public Puzzle getPuzzle()
	{
		return this.puzzle;
	}

	public int getTryNum()
	{
		return this.tryNum;
	}

	public String toString()
	{
		return "\n\nEnter your solution starting from the left head, moving clockwise."
				+ "\nUse the person's id to reference him/her"
					+ " \nMake sure to enter a - between each number. Sample input: 2-5-6-1-3-4";
	}
}
