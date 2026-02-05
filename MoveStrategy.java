/**
 * @author DarrenCYLo
 * @version 19/01/2024
 * MoveStrategy class interface handles different move strategies in the game of 1-2 Nim,
 * HumanStrategy class, RandomStrategy class and YourStrategy class implement this interface to define how human player and computer player removes 1 marble or 2 marbles
 */
public interface MoveStrategy 
    /**
     * Determines the number of marbles removed in the game of 1-2 Nim and imlementation of this method is determined by user input/HumanUserStrategy, RandomStrategy and YourStrategy
     * @param "currentPileSize" the current number of marbles 
     * @return the number of marbles to remove 1 marble or 2 marbles
     */
{
    int NextMove(int currentPileSize); // Declares a method to determine 1 marble or 2 marbles removal
}