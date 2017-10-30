	import static org.junit.Assert.*;

	import java.sql.SQLException;
	import java.time.LocalDate;
	import java.time.LocalTime;
	import java.util.ArrayList;

	import org.junit.Before;
	import org.junit.Test;

	public class RoundTests {

	 private Round r;
	 
	 @Before
	 public void beforeTest()
	 {
	  r = new Round(1,1);
	 }
	 
	 
	 @Test
	 public void newRoundIsOnTryOne()
	 {
	  assertEquals(1,r.getTryNum());
	 }
	 
	 @Test
	 public void newRoundHasScore100()
	 {
	  assertEquals(100, r.getRoundScore());
	 }
	 
	 @Test
	 public void generateRoundIDConcatenatesCorrectly()
	 {
	  assertEquals("11", r.generateRoundID());
	 }
	 
	 @Test 
	 public void playRoundSetsUpCorrectPuzzle() throws SQLException
	 {
	  r.playRound();
	  assertEquals(new Puzzle("11"), r.getPuzzle());
	 }
	 
	 //no way to test start time because will always be a second off
	 
	 @Test
	 public void checkSolutionReturnsTrueForCorrectAnswer() throws SQLException
	 {
	  r.playRound();
	  assertTrue(r.checkSolution("1-12-13-11-2-14-9-10"));
	 }
	 
	 @Test
	 public void checkSolutionReturnsFalseForWrongAnswer() throws SQLException
	 {
	  r.playRound();
	  assertFalse(r.checkSolution("1-2-13-11-12-14-10-9"));
	 }
	 
	 @Test
	 public void afterPuzzleAddsToTriesIfWrongAnswer() throws SQLException
	 {
	  r.playRound();
	  r.afterPuzzle("1-2-13-11-12-14-10-9", LocalTime.now());
	  r.afterPuzzle("2-1-13-11-12-14-10-9", LocalTime.now());
	  assertEquals(3, r.getTryNum());
	 }
	 
	 @Test
	 public void calculateScoreReturns100MultipliedByLevelIfNoConsiderations()
	 {
	  r.playRound();
	  //solution with no numPrefsMet
	  r.checkSolution(answer);
	  //try num is 1 because was just instantiated
	  //end time has to be reg time
	  r.calculateScore(endTime);
	  assertEquals(100, r.getRoundScore());
	 }
	 
	 @Test
	 public void calculateScoreReturnsCorrectScoreWithPrefsMet()
	 {
	  r.playRound();
	  //numPrefsMet for this answer is 2
	  r.checkSolution(answer);
	  //try num is 1 because was just instantiated
	  //end time has to be reg time
	  r.calculateScore(endTime);
	  assertEquals(120, r.getRoundScore());
	 }
	 
	 @Test
	 public void calculateScoreReturnsCorrectScoreTry3() throws SQLException
	 {
	  r.playRound();
	  //numPrefsMet for this answer is 2
	  r.afterPuzzle("1-2-13-11-12-14-10-9", LocalTime.now().plusSeconds(300));
	  r.afterPuzzle("2-1-13-11-12-14-10-9", LocalTime.now().plusSeconds(500));
	  r.afterPuzzle("1-12-13-11-2-14-9-10", LocalTime.now().plusSeconds(560));
	  assertEquals(90, r.getRoundScore());
	 } 
 
}
