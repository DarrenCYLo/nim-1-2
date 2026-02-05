import java.util.Scanner; // Imports the Scanner class for reading user input

/**
 * @author DarrenCYLo
 * @version 12/01/2025
 * TextBasedUI class Handles a text-based interface to interact with the game of Nim 1-2 through text-based commands
 * Allows players to select a computer strategy, perform the functions: Make a move, Save, Load, Undo, Clear (reset) and Quit 
 */
public class TextBasedUI 
{ 
    private NimGame game; // Attribute that contains the game of Nim 1-2 logic
    private Scanner reader; // Attribute to read user input from the console

    /**
     * Constructor
     * Initialises the game of 1-2 Nim by printing out "Choose a computer strategy"and creating Player objects for the game of 1-2 Nim
     */
    public TextBasedUI() 
    {
        reader = new Scanner(System.in); // Create a scanner to read user input
        
        System.out.println("The Game of 1-2 Nim Assessment!");
        System.out.println("------------------------------");
        System.out.println("Choose a computer strategy:");
        System.out.println("[R] Random\n" +
                           "[Y] Your Strategy");

        String gameMode = reader.nextLine().toUpperCase(); // Reads the user's input strategy choice (Random Strategy or Computer Strategy)
        MoveStrategy computerStrategy; // The strategy (Random Strategy or Computer Strategy) to be used by the computer payer)

        if (gameMode.equals("R")) 
        {
            computerStrategy = new RandomStrategy(); // Random Strategy is played if user selects "R" Random Strategy
            System.out.println("You selected Random Computer strategy.");
        } 
        else if (gameMode.equals("Y")) 
        {
            computerStrategy = new YourStrategy(); // Your Strategy is played if user selects "Y" Your Strategy 
            System.out.println("You selected Your Computer strategy.");
        } 
        else 
        {
            System.out.println("Invalid option. Exiting."); // Exits the game of 1-2 Nim if the user input is invalid (user must inputer "R" of "Y")
            return;
        }

        Player player1 = new Player("Human", new HumanUserStrategy()); // Create human player
        Player player2 = new Player("Computer", computerStrategy); // Create computer player

        this.game = new NimGame(player1, player2); // Initialise the NimGame object with the players
        startGame(); // Start the game of 1-2 Nim loop
    }

    /**
     * Starts the game of 1-2 Nim and manages the human player's and computer player's turn
     */
    private void startGame() 
    {     
        if (game.isHumanTurn()) // Check if the human player plays first
        {
            System.out.println("First turn: Human Player");
        } 
        else // Check if the computer player plays first
        {
            System.out.println("First turn: Computer Player");
        }
        
        System.out.println("\nInitial number of marbles: " + game.getMarbleSize());  // Show the initial number of marbles
        
        displayMarbles(); // Show marbles visually using "@"

        while (!game.checkWinner()) 
        {
            displayMenu(); // Continue showing the menu until the game of 1-2 Nim ends
        }

        announceWinner(); // Announce the game of 1-2 Nim winner
    }

