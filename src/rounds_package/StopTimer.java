/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rounds_package;

import java.util.ArrayList;
import questions_package.Question;

/**
 * Class that extends a Round object - type of a game round
 * @author desppapa
 * @author gogopavl
 */
public class StopTimer extends Round{
    
    /**
     * Constructor
     * @param nQ number of questions
     * @param rQ list of round questions
     */
    public StopTimer(int nQ, ArrayList<Question> rQ) {
        super(nQ, rQ);
    }
    
    /////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////
 
    /**
     * 
     * @return the current round type
     */
    @Override
    public String getRoundType() {
        return "Stop Timer";
    }
    
    
    @Override
    public int evaluateAnwser(Question q, int userInput){
      
        if(q.checkAnswer(userInput)){
            return 0;
        }
        else {
            return 0;
        }
    }
    
    /**
     * StopTimer method that evaluates the number of points to be returned, based on the question's answer
     * @param q The question 
     * @return An integer value (represents user's points)
     */
    public int evaluateAnwser(Question q, int userInput, long millis){
        Double result;
        if(q.checkAnswer(userInput)){
            result = millis*0.2;
            return result.intValue();
        }
        else {
            return 0;
        }
    }
    
}
