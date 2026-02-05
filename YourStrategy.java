/**
 * @author DarrenCYLo
 * @version 19/01/2024
 * YourStrategy class handles the MoveStrategy interface for a computer player's marble removal that alternates between 1 marble removal and then 2 marbles removal on consecutive turns
 */
public class YourStrategy implements MoveStrategy 
{
    private int counter = 0; // Counter to alternate between 1 marble removal or 2 marbles removal

    /**
     * Parameterised constructor to determines the computer player's move using yur strategy that is remove 1 marble removal, then 2 marbles removal and the cycle repeats and adjusted to not over remove when there is 1 marble remaining
     * @param "currentPileSize" the current number of marbles
     * @return the number of marbles the computer player removes (1 or 2)
     */
    @Override
    public int NextMove(int currentPileSize) // Add the required parameter
    {
        int move; // Variable to store the number of marbles to remove
        if (counter == 0) // Alternate marble removal based on the counter value.
        {
            move = 1; // Your strategy computer player remove 1 marble on this turn
            counter = 1; // Update counter for the next turn
        } 
        else 
        {
            move = 2; // Your strategy computer player remove 2 marbles on this turn
            counter = 0; // Update counter for the next turn
        }
        if (move > currentPileSize) // Ensure the alternating 1 marble and 2 marbles removal does not over remove the remaining marbles
        {
            move = currentPileSize; // Adjusts marble removal to force only the remaining marble is removed
        }
        return move; // Return the valid number of marbles to remove
    }
}