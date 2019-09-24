package Stratego.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reposition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long moveId;
    private long matchId;
    private int turnId;
    private int startX;
    private int startY;
    private int curX;
    private int curY;
    private char pieceName;
    private char pieceCapturedByPlayer;
    private char pieceCapturedByOpponent;

    public Reposition() {
    }

    public Reposition(long matchId, int turnId, int startX, int startY, int curX, int curY,
                      char pieceName, char pieceCapturedByPlayer, char pieceCapturedByOpponent) {
        this.matchId = matchId;
        this.turnId = turnId;
        this.startX = startX;
        this.startY = startY;
        this.curX = curX;
        this.curY = curY;
        this.pieceName = pieceName;
        this.pieceCapturedByPlayer = pieceCapturedByPlayer;
        this.pieceCapturedByOpponent = pieceCapturedByOpponent;
    }

    public long getMoveId() {
        return moveId;
    }

    public void setMoveId(long moveId) {
        this.moveId = moveId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public int getTurnId() {
        return turnId;
    }

    public void setTurnId(int turnId) {
        this.turnId = turnId;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getCurX() {
        return curX;
    }

    public void setCurX(int curX) {
        this.curX = curX;
    }

    public int getCurY() {
        return curY;
    }

    public void setCurY(int curY) {
        this.curY = curY;
    }

    public char getPieceName() {
        return pieceName;
    }

    public void setPieceName(char pieceName) {
        this.pieceName = pieceName;
    }

    public char getPieceCapturedByPlayer() {
        return pieceCapturedByPlayer;
    }

    public void setPieceCapturedByPlayer(char pieceCapturedByPlayer) {
        this.pieceCapturedByPlayer = pieceCapturedByPlayer;
    }

    public char getPieceCapturedByOpponent() {
        return pieceCapturedByOpponent;
    }

    public void setPieceCapturedByOpponent(char pieceCapturedByOpponent) {
        this.pieceCapturedByOpponent = pieceCapturedByOpponent;
    }

    @Override
    public String toString() {
        return "Reposition{" +
                "moveId=" + moveId +
                ", matchId=" + matchId +
                ", turnId=" + turnId +
                ", startX=" + startX +
                ", startY=" + startY +
                ", curX=" + curX +
                ", curY=" + curY +
                ", pieceName='" + pieceName + '\'' +
                ", pieceCapturedByPlayer='" + pieceCapturedByPlayer + '\'' +
                ", pieceCapturedByOpponent='" + pieceCapturedByOpponent + '\'' +
                '}';
    }
}
