package game_package;

/**
 * Class that represents a Player's profile 
 * @author desppapa
 * @author gogopavl
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

    /**
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name a given name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return a player's points
     */
    public int getPoints() {
        return points;
    }

    /**
     *
     * @param points a given number of points
     */
    public void setPoints(int points) {
        this.points = points;
    }
    
    /**
     *
     * @param p a given number of points
     */
    public void addPoints(int p) {
        points = points + p;
    }   
}
