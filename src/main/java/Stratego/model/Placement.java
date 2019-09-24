package Stratego.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dummyId;
    private long matchId;
    private int  x;
    private int  y;
    private char pieceName;
    private int isPlayer;

    public Placement(long matchId, int x, int y, char pieceName, int isPlayer) {
        this.matchId = matchId;
        this.x = x;
        this.y = y;
        this.pieceName = pieceName;
        this.isPlayer = isPlayer;
    }

    public Placement() {
    }

    public int getIsPlayer() {
        return isPlayer;
    }

    public void setIsPlayer(int isPlayer) {
        this.isPlayer = isPlayer;
    }

    public long getDummyId() {
        return dummyId;
    }

    public void setDummyId(long dummyId) {
        this.dummyId = dummyId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getPieceName() {
        return pieceName;
    }

    public void setPieceName(char pieceName) {
        this.pieceName = pieceName;
    }

    @Override
    public String toString() {
        return "Placement{" +
                "dummyId=" + dummyId +
                ", matchId=" + matchId +
                ", x=" + x +
                ", y=" + y +
                ", pieceName='" + pieceName + '\'' +
                ", isPlayer=" + isPlayer +
                '}';
    }
}
