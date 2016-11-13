/**
 * Main class to invoke all needed methods
 */
package main_package;


import java.util.Scanner;
import game.Game;
import game.Player;

/**
 *
 * @author desppapa && gogopavl
 */
public class MainRun {
    private static Scanner input;
    private static Game buzzGame;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        input = new Scanner(System.in);
        initializeGame();
        buzzGame.gameSetup(); 
        buzzGame.startGame();
    }
    
    /**
     * Method that sets all required fields in order to start a game
     */
    public static void initializeGame(){
        int numberOfRounds;
        
        System.out.print("Εισάγετε Όνομα Χρήστη: ");
        String nameOfPlayer = input.nextLine();
        Player player = new Player(nameOfPlayer, 0);
        
        buzzGame = new Game(); // second param is num of players - will be changed 
                                                 // in the second version of our software
                                                 
        System.out.print("Εισάγετε αριθμό γύρων (1-" + buzzGame.getMaxNumberOfRounds() + "): ");
        numberOfRounds = input.nextInt();
        
        buzzGame.setNumberOfRounds(numberOfRounds);
        buzzGame.setNumberOfPlayers(1); 
        buzzGame.addPlayer(player);
         
        System.out.println("Number of Rounds is: " + buzzGame.getNumberOfRounds());
    }
    
    
    
}
