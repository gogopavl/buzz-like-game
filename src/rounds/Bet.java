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
    
}
