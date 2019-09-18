package Stratego.board;

public class Move_status {
    private boolean is_valid_move;
    private String error_message;   //display err_msg on board if invalid move

    private boolean game_ended;    //indicates if game has ended
    private String game_result;    //if game ended, either be win:lose:draw:null (game did not end)

    private boolean capture;
    private String captured;       //who is captured, capturing, or tied
    private String image_src;  /* if AI captures a user piece, it must be revealed*/


    public boolean isIs_valid_move() {
        return is_valid_move;
    }

    public void setIs_valid_move(boolean is_valid_move) {
        this.is_valid_move = is_valid_move;
        if(!is_valid_move)
        {
            this.game_ended=false;
            this.capture=false;

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

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public String getCaptured() {
        return captured;
    }

    public void setCaptured(String captured) {
        this.captured = captured;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
