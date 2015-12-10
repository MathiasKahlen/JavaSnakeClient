package SDK.Model;

/**
 * Created by nicolaiostergaard on 12/10/15.
 */
public class Score {

    private int id;
    private Gamer user;
    private Gamer opponent;
    private Game game;
    private int score;

    public Score(int id, Gamer user, Game game, Gamer opponent, int score)
    {
        this.id = id;
        this.user = user;
        this.game = game;
        this.opponent = opponent;
        this.score = score;
    }

    public Score(){}

    public Gamer getOpponent() { return opponent; }
    public void setOpponent(Gamer opponent) { this.opponent = opponent;  }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Gamer getUser() {
        return user;
    }
    public void setUser(Gamer user) {
        this.user = user;
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
