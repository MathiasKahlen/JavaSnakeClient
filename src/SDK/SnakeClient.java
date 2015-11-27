package SDK;

import SDK.Model.*;

import java.util.ArrayList;

/**
 * Created by Kahlen on 18-11-2015.
 */
public interface SnakeClient {

    //Login Method
    String login(String username, String password);

    //Logout method
    void logout();

    //Get user for setting currentUser in session
    User getUser(int id);

    //Get all users
    boolean getAllUsers();

    String createUser(String firstName, String lastName, String email, String username, String password);

    String createGame(String name, int opponentId, String hostControls);

    String joinGame();

    void startGame();

    void deleteGame();

    Game getGame();

    ArrayList<Game> getCurrentUsersGames();

    ArrayList<Game> getOpenGames();

    ArrayList<Score> getCurrentUsersScores();

    void getHighScores();





}
