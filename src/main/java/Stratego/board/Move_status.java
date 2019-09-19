package Stratego.board;

public class Move_status {
<<<<<<< HEAD
=======
    private boolean is_valid_move;
    private String error_message;   //display err_msg on board if invalid move

    private boolean game_ended;    //indicates if game has ended
    private String game_result;    //if game ended, either be win:lose:draw:null (game did not end)

    private int fight_result;   //-1 for no fight, 0 user win, 1 user lose, 2 tie
    private String piece_captured_by_player;
    private String piece_captured_by_ai;
    private String image_src;  //what is left on the board /* if AI captures a user piece, it must be revealed*/

    private String piece_name; // name/rank of the piece

    public String getPiece_name() {
        return piece_name;
    }

    public void setPiece_name(String piece_name) {
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

    public void setGame_ended(boolean game_ended) {
        this.game_ended = game_ended;
    }

    public String getGame_result() {
        return game_result;
    }

    public void setGame_result(String game_result) {
        this.game_result = game_result;
    }



    public String getImage_src() {
        return image_src;
    }

    public int getFight_result() {
        return fight_result;
    }

    public void setFight_result(int fight_result) {
        this.fight_result = fight_result;
    }

    public String getPiece_captured_by_player() {
        return piece_captured_by_player;
    }

    public void setPiece_captured_by_player(String piece_captured_by_player) {
        this.piece_captured_by_player = piece_captured_by_player;
    }

    public String getPiece_captured_by_ai() {
        return piece_captured_by_ai;
    }

    public void setPiece_captured_by_ai(String piece_captured_by_ai) {
        this.piece_captured_by_ai = piece_captured_by_ai;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
>>>>>>> e69e4b5bcec05759001baa44c3c66668a098b7eb
}
