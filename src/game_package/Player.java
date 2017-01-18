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
    
    /**
     * 
     * @param value an integer number (0 or 1) 
     */
    public void addToThermometerCounter(int value){
        this.thermometerCounter += value;
    }

    /**
     * 
     * @return the player's input
     */
    public int getInput() {
        return input;
    }

    /**
     * 
     * @param input a given player's input
     */
    public void setInput(int input) {
        this.input = input;
    }

    /**
     * 
     * @return the player's time at Stop Timer round
     */
    public long getMillisST() {
        return millisST;
    }

    /**
     * 
     * @param millisST a player's time at Stop Timer round
     */
    public void setMillisST(long millisST) {
        this.millisST = millisST;
    }

    /**
     * 
     * @return the player's time at Quick Answer round
     */
    public long getMillisQA() {
        return millisQA;
    }

    /**
     * 
     * @param millisQA a given player's time at Quick Answer round
     */
    public void setMillisQA(long millisQA) {
        this.millisQA = millisQA;
    }

    /**
     * 
     * @return the player's bid
     */
    public int getBetBid() {
        return betBid;
    }

    /**
     * 
     * @param betBid a given player's bid
     */
    public void setBetBid(int betBid) {
        this.betBid = betBid;
    }

    /**
     * 
     * @return the player's number of correct answers
     */
    public int getThermometerCounter() {
        return thermometerCounter;
    }

    /**
     * 
     * @param thermometerCounter a given player's number of correct answers
     */
    public void setThermometerCounter(int thermometerCounter) {
        this.thermometerCounter = thermometerCounter;
    }
}
