package rounds_package;

import game_package.Game;
import java.util.ArrayList;
import questions_package.Question;

/**
 * Class that extends a Round object - type of a game round
 * @author desppapa
 * @author gogopavl
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
        String userInput;
       
        q.displayQuestion();
       
        System.out.print("Για να απαντήσετε πιέστε από 1-4: ");
        do{
            userInput = answerInput.nextLine();
        }while(!Game.validateInput(userInput, 4));
        
        if(q.checkAnswer(Integer.parseInt(userInput))){
            return 1000;
        }
        else {
            return 0;
        }
    }
    
    
}
