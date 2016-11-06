/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rounds;

import java.util.ArrayList;
import questions.Question;

/**
 *
 * @author Bue
 */
public class Round {
    private int numberOfQuestions;
    private ArrayList<Question> roundQuestions;
    
    public Round(int nQ, ArrayList<Question> rQ){
        numberOfQuestions = nQ;
        roundQuestions = rQ;
    }

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
