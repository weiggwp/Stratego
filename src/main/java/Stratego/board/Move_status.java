package Stratego.board;

public class Move_status {

    private boolean is_valid_move;
    private String error_message;   //display err_msg on board if invalid move

    private boolean game_ended;    //indicates if game has ended
    private String game_result;    //if game ended, either be win:lose:draw:null (game did not end)

    private int fight_result;   //4 for no fight, 0 user win, 1 user lose, 2 tie, 3 game_over
    private char pieceCapturedByPlayer=' ';
    private char pieceCapturedByComputer=' ';

    private String image_src;  //what is left on the board /* if AI captures a user piece, it must be revealed*/

    private char piece_name; // name/rank of the piece


    public char getPieceCapturedByPlayer() {
        return pieceCapturedByPlayer;
    }

    public void setPieceCapturedByPlayer(char pieceCapturedByPlayer) {
        this.pieceCapturedByPlayer = pieceCapturedByPlayer;
    }
    public Move_status()
    {
        this.notValid(false,"");    //initialize to not valid
        this.game_ended = false;
    }
    public void notValid(boolean is_valid,String error)  //if invalid, don't need to set anything else
    {
        this.is_valid_move = is_valid;
        this.error_message = error;
    }
    public char getPieceCapturedByComputer() {
        return pieceCapturedByComputer;
    }

    public void setPieceCapturedByComputer(char pieceCapturedByComputer) {
        this.pieceCapturedByComputer = pieceCapturedByComputer;
    }

    public char getPiece_name() {
        return piece_name;
    }

    public void setPiece_name(char piece_name) {
        this.piece_name = piece_name;
    }

    public boolean isIs_valid_move() {
        return is_valid_move;
    }

    public void setIs_valid_move(boolean is_valid_move) {
        this.is_valid_move = is_valid_move;
        if(!is_valid_move)
        {
            this.game_ended=false;
            this.fight_result=-1;

        }
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public boolean isGame_ended() {
        return game_ended;
    }

    public void gameEnded()
    {
        this.game_ended = true;
        this.game_result = "win";
    }


    public String getGame_result() {
        return game_result;
    }

    public void setGame_result(String game_result) {
        this.game_result = game_result;
    }


    public String getImage_src() {
        return new String(image_src);
    }

    public int getFight_result() {
        return fight_result;
    }

    public void setFight_result(int fight_result) {
        this.fight_result = fight_result;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

}
