package game_package;

import rounds_package.CorrectAnswer;
import rounds_package.Bet;
import java.io.BufferedReader;
import java.util.ArrayList;
import rounds_package.Round;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import questions_package.ImageQuestion;
import questions_package.Question;

/**
 * Class that represents a buzz game. Contains functions needed to implement the basic logic, to load the game questions and to validate the user's input
 * @author desppapa
 * @author gogopavl
 */
public class Game {
    
    private static BufferedReader br = null;
    
    private final static int NUMBER_OF_ROUND_TYPES = 2; // Bet and CorrectAnswer
    private final static int NUMBER_OF_QUESTIONS_PER_ROUND = 5; // We thought five was a good number of questions per round
    
    private int numberOfRounds;
    private int numberOfPlayers;
    private final ArrayList<Player> listOfPlayers;
    private ArrayList<Question> allQuestions;
    private ArrayList<Round> rounds;
    
    /**
     * Empty Constructor - used to initialize a game
     */
    public Game(){ 
        listOfPlayers = new ArrayList<>();
        allQuestions = new ArrayList<>();
        rounds = new ArrayList<>();
        importQuestions(); // read files
    }
    
    /////////////////////////////////////////////////
    //SETTERS & GETTERS
    /////////////////////////////////////////////////

    /**
     *
     * @return the number of rounds
     */
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    /**
     *
     * @param numberOfRounds a given number
     */
    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    /**
     *
     * @return the number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     *
     * @param numberOfPlayers a given number
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
    
    /**
     *
     * @param p a given player
     */
    public void addPlayer(Player p){
        listOfPlayers.add(p);
    }
    
    /**
     *
     * @return the list of players
     */
    public ArrayList<Player> getPlayers(){
        return listOfPlayers;
    }
    
    /**
     *
     * @param r a given round
     */
    public void addRound(Round r){
        rounds.add(r);
    }
    
    /**
     *
     * @return the list of rounds
     */
    public ArrayList<Round> getRounds(){
        return rounds;
    }

    /**
     *
     * @return the number of round types
     */
    public static int getNUMBER_OF_ROUND_TYPES() {
        return NUMBER_OF_ROUND_TYPES;
    }
    
    /**
     *
     * @return the number of questions per round
     */
    public static int getNUMBER_OF_QUESTIONS_PER_ROUND() {
        return NUMBER_OF_QUESTIONS_PER_ROUND;
    }
    
    /**
     *
     * @return the maximum number of rounds that can be played 
     */
    public int getMaxNumberOfRounds(){
        return Math.floorDiv(allQuestions.size() , NUMBER_OF_QUESTIONS_PER_ROUND) ;
    }
    
    /////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////
    
    /**
     * Method that loads questions and rounds
     */
    public void gameSetup(){
        Random r = new Random();
        int roundType;
        
        Collections.shuffle(allQuestions); // shuffling all questions - each round contains questions of any type
        Iterator<Question> questionsIterator = allQuestions.iterator();
        
        for (int i = 0 ; i < numberOfRounds ; ++i ){
           ArrayList<Question> tempQuestionList = new ArrayList<>(); 
           
           roundType = r.nextInt(NUMBER_OF_ROUND_TYPES); // to make sure player plays as many different round types as possible 
           
           switch(roundType){
               case 0: 
                   // add NUMBER_OF_QUESTIONS_PER_ROUND to round object and remove them from allQuestions list
                   for(int j = 0 ; j < NUMBER_OF_QUESTIONS_PER_ROUND ; ++j){
                       tempQuestionList.add(questionsIterator.next());
                       questionsIterator.remove();
                   }
                   rounds.add(new CorrectAnswer(NUMBER_OF_QUESTIONS_PER_ROUND , tempQuestionList));
                   
                   break;
               case 1: 
                   // add NUMBER_OF_QUESTIONS_PER_ROUND to round object and remove them from allQuestions list
                   for(int j = 0 ; j < NUMBER_OF_QUESTIONS_PER_ROUND ; ++j){
                       tempQuestionList.add(questionsIterator.next());
                       questionsIterator.remove();
                   }
                   rounds.add(new Bet(NUMBER_OF_QUESTIONS_PER_ROUND , tempQuestionList));
                   
                   break;
               default: 
                   //system error
                   break;
           }
           
        }
    }
    /**
     * Method that runs the game
     */
    public void startGame(){
        
        Scanner answerInput = new Scanner(System.in);
        int userInput , roundNumber = 1;
        
        ArrayList<Question> localQuestionList;        
        
        for(Round currentRound : rounds){
            
            System.out.println("\n--------------------Γύρος " + roundNumber++ +"--------------------");
            currentRound.printRoundType();
            
            localQuestionList = currentRound.getRoundQuestions();
            
            for(Question currentQuestion : localQuestionList){
                
                listOfPlayers.get(0).addPoints(currentRound.evaluateAnwser(currentQuestion));
                
            }
        }
        System.out.println("\nΤο παιχνίδι ολοκληρώθηκε! Το σκορ που σημειώσατε είναι: "+ listOfPlayers.get(0).getPoints() + " πόντοι.");
    }
    /**
     * Function that receives the user's input and checks if it is valid
     * 
     * @param input String - user's input
     * @param limit if the user submits a number that should be less or equal than some limit
     * @return true if input is acceptable, otherwise false
     */
    public static boolean validateInput(String input, int limit){
        try { 
            Integer.parseInt(input); 
        } catch(NumberFormatException | NullPointerException e) {
            System.out.println("Η είσοδός σας δεν είναι έγκυρη. Εισάγετε έναν αριθμό από 1-" + limit);
            return false; 
        }
        int integerInput = (Integer.parseInt(input));
        if((integerInput < 1) || (integerInput>limit)){
            System.out.println("Η είσοδός σας δεν είναι έγκυρη. Εισάγετε έναν αριθμό από 1-" + limit);
            return false;
        }
        return true;
    }
    
    /**
     * Function that calls the readQuestionsFromFile method with all question files as parameters
     */
    public final void importQuestions(){
        readQuestionsFromFile("Technology.txt");
        readQuestionsFromFile("Biology.txt");
        readQuestionsFromFile("General.txt");
        readQuestionsFromFile("Science.txt");
        readImageQuestionsFromFile("Cinema.txt");
        readImageQuestionsFromFile("Music.txt");
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

            br = new BufferedReader(new FileReader("questions/"+filename));
            
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
        }finally{
                try {
                    if (br != null)br.close();
                }catch (IOException ex) {
                }
        }

    }
    /**
     * Method that reads a given file containing a question, its possible answers, the correct answer and the image filename.
     * Its filename implies the question type.
     * 
     * @param filename name of file containing questions
     */
    public void readImageQuestionsFromFile(String filename){
        try{
            String currentLine; // variable used to store each line read
            String[] tempSplittedString = new String[2]; // array used to store both parts of the string splitted (":")
            Boolean flag = true; // variable to separate each question's data block
            String questionType = filename.split(Pattern.quote("."))[0]; // variable used to fill the "type" field of a question object

            br = new BufferedReader(new FileReader("questions/"+filename));
            
            while ((currentLine = br.readLine()) != null) {
                if(currentLine.equals("#")){ // new question block
                    
                    ImageQuestion tempQuestion = new ImageQuestion(); // temp question object to form a new question
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
                                break;
                            case "i": //it's the image name
                                tempQuestion.setImageName(tempSplittedString[1]);
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
        }finally{
                try {
                    if (br != null)br.close();
                }catch (IOException ex) {
                }
        }

    }    
}
