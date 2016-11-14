/**
 * 
 * Class that implements a game round
 * 
 */
package rounds;

import java.util.ArrayList;
import java.util.Scanner;
import questions.Question;

/**
 *
 * @author desppapa && gogopavl
 */
public class Round {
    private int numberOfQuestions;
    private ArrayList<Question> roundQuestions;
    Scanner answerInput;
    /**
     * Constructor
     * @param nQ number of questions
     * @param rQ list of round questions
     */
    public Round(int nQ, ArrayList<Question> rQ){
        numberOfQuestions = nQ;
        roundQuestions = rQ;
        answerInput = new Scanner(System.in);
    }
    /////////////////////////////////////////////////
    //SETTERS & GETTERS
    /////////////////////////////////////////////////    
    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public ArrayList<Question> getRoundQuestions() {
        return roundQuestions;
    }

    public void setRoundQuestions(ArrayList<Question> roundQuestions) {
        this.roundQuestions = roundQuestions;
    }
    
    /////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////
    /**
     * Method that prints the round type
     */
    public void printRoundType() {
        System.out.println("Round object");// doesn't matter
    }
    /**
     * Round method that evaluates the number of points to be returned, based on the question's answer
     * @param q The question 
     * @return An integer value (represents user's points)
     */
    public int evaluateAnwser(Question q){
        return 0; // doesn't matter
    }

    
    
}
