package SDK.Model;

import java.util.ArrayList;

/**
 * Created by Kahlen on 18-11-2015.
 */
public class Session {

    private String jwtToken;
    private User currentUser;

    private ArrayList<Game> pendingGames;
    private ArrayList<Game> hostedGames;
    private ArrayList<Game> finishedGames;


    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setPendingGames(ArrayList<Game> pendingGames) {
        this.pendingGames = pendingGames;
    }

    public ArrayList<Game> getPendingGames() {
        return pendingGames;
    }

    public void setHostedGames(ArrayList<Game> hostedGames) {
        this.hostedGames = hostedGames;
    }

    public ArrayList<Game> getHostedGames() {
        return hostedGames;
    }

    public void setFinishedGames(ArrayList<Game> finishedGames) {
        this.finishedGames = finishedGames;
    }

    public ArrayList<Game> getFinishedGames() {
        return finishedGames;
    }
}
