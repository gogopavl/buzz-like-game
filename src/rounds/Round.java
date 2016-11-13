/**
 * 
 * Class that implements a game round
 * 
 */
package rounds;

import java.util.ArrayList;
import questions.Question;

/**
 *
 * @author desppapa && gogopavl
 */
public class Round {
    private int numberOfQuestions;
    private ArrayList<Question> roundQuestions;
    /**
     * Constructor
     * @param nQ number of questions
     * @param rQ list of round questions
     */
    public Round(int nQ, ArrayList<Question> rQ){
        numberOfQuestions = nQ;
        roundQuestions = rQ;
        for(Question q : rQ){
            System.out.println(q.getSentence());
        }
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
    
    
}
