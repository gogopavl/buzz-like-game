/**
 * 
 * Class implementing the structure of any question
 * 
 */
package questions;


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
        possibleAnswers = pA;
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
        this.possibleAnswers = possibleAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public void displayQuestion(){
        System.out.println("in here!!");
        System.out.println(sentence);
        for(String s : possibleAnswers){
            System.out.println(s);
        }
    }
    
    public boolean checkAnswer(int option){
        if(possibleAnswers[option-1].equals(correctAnswer)){
            System.out.println("is correct!!!!!!!");
            return true;
        }else{
            System.out.println("is wrong!!!!");
            return false;
        }
    }
}
