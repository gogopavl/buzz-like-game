/**
 * 
 * Class implementing the structure of any question
 * 
 */
package questions;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author desppapa && gogopavl
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(String[] possibleAnswers) {
        this.possibleAnswers = arrayShuffle(possibleAnswers);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

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
            System.out.println("\nΣωστή απάντηση :)\n");
            return true;
        }else{
            System.out.println("\nΛάθος απάντηση :(\n");
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
