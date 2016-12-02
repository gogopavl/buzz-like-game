package main_package;


import java.util.Scanner;
import game_package.Game;
import game_package.Player;

/**
 * Main class to create game object and invoke all needed methods
 * @author desppapa
 * @author gogopavl
 */
public class MainClass {
    private static Scanner input; // player's name
    private static Game buzzGame;
    /**
     * Main method to create game, set parameters and run program
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        input = new Scanner(System.in);
        
        String numberOfRounds;
        
        System.out.print("Εισάγετε Όνομα Χρήστη: ");
        String nameOfPlayer = input.nextLine();
        Player player = new Player(nameOfPlayer, 0); // zero points
        
        buzzGame = new Game(); // create instance
        
        System.out.print("Εισάγετε αριθμό γύρων (1-" + buzzGame.getMaxNumberOfRounds() + "): ");

        do{
            numberOfRounds = input.nextLine();
        }while(!Game.validateInput(numberOfRounds, buzzGame.getMaxNumberOfRounds()));
        
        // set parameters of buzzGame instance
        buzzGame.setNumberOfRounds(Integer.parseInt(numberOfRounds));
        buzzGame.setNumberOfPlayers(1); // only one player in first version
        buzzGame.addPlayer(player);
       
        buzzGame.gameSetup(); 
        buzzGame.startGame();
    } 
}
