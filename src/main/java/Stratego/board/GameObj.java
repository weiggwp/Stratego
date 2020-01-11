package Stratego.board;

public class GameObj {
    long gameID;

    public long getGameID() {
        return gameID;
    }

    public GameObj()
    {

    }
    public void setGameID(long gameID) {
        this.gameID = gameID;
    }

    public GameObj(long gameID) {
        this.gameID = gameID;
    }
}
