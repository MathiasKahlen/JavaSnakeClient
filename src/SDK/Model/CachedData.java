package SDK.Model;

import java.util.ArrayList;

/**
 * Created by Kahlen on 18-11-2015.
 */
public class CachedData {

    private ArrayList<User> allUsers;
    private ArrayList<Score> highScores;

    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }
    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void setHighScores(ArrayList<Score> highScores) {
        this.highScores = highScores;
    }

    public ArrayList<Score> getHighScores() {
        return highScores;
    }
}
