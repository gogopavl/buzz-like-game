/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.BufferedReader;
import java.util.ArrayList;
import rounds.Round;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.regex.Pattern;
import questions.Question;

/**
 *
 * @author Bue
 */
public class Game {
    
    private static BufferedReader br = null;
    
    private final static int NUMBER_OF_ROUND_TYPES = 2;
    
    private int numberOfRounds;
    private int numberOfPlayers;
    private ArrayList<Player> listOfPlayers;
    private ArrayList<Question> allQuestions;
    private int currentRound;
    private ArrayList<Round> rounds;
    
    

    public Game(int nR, int nP) {
        numberOfRounds = nR;
        numberOfPlayers = nP;
        listOfPlayers = new ArrayList<>();
        allQuestions = new ArrayList<>();
        currentRound = 0;
        rounds = new ArrayList<>();
        importQuestions();
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
    
    public void addRound(Round r){
        rounds.add(r);
    }
    
    public ArrayList<Round> getRounds(){
        return rounds;
    }

    public static int getNUMBER_OF_ROUND_TYPES() {
        return NUMBER_OF_ROUND_TYPES;
    }
    
    public void importQuestions(){
        readQuestionsFromFile("Technology.txt");
        readQuestionsFromFile("Biology.txt");
        readQuestionsFromFile("General.txt");
        readQuestionsFromFile("Science.txt");        
    }
    
    public void readQuestionsFromFile(String filename){
        try{
            String currentLine;
            String[] tempSplittedString = new String[2];
            Boolean flag = true;

            br = new BufferedReader(new FileReader(filename));
            
            while ((currentLine = br.readLine()) != null) {
                if(currentLine.equals("#")){
                    
                    Question tempQuestion = new Question();
                    
                    tempQuestion.setType(filename.split(Pattern.quote("."))[0]);
                    
                    String[] tempPossibleAnswers = new String[4];
                    int TPAPosition = 0;
                    while(flag){
                        currentLine = br.readLine();
                        
                        tempSplittedString = currentLine.split(":");
                        
                        if(tempSplittedString[0].equals("q")){
                            tempQuestion.setSentence(tempSplittedString[1]);
                        }
                        else if(tempSplittedString[0].equals("a")){
                            tempPossibleAnswers[TPAPosition++] = tempSplittedString[1];                                                   
                        }
                        else if(tempSplittedString[0].equals("c")){
                            tempQuestion.setCorrectAnswer(tempSplittedString[1]);
                            flag = false;
                        }
                      
                    }
                    tempQuestion.setPossibleAnswers(tempPossibleAnswers);
                    TPAPosition = 0;  
                    flag = true;
                    allQuestions.add(tempQuestion);
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }finally{
                try {
                    if (br != null)br.close();
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
        }

    }   
    
    
    
}
