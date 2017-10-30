import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class GameGUI extends JFrame {

	private JPanel menuPanel;
	private JPanel gamePanel;
	private JLabel welcomeLabel;
	private JLabel menuLabel;
	private JLabel gameLabel;
	private JMenuItem newGame;
	private JMenuItem savedGame;
	private JMenuItem exit;
	private JLabel gameRules;
	private JLabel score;
	private JLabel lblUsersAndScores;
	private JLabel usersAndScores;
	private JTable people;
		
	public static Game game;
	private JTable table;

	public GameGUI() throws SQLException{
		GameGUI.game = new Game();
		//game.GUIPlayGame();
		menuPanel = new JPanel();
		gamePanel = new JPanel();
		welcomeLabel = new JLabel("Welcome to Shabbos Table");
		gameLabel = new JLabel("THE SHABBOS TABLE");
		newGame = new JMenuItem("Play New Game");
		savedGame = new JMenuItem("Open Saved Game (Future Feature)");
		exit = new JMenuItem("Exit");
		gameRules = new JLabel(game.getGameRules());
		score = new JLabel("Score: " + game.getScore());
		lblUsersAndScores = new JLabel("USERS AND SCORES");
		usersAndScores = new JLabel(game.getGUINamesAndScores());
		usersAndScores.setVerticalAlignment(SwingConstants.TOP);
		menuLabel = new JLabel("Please Select an Item From the Menu");
		people = new JTable();
		
	//	setUpMenu();
		setUpGame();
		
			}	
	
	public void setUpMenu()
	{
		setTitle("Shabbos Table Menu");
		
		menuPanel.setForeground(Color.DARK_GRAY);
		menuPanel.setBackground(new Color(255, 255, 153));
	    getContentPane().add(menuPanel, BorderLayout.CENTER);
	    menuPanel.setLayout(null);
			
		
	    welcomeLabel.setBounds(400, 25, 600, 30);
	    welcomeLabel.setForeground(Color.DARK_GRAY);
	    welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    welcomeLabel.setFont(new Font("Algerian", Font.PLAIN, 40));
	    menuPanel.add(welcomeLabel);
	    
	    menuLabel.setBounds(80, 100, 550, 40);
	    menuLabel.setBackground(new Color(255, 255, 153));
	    menuLabel.setForeground(Color.DARK_GRAY);
	    menuLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 30));
	    menuPanel.add(menuLabel);
	    
	    setUpMenuItem(newGame);
	    newGame.setBounds(77, 150, 550, 40);
	    newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					menuPanel.setVisible(false);
					setUpGame();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Cannot connect to database. \nPlease contact 347-638-4683", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	    setUpMenuItem(savedGame);
	    savedGame.setBounds(77, 200, 550, 40);
	 //   savedGame.addActionListener(new RemoveListener(mySB));
	    
	    setUpMenuItem(exit);
	    exit.setBounds(77, 250, 550, 40);
	
	    setUpDisplayUsersAndScores();
	    
	    setSize(1300,800);
	    this.setLocationRelativeTo(null);
	    setVisible(true);
	}
	
	public void setUpMenuItem(JMenuItem item)
	{
	    item.setBorder(new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, null, null, null));
	    item.setBackground(new Color(255, 255, 153));
	    item.setForeground(Color.DARK_GRAY);
	    item.setFont(new Font("Segoe UI Black", Font.PLAIN, 30));
	    menuPanel.add(item);
	}
	
	public void setUpDisplayUsersAndScores()
	{

	    lblUsersAndScores.setBounds(800, 100, 550, 40);
	    lblUsersAndScores.setBackground(new Color(255, 255, 153));
	    lblUsersAndScores.setForeground(Color.DARK_GRAY);
	    lblUsersAndScores.setFont(new Font("Segoe UI Black", Font.PLAIN, 30));
	    menuPanel.add(lblUsersAndScores);
	    
	    usersAndScores.setBounds(800, 150, 460, 800);
	    usersAndScores.setForeground(Color.DARK_GRAY);
	    usersAndScores.setHorizontalAlignment(SwingConstants.LEFT);
	    usersAndScores.setFont(new Font("Arial", Font.PLAIN, 22));
	    menuPanel.add(usersAndScores);
	}
	public void setUpGame() throws SQLException
	{
		setTitle("Game");
		
		gamePanel.setForeground(Color.DARK_GRAY);
		gamePanel.setBackground(new Color(255, 255, 153));
	    getContentPane().add(gamePanel, BorderLayout.CENTER);
	    gamePanel.setLayout(null);
	
		gameLabel.setBounds(420, 13, 427, 24);
	    gameLabel.setForeground(Color.DARK_GRAY);
	    gameLabel.setHorizontalAlignment(SwingConstants.LEFT);
	    gameLabel.setFont(new Font("Algerian", Font.PLAIN, 22));
	    gamePanel.add(gameLabel);
	    
	    gameRules.setBounds(22,33,500,180);
	    gameRules.setForeground(Color.DARK_GRAY);
	    gameRules.setHorizontalAlignment(SwingConstants.LEFT);
	    gameRules.setFont(new Font("Arial", Font.PLAIN, 18));
	    gamePanel.add(gameRules);
	    
	    score.setBounds(0,630,250,50);
	    score.setForeground(Color.DARK_GRAY);
	    score.setHorizontalAlignment(SwingConstants.LEFT);
	    score.setFont(new Font("Arial", Font.PLAIN, 18));
	    gamePanel.add(score);
	    
	    boolean success = false;
	    do{
	    try{
	    	String userButton = JOptionPane.showInputDialog(null, "Enter username");
	    	if(userButton == null){ JOptionPane.getRootFrame().setVisible(false); break;} //either do this whole zach or just do system.exit and close game
	    	game.GUICreateUser(userButton);
	    	success = true;
	   	    }
	    catch(SQLException e){
	    	JOptionPane.showMessageDialog(null, "Invalid user name. Please make sure to enter a name that does not yet exist", "Error Creating User", JOptionPane.ERROR_MESSAGE);
	    }
    
	    }
	    while(!success);
	    JLabel getUserName = new JLabel(game.getCurrentLevel().getCurrentRound().playRound());
	    getUserName.setBounds(10, 150, 46, 14);
	    gamePanel.add(getUserName);
	    
    
	    setUpPeople();
	    
	    setSize(1300,800);
	    this.setLocationRelativeTo(null);
	    setVisible(true);
	}
	
	private void setUpPeople()
	{
		int i = 1;
		ArrayList<JLabel> pLabels= new ArrayList<JLabel>();
		for(Person p : game.getCurrentLevel().getCurrentRound().getPuzzle().getPeople())
		{
			pLabels.add(createNewPersonLabel(p));
			
		}
	    table = new JTable();
	    table.setBounds(439, 360, -397, -105);
	    gamePanel.add(table);
		for(JLabel l : pLabels)
		{
			l.setBounds(100*i, 5000, 800, 500);
		    l.setForeground(Color.DARK_GRAY);
		    l.setHorizontalAlignment(SwingConstants.LEFT);
		    l.setFont(new Font("Arial", Font.PLAIN, 18));
		    i++;
		    gamePanel.add(l);
		}
	}
	
	private JLabel createNewPersonLabel(Person p)
	{ 
		return new JLabel(p.toString());
	}
}

