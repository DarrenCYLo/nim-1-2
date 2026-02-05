/**
 * @author DarrenCYLo
 * @version 18/01/2025
 * The Player class handles a human player and computer player in the Game of 1-2 Nim 
 * Handles encapsulation of the computer player's name and stragegy for marble removal
 */
public class Player 
{
    private String name; // Instance variable that represents the name of the human player and computer player (private keyword, data type and variable)
    private MoveStrategy strategy; // The strategy the player uses to decide their moves (null/nothing for a human player)
    
    /**
     * Parameterised constructor to create a human player with only a name
     * @param "name" the name of the human player
     */
    public Player(String name) 
    {
        this.name = name; // Assign the user's name as "Human player"
        this.strategy = null; // null/nothing strategy as it is the human player will determine the strategy of marble removal
    }
    
    /**
     * Parameterised constructor to create  player with a name and a strategy
     * This is used for computer players who need a predefined move strategy
     * @param "name" the name of the human player
     * @param "strategy" the strategy used by the computer player to remove marbles
     */
    public Player(String name, MoveStrategy strategy) 
    {
        this.name = name; // Assign the computer' name as "Computer player"
        this.strategy = strategy; // Assign the computer player's strategy (random strategy or computer stratgegy)
    }
    
    /**
     * Getter methods
     * @return the name of the human player or computer player
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * Getter methods
     * @return the player's strategy (null for human player and MoveStrategy for computer player) to remove marbles
     */
    public MoveStrategy getStrategy() 
    {
        return strategy;
    }
    
    /**
     * Getter methods
     * Determines the player's marble removal, user input determines the human player's marble removal and assigned strategy (random strategy or your strategy) determines computer player's marble removal
     * @param "currentPileSize" the current number of marbles 
     * @return "the number of marbles removed (1 marble or 2 marbles)" decided by the player's strategy
     */
    public int getMove(int currentPileSize) 
    {
        return strategy.NextMove(currentPileSize); // Pass the current pile size
    }
    
    /**
     * Updates the strategy for the computer player
     * @param "strategy" the new strategy to assign to the computer player
     */
    public void setStrategy(MoveStrategy strategy) 
    {
        this.strategy = strategy;
    }
}