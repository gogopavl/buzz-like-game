/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package questions;


/**
 *
 * @author Bue
 */
public class Question {
    private String type;
    private String sentence;
    private String[] possibleAnswers;
    private String correctAnswer;
    
    /**
     * 
     * @param t the question type e.g. "Sports"
     * @param s the question text 
     * @param pA array with all the possible answers
     * @param cA the correct answer
     */
    public Question(String t, String s, String[] pA, String cA){
        type = t;
        sentence = s;
        possibleAnswers = pA;
        correctAnswer = cA;
    }
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
        this.possibleAnswers = possibleAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
