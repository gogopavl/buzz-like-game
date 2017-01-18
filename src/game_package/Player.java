package game_package;

/**
 * Class that represents a Player's profile 
 * @author desppapa
 * @author gogopavl
 */
public class Player {
    
    private String name;
    private int points;
    
    private int input = 0;
    private long millisST = 0 ;
    private long millisQA = 0;
    private int betBid = 0;
    private int thermometerCounter = 0;
    
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
    
    public void addToThermometerCounter(int value){
        this.thermometerCounter += value;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public long getMillisST() {
        return millisST;
    }

    public void setMillisST(long millisST) {
        this.millisST = millisST;
    }

    public long getMillisQA() {
        return millisQA;
    }

    public void setMillisQA(long millisQA) {
        this.millisQA = millisQA;
    }

    public int getBetBid() {
        return betBid;
    }

    public void setBetBid(int betBid) {
        this.betBid = betBid;
    }

    public int getThermometerCounter() {
        return thermometerCounter;
    }

    public void setThermometerCounter(int thermometerCounter) {
        this.thermometerCounter = thermometerCounter;
    }
}
