package game_test_package;

import game_package.Game;
import game_package.Player;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Class to test the functionality of Game class
 * @author desppapa
 * @author gogopavl
 */
public class Game_test {
    
    private Game gameTest;
    
    /**
     * Not used
     */
    public Game_test() {
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
        gameTest = new Game();
        
        Player testPlayer = new Player("test",0);
        
        gameTest.setNumberOfRounds(8);
        gameTest.setNumberOfPlayers(1); // only one player in first version
        gameTest.addPlayer(testPlayer);
        
        gameTest.gameSetup(); 
    }
    
    /**
     * Not used
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getNumberOfRounds method, of class Game.
     */
    @Test
    public void testGetNumberOfRounds() {
        System.out.println("getNumberOfRounds");
        int expResult = 8;
        int result = gameTest.getNumberOfRounds();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumberOfPlayers method, of class Game.
     */
    @Test
    public void testGetNumberOfPlayers() {
        System.out.println("getNumberOfPlayers");
        int expResult = 1;
        int result = gameTest.getNumberOfPlayers();
        assertEquals(expResult, result);
    }

    /**
     * Test of validateInput method, of class Game.
     */
    @Test
    public void testValidateInput() {
        System.out.println("validateInput");
        
        ArrayList<String> variousInputs = new ArrayList<>(); // list of various inputs (not acceptable)
        variousInputs.add("100");
        variousInputs.add("-10");
        variousInputs.add("text");
        
        int limit = 50;
        
        boolean expResult = false;
        variousInputs.stream().map((s) -> Game.validateInput(s, limit)).forEachOrdered((result) -> {
            assertEquals(expResult, result);
        });
    }
}
