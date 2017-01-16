package file_operations;

import game_package.Player;

/**
 * Class to store two players and their matchup's individual scores - used for stats (file writing & reading)
 * @author desppapa
 * @author gogopavl
 */
public class Matchup {
    private Player p1;
    private Player p2;

    public Matchup(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }
    
    
}