    /**
     * Displays the menu options for the user nad handles functionalities
     */
    private void displayMenu() // Show menu options
        {  
        System.out.println("\nChoose an option: \n"
            + "[M] Make a move\n"
            + "[S] Save game\n"
            + "[L] Load saved game\n"
            + "[U] Undo move\n"
            + "[C] Clear game\n"
            + "[Q] Quit game\n");

        String choice = reader.nextLine().toUpperCase(); //Read the user's input
        switch (choice) // Handle the user's input using switch statement
        {
            case "M":
                makeMove(); // "M" handles "Make a move"
                break;
            case "S":
                System.out.println("The Game of Nim 1-2 is saved!");
                game.saveGame(); // "S" handles "Save game"
                break;
            case "L":
                System.out.println("The Game of Nim 1-2 is loaded!");
                System.out.println("It is " + (game.isHumanTurn() ? "Human's" : "Computer's") + " turn to play");
                displayMarbles(); // Displays marbles from loaded game of 1-2 Nim
                game.loadGame(); // "L" handles "Load saved game"
                break;
            case "U":
                System.out.println("Undo last move!");
                System.out.println("It is " + (game.isHumanTurn() ? "Human's" : "Computer's") + " turn to play");
                displayMarbles(); // Displays number of marbles after undo
                game.undoLastMove(); // "U" handles "Undo move"
                break;
            case "C":
                System.out.println("The Game of 1-2 Nim has been reset!");
                System.out.println("------------------------------");
                System.out.println("Choose a computer strategy:");
                System.out.println("[R] Random\n" +
                                   "[Y] Your Strategy");

                game.resetGame(); // "C" handles "Clear game" (reset)
                
                if (game.getComputerPlayer().getStrategy() instanceof RandomStrategy) 
                {
                System.out.println("You selected Random Computer strategy.");
                } 
                else 
                {
                System.out.println("You selected Your Computer strategy.");
                }
                System.out.println(" ");
                
                System.out.println(game.isHumanTurn() ? "First turn: Human Player" : "First turn: Computer Player");
                System.out.println("Initial number of marbles: " + game.getMarbleSize());
                displayMarbles(); // Displays number of marbles after reset
                break;
            case "Q":
                System.out.println("Thank you for playing! Exiting game...");
                System.exit(0); // "Q" handles "Quit game" and exits the game of 1-2 Nim program
                break;
            default:
                System.out.println("Invalid choice. Please select again.");
        }
    }

    /**
     * Handles making a move function for the human player and computer player
     */ 
    private void makeMove() 
    {
        if (game.isHumanTurn()) // Check if it's the human player's turn
        {
            assignMoveFrom(game.getHumanPlayer()); // Handle the human player's move
            if(!game.checkWinner()) // Computer player's turn to play if the game of 1-2 Nim hasn't ended
                assignMoveFrom(game.getComputerPlayer()); // Handle the computer player's move
        } 
        else 
        {
            assignMoveFrom(game.getComputerPlayer()); // Handle the computer player's move
            if(!game.checkWinner()) // Human player's turn to play if the game of 1-2 Nim hasn't ended
                assignMoveFrom(game.getHumanPlayer()); // Handle the human player's move
        }
    }
        
     /**
     * Assigns a move for the human player or computer player and updates the game of 1-2 Nim state
     * @param "player" the human player or computer player making a move
     */
    private void assignMoveFrom(Player player) 
    {
        System.out.println("\nIt is " + player.getName() + "'s turn to play.");
        int move = player.getMove(game.getMarbleSize()); // Get the human players' or computer player's move
        game.assignMove(move); // apply the human players' or computer player's move to the game of 1-2 Nim state
        System.out.println(player.getName() + " takes " + move + " marbles.");
        displayMarbles(); // updates the number of marbles visually using "@"
    }
    
    /**
     * Displays the current number of marbles in the game of 1-2 Nim using "@"
     */
    private void displayMarbles() 
    {
        System.out.println("Current number of marbles: " + game.getMarbleSize());
        for (int i = 0; i < game.getMarbleSize(); i++) 
        {
            System.out.print("@ ");
            if ((i + 1) % 10 == 0) // Prints out a newline every 10 marbles "@"
            {
                System.out.println();
            }
        }
        System.out.println();
    }
    
    /**
     * Announces human playeer or computer player as the winner when the game of 1-2 Nim ends.
     */
    private void announceWinner() 
    {
        String winnerName;
        if (game.isHumanTurn())
            winnerName = game.getComputerPlayer().getName(); //Computer player wins
        else
            winnerName = game.getHumanPlayer().getName(); //Human player Wins
        System.out.println("*** " + winnerName + " is the winner! ***");
    }

    /**
     * Method to create a new TextBasedUI object that starts the game of 1-2 Nim
     */
    public static void main(String[] args) 
    {
        TextBasedUI textUi = new TextBasedUI(); // Start the game of 1-2 Nim
    }
}