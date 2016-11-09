/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;

/**
 *
 * @author Bue
 */
public class Game {
    private int numberOfRounds;
    private int numberOfPlayers;
    private ArrayList<Player> listOfPlayers;

    public Game(int nR, int nP) {
        numberOfRounds = nR;
        numberOfPlayers = nP;
        listOfPlayers = new ArrayList<>();
    }
    
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
    
    public void addPlayer(Player p){
        listOfPlayers.add(p);
    }
    
    public ArrayList<Player> getPlayers(){
        return listOfPlayers;
    }

    
    
}
