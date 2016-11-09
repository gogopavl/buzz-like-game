/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_package;


import game.Game;
import game.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import rounds.CorrectAnswer;

/**
 *
 * @author gogopavl
 */
public class MainRun {
    private static Scanner input;
    private static Game buzzGame;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        input = new Scanner(System.in);
                
        
    }
    
    public static void initializeGame(){
        int numberOfRounds;
        
        System.out.print("Όνομα Χρήστη: ");
        String nameOfPlayer = input.nextLine();
        Player player = new Player(nameOfPlayer, 0);
        
        
        System.out.print("Δώσε αριθμό γύρων: ");
        numberOfRounds = input.nextInt();
        
        buzzGame = new Game(numberOfRounds , 1);
        buzzGame.addPlayer(player);
    }
    
    public static void gameSetup(){
        int roundModulo;
        for (int i = 0 ; i < buzzGame.getNumberOfRounds() ; ++i ){
           roundModulo = i % Game.getNUMBER_OF_ROUND_TYPES(); 
           switch(roundModulo){
               case 0: 
                   //
                   break;
               case 1: 
                   //
                   break;
               default: 
                   //
                   break;
           }
        }         
    }
}
