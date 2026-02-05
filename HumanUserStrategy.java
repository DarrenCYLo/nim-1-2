import java.util.Scanner; // Imports the Scanner class for reading user input

/**
 * @author DarrenCYLo
 * @version 19/01/2025
 * HumanUserStrategy class handles the MoveStrategy interface for a human player's marble removal by user input (1 or 2) and ensuring the input is valid
 */
public class HumanUserStrategy implements MoveStrategy 
{
    private Scanner reader;// Instance variable to read user input from the console
    
    /**
     * Constructor to initialise the Scanner object for user input
     */
    public HumanUserStrategy() 
    {
        reader = new Scanner(System.in); // Create a new Scanner instance for reading input from the console.
    }
    
    /**
     * Method to prompt human player "how many marbles to remove (1 or 2)"
     * Ensures that only 1  marble or 2 marbles is validly removed and does not over remove when there is 1 marble remaining
     * @param "currentPileSize" the current number of marbles
     * @return the number of marbles the player chooses to remove (1 marble or 2 marbles)
     */
    @Override
    public int NextMove(int currentPileSize) // Accept current number of marbles as the parameter
    {
        int move = 0; // Initialise the variable to store the player's move
        boolean validMove = false; // Flag to check if the user input is valid

        while (!validMove) // while loop to keep asking for user input until a valid move is entered (1 marble or 2 marbles)
        {
            System.out.print("How many marbles do you want to remove? (1 or 2): ");
            move = reader.nextInt(); // Read the user's input
            
            if (move == 1 || move == 2) // Forces the human player to remove 1 marble or 2 marbles
            {
                if (move <= currentPileSize) // Forces the human player to not over remove the number of marbles remaining
                {
                    validMove = true; // Valid 1 marble or 2 marbles removal exits the while loop
                } 
                else 
                {
                    System.out.println("Oi! You can't remove more marbles than what's left!");
                }
            } 
            else 
            {
                System.out.println("Oi! You can only remove 1 or 2 marbles!");
            }
        }
        return move; // Return the valid number of marbles to remove
    }
}