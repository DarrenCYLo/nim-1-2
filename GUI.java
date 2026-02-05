import javax.swing.*; // Imports all Swing components, JFrame, JPanel, JLabel, JRadioButton, ButtonGroup, JButton and JOptionPane are used to create the GUI
import java.awt.*; // Imports the Abstract Window Toolkit package, BorderLayout, GridLayout, FlowLayout are used to manage GUI design.layout
import java.awt.event.ActionEvent; // Imports the ActionEvent class that represents an event triggered by user selecting radio buttons and clicking Jbuttons
import java.awt.event.ActionListener; // Imports the ActionListener interface that listens and handles user selecting radio buttons and clicking Jbuttons

/**
 * @author DarrenCYLo
 * @version 20/01/2025
 * GUI class provides a graphical user interface for the Game of 1-2 Nim.
 * It allows players to interact with the game visually and perform actions such as selecting strategies, removing marbles, undoing moves, saving, loading, and resetting the game.
 */
public class GUI extends JFrame 
{
    private NimGame game; // Instance variable that represents the game of1-2 Nim game logic and game state management (private keyword, NimGame class data type and variable)
    private JPanel marblePanel; // Panel to show the marbles visually as "●"
    private JLabel turnLabel; // Label to show the current player's turn
    private JLabel marbleCountLabel; // Label to show the current number of marbles
    private JRadioButton randomStrategyRadio, yourStrategyRadio; // Radio buttons for selecting the computer's strategy
    private JButton removeOneButton, removeTwoButton, undoButton, saveButton, loadButton, resetButton; // Buttons for functions

