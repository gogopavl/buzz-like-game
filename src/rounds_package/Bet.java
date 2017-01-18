package rounds_package;

import game_package.Game;
import java.util.ArrayList;
import questions_package.Question;

/**
 * Class that extends a Round object - type of a game round
 * @author desppapa
 * @author gogopavl
 */
public class Bet extends Round{
    /**
     * Constructor
     * @param nQ number of questions
     * @param rQ list of round questions
     */
    public Bet(int nQ, ArrayList<Question> rQ){
        super(nQ,rQ); 
    }
    
    /////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////
    
//    /**
//     * Method that prints the round type
//     */
//    @Override
//    public void printRoundType() {
//        System.out.println("Ποντάρισμα - Για κάθε σωστή απάντηση κερδίζετε τους πόντους που ποντάρατε, αλλιώς τους χάνετε.\n");
//    }
    
    /**
     * 
     * @return the current round type
     */
    @Override
    public String getRoundType(){
        return "Bet";
    }
    
     /**
     * Bet method that evaluates the number of points to be returned, based on the question's answer
     * @param q The question 
     * @return An integer value (represents user's points)
     */
    @Override
    public int evaluateAnwser(Question q, int userInput){  
        
        String betInput, usrIn;
        
        if(q.checkAnswer(userInput)){
            return 1;
        }
        else {
            return -1;
        }

    }
    
}
