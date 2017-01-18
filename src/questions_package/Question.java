package questions_package;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Class implementing the structure of any question
 * @author desppapa
 * @author gogopavl
 */
public class Question {
    private String type;
    private String sentence;
    private String[] possibleAnswers;
    private String correctAnswer;
    
    /**
     * Constructor
     * @param t the question type e.g. "Sports"
     * @param s the question text 
     * @param pA array with all the possible answers
     * @param cA the correct answer
     */
    public Question(String t, String s, String[] pA, String cA){
        type = t;
        sentence = s;
        possibleAnswers = arrayShuffle(pA);
        correctAnswer = cA;
    }
    /**
     * Default constructor
     */
    public Question(){
    }

    /////////////////////////////////////////////////
    //SETTERS & GETTERS
    /////////////////////////////////////////////////

    /**
     *
     * @return the type of question
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type a given string
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return the sentence
     */
    public String getSentence() {
        return sentence;
    }

    /**
     *
     * @param sentence a given string
     */
    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    /**
     *
     * @return the list of possible answers
     */
    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    /**
     *
     * @param possibleAnswers a given string array
     */
    public void setPossibleAnswers(String[] possibleAnswers) {
        this.possibleAnswers = arrayShuffle(possibleAnswers);
    }

    /**
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     *
     * @param correctAnswer a given string
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    /////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////
    
    /**
     * Method that prints the structure of a question and all its possible answers
     */
    public void displayQuestion(){
        int index = 1;
        System.out.println(sentence);
        for(String s : possibleAnswers){
            System.out.println((index++)+ ". " + s);
        }
    }
    /**
     * Function that evaluates whether the given answer is correct or wrong
     * @param option an integer that represents the given answer
     * @return true if correct, otherwise false
     */
    public boolean checkAnswer(int option){
        if(possibleAnswers[option-1].equals(correctAnswer)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Function that shuffles a String[]'s contents using the Fisher–Yates shuffle algorithm
     * @param ar The given String[]
     * @return The shuffled String[]
     */
    public String[] arrayShuffle(String[] ar){
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
          int index = rnd.nextInt(i + 1);
          // Simple swap
          String a = ar[index];
          ar[index] = ar[i];
          ar[i] = a;
        }
        return ar;
    }
   
}
