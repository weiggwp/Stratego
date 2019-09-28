package Stratego.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name="matchup")
public class Match {
    @Id
    @NotNull
    @Column(name = "match_id")
    private long matchId;
    @Column(name = "user_id")
    @NotNull
    private long userId;
    @Column(name = "outcome")
    @NotNull
    private String outcome;
    @Column(name = "unix_time")
    @NotNull
    private long unixTime;

    private String date;
    private String piecesLostPlayer;
    private String piecesLostComputer;



    public Match() {
    }
    public Match(long userId, String outcome) {
        this.userId = userId;
        this.outcome = outcome;
    }

    public Match(@NotNull long userId, @NotNull String outcome, @NotNull long unixTime) {
        this.userId = userId;
        this.outcome = outcome;
        this.unixTime = unixTime;
        this.date = convertUnixTime();

    }

    public Match(@NotNull long matchId, @NotNull long userId, @NotNull String outcome, @NotNull long unixTime) {
        this.matchId = matchId;
        this.userId = userId;
        this.outcome = outcome;
        this.unixTime = unixTime;
        this.date = convertUnixTime();
    }

    public Match(long matchId, long userId, String outcome) {
        this.matchId = matchId;
        this.userId = userId;
        this.outcome = outcome;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getOutcome() {
        return outcome;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getPiecesLostPlayer() {
        return piecesLostPlayer;
    }

    public String getPiecesLostComputer() {
        return piecesLostComputer;
    }

    public String getDate() {
        return date;
    }

    public void setPiecesLostPlayer(String piecesLostPlayer) {
        this.piecesLostPlayer = piecesLostPlayer;
    }

    public void setPiecesLostComputer(String piecesLostComputer) {
        this.piecesLostComputer = piecesLostComputer;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + matchId +
                ", userId=" + userId +
                ", outcome='" + outcome + '\'' +
                ", unixTime=" + unixTime +
                '}';
    }


    public String convertUnixTime() {

        Date date = new Date(this.unixTime);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("America/New_York"));
        String formattedDate = sdf.format(date);
        return formattedDate;

    }

    public void convertUnixTime_2() {

        Date date = new Date(this.unixTime);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("America/New_York"));
        String formattedDate = sdf.format(date);
        this.setDate(formattedDate);

    }


}
