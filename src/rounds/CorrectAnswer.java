/**
 * 
 * Class that extends a Round object - type of a game round
 * 
 */
package rounds;

import java.util.ArrayList;
import questions.Question;

/**
 *
 * @author desppapa && gogopavl
 */
public class CorrectAnswer extends Round{
    /**
     * Constructor
     * @param nQ number of questions
     * @param rQ list of round questions
     */
    public CorrectAnswer(int nQ, ArrayList<Question> rQ){
        super(nQ,rQ);  
        
    }
    
    /////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////
    /**
     * Method that prints the round type
     */
    @Override
    public void printRoundType() {
        System.out.println("Σωστή Απάντηση - Για κάθε σωστή απάντηση παίρνετε 1000 πόντους.\n");
    }
     /**
     * CorrectAnswer method that evaluates the number of points to be returned, based on the question's answer
     * @param q The question 
     * @return An integer value (represents user's points)
     */
    @Override
    public int evaluateAnwser(Question q){
        int userInput;
       
        q.displayQuestion();
       
        System.out.print("Για να απαντήσετε πιέστε από 1-4: ");
        userInput = answerInput.nextInt();
        
        if(q.checkAnswer(userInput)){
            return 1000;
        }
        else {
            return 0;
        }
    }
    
    
}
