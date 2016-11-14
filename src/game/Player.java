/**
 * 
 * Class that represents a Player's profile 
 * 
 */
package game;

/**
 *
 * @author desppapa && gogopavl
 */
public class Player {
    
    private String name;
    private int points;
    
    /**
     * Constructor
     * @param n the player's name - must be unique
     * @param p player's points
     */
    public Player(String n, int p){
        name = n;
        points = p;
    }
    
    /////////////////////////////////////////////////
    //SETTERS & GETTERS
    /////////////////////////////////////////////////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    public void addPoints(int p) {
        points = points + p;
    }   
}
