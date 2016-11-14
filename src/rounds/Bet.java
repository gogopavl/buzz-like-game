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
    /**
     * Method that prints the round type
     */
    @Override
    public void printRoundType() {
        System.out.println("Ποντάρισμα - Για κάθε σωστή απάντηση κερδίζετε τους πόντους που ποντάρατε, αλλιώς τους χάνετε.\n");
    }
     /**
     * Bet method that evaluates the number of points to be returned, based on the question's answer
     * @param q The question 
     * @return An integer value (represents user's points)
     */
    @Override
    public int evaluateAnwser(Question q){
        
        int betInput, userInput;
           
        System.out.println("\nΟ τύπος της κατηγορίας είναι: " + q.getType() + "\n");
        System.out.print("Για να ποντάρετε 250, 500, 750 ή 1000 πόντους, πιέστε αντίστοιχα 1, 2, 3, 4: ");
        betInput = answerInput.nextInt();
        
        q.displayQuestion();
       
        System.out.print("Για να απαντήσετε πιέστε από 1-4: ");
        userInput = answerInput.nextInt();
        
        switch(betInput){
            case 1: 
                betInput = 250;
                break;
            case 2: 
                betInput = 500;
                break;
            case 3: 
                betInput = 750;
                break;
            case 4: 
                betInput = 1000;
                break;
            default: 
                break;
        }
        
        if(q.checkAnswer(userInput)){
            return betInput;
        }
        else {
            return -betInput;
        }

    }
    
}
