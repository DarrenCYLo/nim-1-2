import java.io.File; // Imports the File class to read from the file NimGameSave.txt
import java.io.FileWriter; // Imports the FileWriter class to write in the file NimGameSave.txt
import java.util.Random; // Imports the Random class for random generation
import java.util.Scanner; // Imports the Scanner class for reading user input
import java.util.Stack; // Imports Stack class to store the game of 1-2 Nim game state and undo functionality 

/**
 * @author DarrenCYLo
 * @version 12/01/2025
 * NimGame class handles the logic and the game 1-2 Nim game state management
 * Handle the game state of the game 1-2 Nim (number of marbles from 5 to 20 and player turn selection), save, load, undo and clear/reset
 */
public class NimGame
{
    private Player humanPlayer; // Instance variable that represents the human player (private keyword, Player class data type and variable)
    private Player computerPlayer; // Instance variable that represents the computer player
    private int marbleSize; // Instance variable that tracks the current number of marbles (private keyword, integer data type an variable)
    private boolean isHumanTurn; // Instance variable that tracks the current number of marbles (true for human player and false for computer player)
    private Stack<String> gameStates; // Stack to store the game of 1-2 Nim game state for undo functionality 

    /**
     * Parameterised constructor
     * @param "humanPlayer" the human player
     * @param "computerPlayer" the computer player with a selected strategy (random strategy or your strategy)
     */
    public NimGame(Player humanPlayer, Player computerPlayer) 
    {
        this.humanPlayer = humanPlayer; // Assigns the parameter "humanPlayer" to the object's properties "humanPlayer"
        this.computerPlayer = computerPlayer; // Assigns the parameter "computerPlayer" to the object's properties "computerPlayer"
        
        Random randomMarble = new Random(); // Create an instance of the Random class from the java.util.Random Java standard library to generate random numbers
        this.marbleSize = randomMarble.nextInt(16) + 5; // Randomly selects number of marbles between minimum 5 to maximum 20 
        
        Random randomPlayer = new Random(); // Create an instance to randomly select if human player or computer player plays first 
        this.isHumanTurn = randomPlayer.nextBoolean(); // nextBoolean() returns "true" or "false" randomly, "true" for human player and "false" for computer player

        this.gameStates = new Stack<>(); // Initialise the stack to store and save the game of 1-2 Nim game states
        saveState(); // Save the initial game of 1-2 Nim game state
    }

    /**
     * Getter methods
     * @return the human player
     */    
    public Player getHumanPlayer() 
    {
        return humanPlayer;
    }

    /**
     * Getter methods
     * @return the computer player
     */ 
    public Player getComputerPlayer()
    {
        return computerPlayer;
    }

    /**
     * Getter methods
     * @return the numner of marble
     */ 
    public int getMarbleSize() 
    {
        return marbleSize;
    }
    
    /**
     * Getter methods
     * @return true if it's the human player's turn
     * @return false if it's the computer player's turn
     */
    public boolean isHumanTurn() 
    {
        return isHumanTurn;
    }
    
    /**
     * Handle game of 1-2 Nim winner
     * @return true if the game of 1-2 Nim has ended
     */
    public boolean checkWinner() 
    {
        return marbleSize <= 0; // The game of 1-2 Nim ends when there is 0 marble
    }
        
    /**
     * Assigns a move by removing the number of marbles (1 marble or 2 marbles) and switching the player's turn
     * @param removeAmount the number of marbles removed (1 marble or 2 marbles)
     */ 
    public void assignMove(int removeAmount) 
    {
        marbleSize -= removeAmount; // Reduce the number of marbles by the amount removed (1 marble or 2 marbles)
        isHumanTurn = !isHumanTurn; // Switches the turn to the other player's turn
        saveState(); // Save the game of 1-2 Nim game state after each marble removal 
    }
    
    /**
     * Saves the current game of 1-2 Nim game state into the stack
     * Saves the current number of marbles and the current player's turn for undo functionality
     */
    private void saveState() 
    {
        String currentState = marbleSize + "," + (isHumanTurn ? "Human" : "Computer"); // Create a string to represent the current game state
        gameStates.push(currentState); // Push the game state onto the stack
    }

