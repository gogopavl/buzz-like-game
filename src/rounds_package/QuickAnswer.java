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
public class QuickAnswer extends Round{
    
    /**
     * Constructor
     * @param nQ number of questions
     * @param rQ list of round questions
     */
    public QuickAnswer(int nQ, ArrayList<Question> rQ) {
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
        return "Quick Answer";
    }
    /**
     * QuickAnswer method that evaluates the number of points to be returned, based on the question's answer
     * @param q The question
     * @param userInput player's answer
     * @return an integer value
     */
    @Override
    public int evaluateAnwser(Question q, int userInput){
      
        if(q.checkAnswer(userInput)){
            return 1;
        }
        else {
            return 0;
        }
    }

}
