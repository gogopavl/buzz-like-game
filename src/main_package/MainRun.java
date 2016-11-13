/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        gameSetup();       
    }
    
    /**
     * Method that sets all required fields in order to start a game
     */
    public static void initializeGame(){
        int numberOfRounds;
        
        System.out.print("Εισάγετε Όνομα Χρήστη: ");
        String nameOfPlayer = input.nextLine();
        Player player = new Player(nameOfPlayer, 0);
        
        
        System.out.print("Εισάγετε αριθμό γύρων: ");
        numberOfRounds = input.nextInt();
        
        buzzGame = new Game(numberOfRounds , 1);
        buzzGame.addPlayer(player);
    }
    /**
     * Method that loads questions and rounds
     */
    public static void gameSetup(){
        int roundModulo;
        for (int i = 0 ; i < buzzGame.getNumberOfRounds() ; ++i ){
           roundModulo = i % Game.getNUMBER_OF_ROUND_TYPES(); 
           switch(roundModulo){
               case 0: 
                   //new CorrectAnswer
                   break;
               case 1: 
                   //new Bet
                   break;
               default: 
                   //system error
                   break;
           }
        }         
    }
    
    
}
