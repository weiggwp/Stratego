package Stratego.board;

public class Round {    //entity being send back to the front end

    private Move user;  //move consists of coordinate changes, move validation info,
                        //move result, and game status

    private Move ai;

    public Move getUser() {
        return user;
    }

    public void setUser(Move user) {
        this.user = user;
    }

    public Move getAi() {
        return ai;
    }

    public void setAi(Move ai) {
        this.ai = ai;
    }
}
