package questions_test_package;

import questions_package.Question;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Class to test the functionality of Question class
 * @author desppapa
 * @author gogopavl
 */
public class Question_test {
    
    private Question testQuestion;
    
    /**
     * Not used
     */
    public Question_test() {
    }
    
    /**
     * Not used
     */
    @BeforeClass
    public static void setUpClass() {
    }
    
    /**
     * Not used
     */
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * Needed variables for testing
     */
    @Before
    public void setUp() {
        String[] testAnswers = {"one","two","three","four"};
        testQuestion = new Question("type","question",testAnswers,"one");
    }
    
    /**
     * Not used
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of checkAnswer method, of class Question.
     */
    @Test
    public void testCheckAnswer() {
        System.out.println("checkAnswer");
        Random r = new Random();
        int option = r.nextInt(4)+1;
        boolean expResult;
        
        if(testQuestion.getPossibleAnswers()[option-1].equals(testQuestion.getCorrectAnswer())){
            expResult = true;
        }else{
            expResult = false;
        }
        boolean result = testQuestion.checkAnswer(option);
        assertEquals(expResult, result);
    }

    /**
     * Test of arrayShuffle method, of class Question.
     */
    @Test
    public void testArrayShuffle() {
        System.out.println("arrayShuffle");
        
        String[] shuffledArray = testQuestion.arrayShuffle(testQuestion.getPossibleAnswers());
       
        for(String s: testQuestion.getPossibleAnswers()){
            for(String t: shuffledArray){
                if(s.equals(t)) break;
            }            
        }        
    }
    
}
