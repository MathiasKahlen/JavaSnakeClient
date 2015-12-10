package SDK;

import SDK.Model.*;

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
    void getAllUsers();

    String createUser(String firstName, String lastName, String email, String username, String password);

    String createGame(String name, int mapSize, int opponentId, String hostControls);

    boolean joinGame(int gameId);

    Game startGame(int gameId, String controls);

    String deleteGame(int gameId);

    void getCurrentUsersGames(String gameStatus);

    void getOpenGames();

    void getHighScores();





}
