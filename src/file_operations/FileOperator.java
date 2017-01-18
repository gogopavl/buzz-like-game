/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file_operations;

import game_package.Player;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that operates files
 * @author desppapa
 * @author gogopavl
 */
public class FileOperator {
    static FileWriter fw;
    static BufferedReader br;
    
    /**
     * Constructor
     * @throws IOException 
     */
    public FileOperator() throws IOException {
//        this.fw = new FileWriter("stats.txt", true);
    }
    
    /**
     * Method that appends at the file stats.txt the SinglePlayerGame stats
     * @param name the player's name
     * @param points the player's points
     * @throws IOException 
     */
    public static void writerFunc(String name, int points) throws IOException{
        fw = new FileWriter("stats.txt", true);
        fw.write("s," + name + "," + points);
        fw.write("\n");
        fw.close();
    }
    
    /**
     * Method that appends at the file stats.txt the MultiPlayerGame stats
     * @param name the first player's name
     * @param points the first player's points
     * @param name2 the second player's name
     * @param points2 the second player's points
     * @throws IOException 
     */
    public static void writerFuncMulti(String name, int points, String name2, int points2) throws IOException{
        fw = new FileWriter("stats.txt", true);
        fw.write("m," + name + "," + points + "," + name2 + "," + points2);
        fw.write("\n");
        fw.close();
    }
    
    /**
     * 
     * @return the list of Single player game scores 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static ArrayList<Player> readerFun() throws FileNotFoundException, IOException{
        br = new BufferedReader(new FileReader("stats.txt"));
        String currentLine;
        ArrayList<Player> singlePlayerScoresList;
        singlePlayerScoresList = new ArrayList<>();
        String[] splittedLine = new String[3];
        
        while ((currentLine = br.readLine()) != null) {
                if(currentLine.split(",")[0].equals("s")){
                    splittedLine = currentLine.split(",");
                    Player player;
                    player = new Player(splittedLine[1],Integer.valueOf(splittedLine[2]));
                    singlePlayerScoresList.add(player);
                }               
        } 
        return singlePlayerScoresList;
    }
    
    /**
     * 
     * @return the list of Multi player game scores
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static ArrayList<Matchup> readerFunMulti() throws FileNotFoundException, IOException{
        br = new BufferedReader(new FileReader("stats.txt"));
        String currentLine;
        
        ArrayList<Matchup> multiPlayerScoresList = new ArrayList<>();
        String[] splittedLine = new String[4];
        
        while ((currentLine = br.readLine()) != null) {
                if(currentLine.split(",")[0].equals("m")){
                    splittedLine = currentLine.split(",");
                    Player player1 , player2;
                    player1 = new Player(splittedLine[1],Integer.valueOf(splittedLine[2]));
                    player2 = new Player(splittedLine[3],Integer.valueOf(splittedLine[4]));
                    
                    multiPlayerScoresList.add(new Matchup(player1, player2));
                }               
        } 
        return multiPlayerScoresList;
    }
    
    
}
