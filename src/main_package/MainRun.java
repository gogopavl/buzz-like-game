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
        
        String numberOfRounds;
        
        System.out.print("Εισάγετε Όνομα Χρήστη: ");
        String nameOfPlayer = input.nextLine();
        Player player = new Player(nameOfPlayer, 0);
        
        buzzGame = new Game(); 
        
        System.out.print("Εισάγετε αριθμό γύρων (1-" + buzzGame.getMaxNumberOfRounds() + "): ");

        do{
            numberOfRounds = input.nextLine();
        }while(!Game.validateInput(numberOfRounds, buzzGame.getMaxNumberOfRounds()));
                  
        buzzGame.setNumberOfRounds(Integer.parseInt(numberOfRounds));
        buzzGame.setNumberOfPlayers(1); // only one player in first version
        buzzGame.addPlayer(player);
       
        buzzGame.gameSetup(); 
        buzzGame.startGame();
    } 
    
    
}
