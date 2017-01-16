/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rounds_package;

import java.util.ArrayList;
import questions_package.Question;
import static sun.security.krb5.Confounder.intValue;

/**
 *
 * @author gogopavl
 */
public class QuickAnswer extends Round{
    
    public QuickAnswer(int nQ, ArrayList<Question> rQ) {
        super(nQ, rQ);
    }
    
    /////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////
 
    
    @Override
    public String getRoundType() {
        return "Quick Answer";
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
