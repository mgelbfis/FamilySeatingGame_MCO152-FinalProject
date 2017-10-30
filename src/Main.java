import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws SQLException {
		//GameGUI gui = new GameGUI();
		Scanner keyboard = new Scanner(System.in);
		int choice = displayMenu(keyboard);
		Game g = createGame(choice, keyboard);
		try {
			g.playGame();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Error. To resolve this issue, please call 347-638-4683");
		}
	}

	public static int displayMenu(Scanner keyboard) {
		int choice;

		do 
		{
			System.out.println("Welcome to Shabbos Table!"
					+ "\nPlease select an option from the menu"
					+ "\n1. Start a New Game" 
					+ "\n2. Continue Saved Game (feature not yet available)");

			
			choice = keyboard.nextInt();
		}
		while (choice != 1 && choice != 2);

		return choice;

	}
	
	public static Game createGame(int choice, Scanner keyboard)
	{
		Game g = null;
		switch(choice){
		case 1: 
			try {
				g = new Game();
			} catch (SQLException e) {
				System.out.println("Unable to connect to database. To resolve this issue, please call 347-638-4683");
			}
			break;
		case 2:
			System.out.println("Enter your username");
			g = new Game(keyboard.nextLine());
			break;
		}
		return g;
	}
}
