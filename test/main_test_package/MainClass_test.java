package main_test_package;

import game_package.Game;
import game_package.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Class to test the functionality of MainClass
 * @author desppapa
 * @author gogopavl
 */
public class MainClass_test {
    
    private Game testBuzz;
    private Player testPlayer;
    
    /**
     * Not used
     */
    public MainClass_test() {
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
        testBuzz = new Game(); 
        testPlayer = new Player("player",0);

    }
    
    /**
     * Not used
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class MainRun.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        
        testBuzz.setNumberOfRounds(8);
        testBuzz.setNumberOfPlayers(1); // only one player in first version
        testBuzz.addPlayer(testPlayer);
        
        testBuzz.gameSetup();
                

        assertEquals(1, testBuzz.getNumberOfPlayers());
        assertEquals(8, testBuzz.getNumberOfRounds());
        assertEquals("player", testBuzz.getPlayers().get(0).getName());
        
                
        
        System.out.println("main passed");
    }
    
}
