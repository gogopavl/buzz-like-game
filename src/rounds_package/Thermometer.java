/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rounds_package;

import java.util.ArrayList;
import questions_package.Question;

/**
 * @author desppapa
 * @author gogopavl
 */
public class Thermometer extends Round{
    
    public Thermometer(int nQ, ArrayList<Question> rQ) {
        super(nQ, rQ);
    }
    
    /////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////
 
    
    @Override
    public String getRoundType() {
        return "Thermometer";
    }
    
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
