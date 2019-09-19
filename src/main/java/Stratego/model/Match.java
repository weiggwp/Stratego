package Stratego.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="matchup")
public class Match {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "match_id")
    private long matchId;
    @Column(name = "user_id")
    @NotNull
    private long userId;
    @Column(name = "outcome")
    @NotNull
    private String outcome;


    public Match() {
    }
    public Match(long userId, String outcome) {
        this.userId = userId;
        this.outcome = outcome;
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


    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + matchId +
                ", userId=" + userId +
                ", outcome='" + outcome + '\'' +
                '}';
    }
}