    /**
     * Saves the current state of the game to the file NimGameSave.txt
     * Saves the number of marbles, human player's or computer player's turn and computer strategy in use
     */     
    public void saveGame() 
    {
        try 
        {
            FileWriter writer = new FileWriter("NimGameSave.txt"); // Create a FileWriter object to write to the file
            for (String state : gameStates) // Write all game of 1-2 Nim game state from the stack to the file NimGameSave.txt
            {
                writer.write(state + "\n"); // Write each game of 1-2 Nim game state followed by a new line in the file NimGameSave.txt
            }
            writer.close(); // Close the writer to save the file NimGameSave.txt
        } 
        catch (Exception e) 
        {
            System.out.println("An error occurred while saving the game");
        }
    }

    /**
     * Loads the latest saved game of 1-2 Nim game state from file NimGameSave.txt
     * Updates the game of 1-2 Nim game state (number of marbles and player's turn)
     */ 
    public void loadGame() 
    {
        try 
        {
            File file = new File("NimGameSave.txt"); // Create a File object to read from the file
            Scanner scanner = new Scanner(file);
            gameStates.clear(); // Clear the stack before loading
            while (scanner.hasNextLine()) // Read each line from the file NimGameSave.txt and push it onto the stack
            {
                String line = scanner.nextLine();
                gameStates.push(line);
            }
            scanner.close(); // Close the scanner
            if (!gameStates.isEmpty()) // Pop the latest game of 1-2 Nim game state from the stack
            {
                String latestState = gameStates.peek(); // Load the game of 1-2 Nim game state
                String[] parts = latestState.split(","); // Split the state into parts

                marbleSize = Integer.parseInt(parts[0]); // Load the game of Nim 1-2 number of marbles
                isHumanTurn = parts[1].equals("Human"); // Load the game of Nim 1-2 player's turn
            } 
            else 
            {
                System.out.println("No saved Game of Nim 1-2 found");
            }
        } 
        catch (Exception e) 
        {
            System.out.println("An error occurred while loading the game");
        }
    }

    /**
     * Undo the last move by restoring the previous game of 1-2 Nim game state from the stack
     * Removes the most recent state and sets the game of 1-2 Nim game to the prior game state
     */
    public void undoLastMove() 
    {
        if (gameStates.size() > 1) // Ensure there is a previous state to undo to
        {
            gameStates.pop(); // Remove the most recent the game of 1-2 Nim game state
            String previousState = gameStates.peek(); // Get the previous game of 1-2 Nim game state
    
            String[] parts = previousState.split(","); // Update the game of 1-2 Nim game state
            marbleSize = Integer.parseInt(parts[0]); // Restore the numer of marbles in game of 1-2 Nim game state
            isHumanTurn = parts[1].equals("Human"); // Restore player's turn in game of 1-2 Nim game state
        } 
        else // Prevent popping/undoing beyond the initial game of 1-2 Nim game state
        {
            System.out.println("No moves to undo");
        }
    }

    /**
     * Resets computer strategy to enable user to select random strategy or your strategy 
     * Resets the number of marbles between minimum 5 to maximum 20 
     * Resets if player turn selection
     */
    public void resetGame() 
    {
        Scanner reader = new Scanner(System.in); // Create a Scanner object to read user input
    
        String gameMode = reader.nextLine().toUpperCase();
        MoveStrategy computerStrategy;

        if (gameMode.equals("R")) 
        {
            computerStrategy = new RandomStrategy();
        } 
        else if (gameMode.equals("Y")) 
        {
            computerStrategy = new YourStrategy();
        } 
        else 
        {
            return; // Invalid input; do nothing
        }

        computerPlayer.setStrategy(computerStrategy); // Apply the computer's strategy (Random or Your Strategy)
    
        Random randomMarble = new Random(); // Randomize the number of marbles
        this.marbleSize = randomMarble.nextInt(16) + 5;

        Random randomPlayer = new Random(); // Randomize who plays first
        this.isHumanTurn = randomPlayer.nextBoolean();
    }
}