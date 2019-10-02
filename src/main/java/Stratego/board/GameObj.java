package Stratego.board;

public class GameObj {
    long GameID;

    public long getGameID() {
        return GameID;
    }

    public GameObj()
    {

    }
    public void setGameID(long gameID) {
        this.GameID = gameID;
    }

    public GameObj(long gameID) {
        this.GameID = gameID;
    }
}
