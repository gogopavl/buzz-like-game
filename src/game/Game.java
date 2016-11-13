/**
 *
 * Class that represents a buzz game
 * 
 */
package game;

import java.io.BufferedReader;
import java.util.ArrayList;
import rounds.Round;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;
import questions.Question;
import rounds.*;

/**
 *
 * @author desppapa && gogopavl
 */
public class Game {
    
    private static BufferedReader br = null;
    
    private final static int NUMBER_OF_ROUND_TYPES = 2;
    private final static int NUMBER_OF_QUESTIONS_PER_ROUND = 5;
    
    private int numberOfRounds;
    private int numberOfPlayers;
    private ArrayList<Player> listOfPlayers;
    private ArrayList<Question> allQuestions;
    private int currentRound;
    private ArrayList<Round> rounds;
    
    /**
     * Empty Constructor - used to initialize a game
     */
    public Game(){ 
        listOfPlayers = new ArrayList<>();
        allQuestions = new ArrayList<>();
        currentRound = 0;
        rounds = new ArrayList<>();
        importQuestions(); // read files
    }
    
    /////////////////////////////////////////////////
    //SETTERS & GETTERS
    /////////////////////////////////////////////////
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
    
    public static int getNUMBER_OF_QUESTIONS_PER_ROUND() {
        return NUMBER_OF_QUESTIONS_PER_ROUND;
    }
    
    public int getMaxNumberOfRounds(){
        return Math.floorDiv(allQuestions.size() , NUMBER_OF_QUESTIONS_PER_ROUND) ;
    }
    
    /**
     * Method that loads questions and rounds
     */
    public void gameSetup(){
        int roundModulo;
        
        Collections.shuffle(allQuestions); // shuffling all questions - each round contains questions of any type
        Iterator<Question> questionsIterator = allQuestions.iterator();
        ArrayList<Question> tempQuestionList = new ArrayList<>(); 
        
        for (int i = 0 ; i < numberOfRounds ; ++i ){
            
           roundModulo = i % NUMBER_OF_ROUND_TYPES; // to make sure player plays as many different round types as possible 
           
           switch(roundModulo){
               case 0: 
                   // add NUMBER_OF_QUESTIONS_PER_ROUND to round object and remove them from allQuestions list
                   for(int j = 0 ; j < NUMBER_OF_QUESTIONS_PER_ROUND ; ++j){
                       tempQuestionList.add(questionsIterator.next());
                       questionsIterator.remove();
                   }
                   System.out.println("tql size: " +tempQuestionList.size());
                   rounds.add(new CorrectAnswer(NUMBER_OF_QUESTIONS_PER_ROUND , tempQuestionList));
                   tempQuestionList.clear();
                   break;
               case 1: 
                   // add NUMBER_OF_QUESTIONS_PER_ROUND to round object and remove them from allQuestions list
                   for(int j = 0 ; j < NUMBER_OF_QUESTIONS_PER_ROUND ; ++j){
                       tempQuestionList.add(questionsIterator.next());
                       questionsIterator.remove();
                   }
                   System.out.println("tql size: " +tempQuestionList.size());
                   rounds.add(new Bet(NUMBER_OF_QUESTIONS_PER_ROUND , tempQuestionList));
                   tempQuestionList.clear();
                   break;
               default: 
                   //system error
                   break;
           }
        }         
    }
    
    public void startGame(){
        Scanner answerInput = new Scanner(System.in);
        int userInput , roundNumber = 1;
        
        for(Round currentRound : rounds){
            System.out.println("Example size " + currentRound.getNumberOfQuestions());
            for(Question currentQuestion : currentRound.getRoundQuestions()){
                currentQuestion.displayQuestion();
                System.out.println("Για να απαντήσετε πιέστε από 1-4");
                userInput = answerInput.nextInt();
                currentQuestion.checkAnswer(userInput);
            }
            roundNumber++;
        }
    }
    
    /**
     * Function that calls the readQuestionsFromFile method with all question files as parameters
     */
    public void importQuestions(){
        readQuestionsFromFile("Technology.txt");
        readQuestionsFromFile("Biology.txt");
        readQuestionsFromFile("General.txt");
        readQuestionsFromFile("Science.txt");        
    }
    /**
     * Method that reads a given file containing a question, its possible answers and the correct answer.
     * Its filename implies the question type.
     * 
     * @param filename name of file containing questions
     */
    public void readQuestionsFromFile(String filename){
        try{
            String currentLine; // variable used to store each line read
            String[] tempSplittedString = new String[2]; // array used to store both parts of the string splitted (":")
            Boolean flag = true; // variable to separate each question's data block
            String questionType = filename.split(Pattern.quote("."))[0]; // variable used to fill the "type" field of a question object

            br = new BufferedReader(new FileReader(filename));
            
            while ((currentLine = br.readLine()) != null) {
                if(currentLine.equals("#")){ // new question block
                    
                    Question tempQuestion = new Question(); // temp question object to form a new question
                    String[] tempPossibleAnswers = new String[4]; // array of string to store each question's possible answers
                    int TPAPosition = 0; // variable used to determine the index of the current possible answer
                    
                    tempQuestion.setType(questionType); // setting type according to filename
                    
                    while(flag){
                        currentLine = br.readLine();
                        
                        tempSplittedString = currentLine.split(":");
                        
                        switch (tempSplittedString[0]) {
                            case "q": // it's a question sentence
                                tempQuestion.setSentence(tempSplittedString[1]);
                                break;
                            case "a": // it's a possible answer 
                                tempPossibleAnswers[TPAPosition++] = tempSplittedString[1];
                                break;
                            case "c": //it's the correct answer
                                tempQuestion.setCorrectAnswer(tempSplittedString[1]);
                                flag = false; // last line of question block in file, turn flag to false
                                break;
                            default:
                                break; // what to do?
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