    /**
     * Constructor
     * Initialise the main GUI layout, components and defines action listeners for user interactions.
     */
    public GUI() // Sets the main GUI frame
    {
        setTitle("The Game of 1-2 Nim Assessment"); // Window title
        setSize(550, 400); // Dimensions of the window
        setLayout(new BorderLayout()); // BorderLayout for the main layout
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Exits the game of 1-2 Nim when the window is closed

        // Top Panel: Strategy Selection
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout to align components in a single row
        JLabel modeLabel = new JLabel("Select Computer Strategy:"); // Label for strategy selection
        randomStrategyRadio = new JRadioButton("Random Strategy"); // Default selection: Random Strategy
        yourStrategyRadio = new JRadioButton("Your Strategy"); // Alternative selection: Your Strategy

        ButtonGroup strategyGroup = new ButtonGroup(); // Group the radio buttons so only one can be selected
        strategyGroup.add(randomStrategyRadio);
        strategyGroup.add(yourStrategyRadio);

        // Add components to the top panel
        topPanel.add(modeLabel); // Add the label to the top panel
        topPanel.add(randomStrategyRadio); // Add the "Random Strategy" radio button to the top panel
        topPanel.add(yourStrategyRadio); // Add the "Your Strategy" radio button to the top panel

        add(topPanel, BorderLayout.NORTH); // Add the top panel to the north region of the layout


        // Middle Panel: Marble Display
        JPanel middlePanel = new JPanel(new BorderLayout()); // Panel to display turn, marble count, and marble display

        // Panel for Turn and Marble Count
        JPanel infoPanel = new JPanel(new GridLayout(2, 1)); // Use GridLayout to stack "Turn" and "Marble Count" labels
        turnLabel = new JLabel("Turn: ", SwingConstants.CENTER); // Label for the current turn
        marbleCountLabel = new JLabel("Current number of marbles: ", SwingConstants.CENTER); // Label for the marble count

        infoPanel.add(turnLabel); // Add the turn label to the info panel
        infoPanel.add(marbleCountLabel); // Add the marble count label below the turn label

        marblePanel = new JPanel(); // Panel to visually represent the marbles
        marblePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Center-align the marbles with spacing
        marblePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border for better visibility

        middlePanel.add(infoPanel, BorderLayout.NORTH); // Add the info panel (Turn + Marble Count) to the top of the middle panel
        middlePanel.add(marblePanel, BorderLayout.CENTER); // Add the marble display to the center of the middle panel

        add(middlePanel, BorderLayout.CENTER); // Add the middle panel to the center region of the layout



        // Bottom Panel: Action Buttons
        JPanel bottomPanel = new JPanel(new GridLayout(2, 4, 5, 5)); // Panel for user action buttons with a grid layout
        removeOneButton = new JButton("Remove 1 marble"); // Button to remove 1 marble
        removeTwoButton = new JButton("Remove 2 marbles"); // Button to remove 2 marbles
        undoButton = new JButton("Undo"); // Button to undo the last move
        saveButton = new JButton("Save"); // Button to save the game
        loadButton = new JButton("Load"); // Button to load the game
        resetButton = new JButton("Reset"); // Button to reset the game

        // Add buttons to the bottom panel
        bottomPanel.add(removeOneButton);
        bottomPanel.add(removeTwoButton);
        bottomPanel.add(undoButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(loadButton);
        bottomPanel.add(resetButton);

        add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the south region of the layout

        

        // Event Listeners for Strategy Selection
        randomStrategyRadio.addActionListener(e -> startGame());
        yourStrategyRadio.addActionListener(e -> startGame());

        // Event Listeners for Action Buttons
        removeOneButton.addActionListener(e -> humanMove(1));
        removeTwoButton.addActionListener(e -> humanMove(2));
        undoButton.addActionListener(e -> undoMove());
        saveButton.addActionListener(e -> saveGame());
        loadButton.addActionListener(e -> loadGame());
        resetButton.addActionListener(e -> resetGame());

        setVisible(true); // Make the GUI visible
    }

    /**
     * Game of 1-2 Nim buttons.
     * @param enabled True to enable the buttons
     */
    private void setGameButtonsEnabled(boolean enabled) {
        removeOneButton.setEnabled(enabled);
        removeTwoButton.setEnabled(enabled);
        undoButton.setEnabled(enabled);
        saveButton.setEnabled(enabled);
        loadButton.setEnabled(enabled);
        resetButton.setEnabled(enabled);
    }
    
    /**
     * Method to start the GUI
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) 
    {
        new GUI(); // Create and display the GUI
    }
    
    /**
     * Starts the game with the selected strategy.
     * Initializes the NimGame object and updates the GUI to reflect the game's state.
     */
    private void startGame() 
    {
        MoveStrategy computerStrategy = randomStrategyRadio.isSelected() ? new RandomStrategy() : new YourStrategy(); // Determine the selected strategy
        game = new NimGame(new Player("Human", new HumanUserStrategy()), new Player("Computer", computerStrategy)); // Initialize the game

        turnLabel.setText("Turn: " + (game.isHumanTurn() ? "Human Player" : "Computer Player")); // Update the turn label
        marbleCountLabel.setText("Current number of marbles: " + game.getMarbleSize()); // Update the marble count label
        setGameButtonsEnabled(true); // Enable game buttons
        updateMarbleDisplay(); // Update the marble display

        // If it's the computer's turn, make the first move automatically
        if (!game.isHumanTurn()) 
        {
            int move = game.getComputerPlayer().getMove(game.getMarbleSize()); // Get the computer's move
            game.assignMove(move); // Apply the move
            updateMarbleDisplay(); // Update the display
            checkGameStatus("Turn: Computer Player removed " + move + " marble"); // Check the game status
        }
    }

    /**
     * Handles the human player's move by removing the specified number of marbles.
     * @param marbles The number of marbles to remove.
     */
    private void humanMove(int marbles) 
    {
        if (game.isHumanTurn()) // Ensure it is the human player's turn
        { 
            if (marbles > game.getMarbleSize()) // Validate the move
            {
                JOptionPane.showMessageDialog(this, "Oi! You can't remove more marbles than what's left!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            game.assignMove(marbles); // Apply the move
            updateMarbleDisplay(); // Update the display
            checkGameStatus(""); // Check the game status
        }
    }

    /**
     * Undoes the last move and updates the GUI.
     */
    private void undoMove() 
    {
        game.undoLastMove(); // Undo the last move
        updateMarbleDisplay(); // Update the display
        turnLabel.setText("Turn: " + (game.isHumanTurn() ? "Human Player" : "Computer Player")); // Update the turn label
    }

    /**
     * Saves the current game state.
     */
    private void saveGame() 
    {
        game.saveGame(); // Save the game
        JOptionPane.showMessageDialog(this, "The Game of Nim 1-2 is saved!", "Save", JOptionPane.INFORMATION_MESSAGE); // Confirmation message
    }

    /**
     * Loads a saved game state and updates the GUI.
     */
    private void loadGame() 
    {
        game.loadGame(); // Load the saved game
        updateMarbleDisplay(); // Update the display
        turnLabel.setText("Turn: " + (game.isHumanTurn() ? "Human Player" : "Computer Player")); // Update the turn label
        JOptionPane.showMessageDialog(this, "The Game of Nim 1-2 is loaded!", "Load", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame() 
    {
        // Clear strategy selection to force user to choose again
        randomStrategyRadio.setSelected(false);
        yourStrategyRadio.setSelected(false);
       
        // Reset the UI text
        turnLabel.setText("Turn: ");
        marbleCountLabel.setText("Current number of marbles: ");

        // Clear the marbles display
        marblePanel.removeAll();
        marblePanel.revalidate();
        marblePanel.repaint();

        // Display a message to inform the user to reselect a strategy
        JOptionPane.showMessageDialog(this, "The Game of 1-2 Nim has been reset!", "Reset", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method that update the GUI to match the current number of marbles in the game state
     */
    private void updateMarbleDisplay() 
    {
        marblePanel.removeAll(); // Clear the marble panel
        for (int i = 0; i < game.getMarbleSize(); i++) // Add a marble for each remaining marble
        {
            JLabel marble = new JLabel("●"); // Unicode for a marble
            marble.setFont(new Font("Serif", Font.BOLD, 50)); // Set the font size
            marble.setForeground(Color.BLACK); // Set the marble color
            marblePanel.add(marble);
        }
        marblePanel.revalidate(); // Refresh the panel
        marblePanel.repaint();
        marbleCountLabel.setText("Current number of marbles: " + game.getMarbleSize()); // Update the marble count label
    }

    /**
     * Checks the game's status if the game is still ongoing update the GUI and determines if a winner has been annouced
     * @param message A message describing the last move.
     */
    private void checkGameStatus(String message) 
    {
        if (game.checkWinner()) // Check if the game has ended
        { 
            String winner = game.isHumanTurn() ? "Computer Player" : "Human Player"; // Determine the winner
            JOptionPane.showMessageDialog(this, winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE); // Show the winner
        } else if (!game.isHumanTurn()) // If it's the computer's turn, make a move
        { 
            int move = game.getComputerPlayer().getMove(game.getMarbleSize()); // Get the computer's move
            game.assignMove(move); // Apply the move
            updateMarbleDisplay(); // Update the display
            checkGameStatus("Turn: Computer Player removed " + move + " marble"); // Check the game status
        } else 
        {
            turnLabel.setText(message); // Update the turn label with the last move
        }
    }
}