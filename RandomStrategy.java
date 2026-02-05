import java.util.Random; // Imports the Random class for random generation

/**
 * @author DarrenCYLo
 * @version 12/01/2025
 * The RandomStrategy class handles the MoveStrategy interface for a computer player's marble removal when using random strategy that randomly remove 1 marble or 2 marbles
 */
public class RandomStrategy implements MoveStrategy 
{
    private Random random = new Random(); // Random object for generating random numbers
    
    /**
     * Method to determine the computer player's move using a random strategy that randomly removes 1 marble or 2 marbles and does not over remove when there is 1 marble remaining
     * @param currentPileSize The current number of marbles in the pile.
     * @return The number of marbles the computer chooses to remove (1 or 2), adjusted if necessary to ensure a valid move.
     */
    @Override
    public int NextMove(int currentPileSize) // Add the required parameter
    {
        int move = random.nextInt(2) + 1; // Randomly choose 1 or 2 marbles
        if (move > currentPileSize) // Ensure the random marble removal does not over remove the remaining marbles
        {
            move = currentPileSize; // Adjusts marble removal to force only the remaining marble is removed
        }
        return move; // Return the valid number of marbles to remove
    }
}