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
    boolean getAllUsers(String token);

    String createUser(String firstName, String lastName, String email, String username, String password);

    void createGame(String token);

    void joinGame(String token);

    void startGame(String token);

    void deleteGame(String token);

    Game getGame(String token);

    ArrayList<Game> getCurrentUsersGames(int id, String token);

    ArrayList<Game> getOpenGames(String token);

    ArrayList<Score> getCurrentUsersScores(String token);

    void getHighScores(String token);





}
