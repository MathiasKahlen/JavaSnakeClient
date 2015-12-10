package SDK;

import SDK.Model.*;
import SDK.SDKConfig.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kahlen on 31-10-2015.
 */
public class ServerConnection implements SnakeClient{

    private Session session;
    private CachedData cachedData;

    public ServerConnection() {
        //TODO: Perhaps it is better to instantiate new Session objects every time a user log in intead of reusing the same object?
        session = new Session();
        cachedData = new CachedData();
        Config.init();
    }


    /**
     * Used in all methods that require get requests to the server
     * @param path path to the endpoint after /api/
     *             ex. "games/" will go to /api/games/
     * @param token get the token from session's variable: "jwtToken"
     *              token is used for authorization at some endpoints
     * @return returns a ClientResponse which can be used in all methods that require get requests to the server
     */
    //Set token to null in methods that don't require a token
    public ClientResponse get(String path, String token){
        Client client = Client.create();

        //Timeout after 5 seconds
        client.setConnectTimeout(1000*5);
        client.setReadTimeout(1000*5);

        WebResource webResource = client.resource(Config.getHost() + ":" + Config.getPort() + "/api/" + path);
        ClientResponse response = webResource
                .header("jwt", token)
                .type("application/json")
                .get(ClientResponse.class);

        if (response.getStatus()==401){
            logout();
        }

        return response;
    }

    /**
     * Used in all methods that require post requests to the server
     * @param json content of the post request
     * @param path path to the endpoint after /api/
     *             ex. "games/" will go to /api/games/
     * @param token get the token from session's variable: "jwtToken"
     *              token is used for authorization at some endpoints
     * @return returns a ClientResponse which can be used in all methods that require post requests to the server
     */
    //Set token to null in methods that don't require a token
    public ClientResponse post(String json, String path, String token){
        Client client = Client.create();

        //Timeout after 5 seconds
        client.setConnectTimeout(1000*5);
        client.setReadTimeout(1000*5);

        WebResource webResource = client.resource(Config.getHost() + ":" + Config.getPort() + "/api/" + path);
        ClientResponse response = webResource
                .header("jwt", token)
                .type("application/json")
                .post(ClientResponse.class, json);

        if (response.getStatus()==401){
            logout();
        }

        return response;
    }

    /**
     * Used in all methods that require put requests to the server
     * @param json content of the put request
     * @param path path to the endpoint after /api/
     *             ex. "games/" will go to /api/games/
     * @param token get the token from session's variable: "jwtToken"
     *              token is used for authorization at some endpoints
     * @return returns a ClientResponse which can be used in all methods that require put requests to the server
     */
    //Set token to null in methods that don't require a token
    public ClientResponse put(String json, String path, String token){
        Client client = Client.create();

        //Timeout after 5 seconds
        client.setConnectTimeout(1000*5);
        client.setReadTimeout(1000*5);

        WebResource webResource = client.resource(Config.getHost() + ":" + Config.getPort() + "/api/" + path);
        ClientResponse response = webResource
                .header("jwt", token)
                .type("application/json")
                .put(ClientResponse.class, json);

        if (response.getStatus()==401){
            logout();
        }

        return response;
    }

    /**
     * Used in all methods that require delete requests to the server
     * @param path path to the endpoint after /api/
     *             ex. "games/" will go to /api/games/
     * @param token get the token from session's variable: "jwtToken"
     *              token is used for authorization at some endpoints
     * @return returns a ClientResponse which can be used in all methods that require delete requests to the server
     */
    //Set token to null in methods that don't require a token
    public ClientResponse delete(String path, String token){
        Client client = Client.create();

        //Timeout after 5 seconds
        client.setConnectTimeout(1000*5);
        client.setReadTimeout(1000*5);

        WebResource webResource = client.resource(Config.getHost() + ":" + Config.getPort() + "/api/" + path);
        ClientResponse response = webResource
                .header("jwt", token)
                .type("application/json")
                .delete(ClientResponse.class);

        if (response.getStatus()==401){
            logout();
        }

        return response;
    }

    public String login(String username, String password){

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        String json = new Gson().toJson(user);

        ClientResponse response = post(json, "login", null);

        JSONObject jsonObject = responseToJson(response);

        if (response.getStatus() == 200){
            int id = (int)(long)jsonObject.get("userid");
            session.setJwtToken(response.getHeaders().getFirst("jwt"));
            session.setCurrentUser(getUser(id));
        }

        return (String)jsonObject.get("message");
    }

    /**
     * clears all variables in the Session class
     * used when a user is logged out
     * Read to-do in Constructor
     */
    public void logout(){
        session.clear();
    }

    /**
     * Used for getting a specific user - currently only used in login method.
     * Perhaps the server should be modified to return the user from the login endpoint instead of just the id.
     * This would cause only one server-request to be needed instead of two when logging in
     * @param id the id of the required user
     * @return returns a User object.
     */
    public User getUser(int id){

        ClientResponse response = get("users/" + Integer.toString(id), null);

        return new Gson().fromJson(response.getEntity(String.class), User.class);
    }

    /**
     * Requests a list of all users from the server
     * If HTTP status code 200 is returned it will store the list in the allUsers ArrayList in the Session object
     */
    public void getAllUsers(){

        String token = session.getJwtToken();
        ClientResponse response = get("users", token);

        if(response.getStatus()==200){
            ArrayList<User> users = new Gson().fromJson(response.getEntity(String.class), new TypeToken<List<User>>(){}.getType());
            cachedData.setAllUsers(users);
        }
    }

    /**
     * Method for creating a user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param email the user's email
     * @param username a specified username
     * @param password a specified password
     * @return returns the message received in the response entity from the server
     */
    public String createUser(String firstName, String lastName, String email, String username, String password) {

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        String json = new Gson().toJson(user);

        ClientResponse response = post(json, "users", null);

        return (String)responseToJson(response).get("message");
    }

    /**
     * Method for creating a new game
     * @param gameName Name of the game
     * @param mapSize Size of the map
     * @param opponentId The id of the invited opponent OR 0 if game should be open
     * @param hostControls The host's controls
     * @return returns the message received in the response entity from the server
     */
    public String createGame(String gameName, int mapSize, int opponentId, String hostControls) {

        Game game = new Game();
        Gamer opponent = new Gamer();
        Gamer host = new Gamer();

        //If opponentId is set to 0 an open game will be created
        //Otherwise an invitation will be sent to the user with the opponentId
        if (opponentId == 0){
            opponent = null;
        } else {
            opponent.setId(opponentId);
            game.setOpponent(opponent);
        }

        //Sets the users controls
        game.setHost(host);
        game.getHost().setControls(hostControls);
        //Sets game name and size of the map
        game.setName(gameName);
        game.setMapSize(mapSize);

        String token = session.getJwtToken();
        String json = new Gson().toJson(game);

        ClientResponse response = post(json, "games", token);

        JSONObject jsonObject = responseToJson(response);

        return (String)jsonObject.get("message");
    }

    /**
     * Method for joining an open game
     * @param gameId The game to be joined
     * @return returns true if HTTP status code of the response is 200
     */
    public boolean joinGame(int gameId) {
        //Set the token
        String token = session.getJwtToken();
        //The game to join
        Game game = new Game();
        game.setGameId(gameId);
        //Converting to json
        String json = new Gson().toJson(game);
        //Sending the put and getting a response
        ClientResponse response = put(json, "games/join", token);
        //Return true if game was join or false if it wasnt
        return response.getStatus() == 200;
    }

    /**
     * Method for starting (and finishing) a game that is pending
     * Basically the method for the opponent to play it's turn in the game which also returns the result of the game
     * @param gameId Id of the game that is played
     * @param controls The opponent's controls
     * @return returns a Game object containing the result of the game with scores in the Gamer objects and
     * a winner object
     */
    public Game startGame(int gameId, String controls) {
        //Set the token
        String token = session.getJwtToken();
        //The game to play
        Game game = new Game();
        game.setGameId(gameId);
        Gamer opponent = new Gamer();
        opponent.setControls(controls);
        game.setOpponent(opponent);
        //Converting to json
        String json = new Gson().toJson(game);
        //Sending the put and getting a response
        ClientResponse response = put(json, "games/start", token);

        return new Gson().fromJson(response.getEntity(String.class), Game.class);
    }

    /**
     * Method for deleting a game
     * @param gameId Id of the game to delete
     * @return returns the message received in the response entity from the server
     */
    public String deleteGame(int gameId) {
        //Set the token
        String token = session.getJwtToken();
        //Sending the put and getting a response
        ClientResponse response = delete("games/" + Integer.toString(gameId), token);

        //return the response message
        return (String)responseToJson(response).get("message");
    }

    /**
     * Requests a list of all games of a certain type depending on the parameter
     * If HTTP status code 200 is returned from the server the received list will be set in the associated
     * ArrayList in the Session object
     * @param gameStatus the status of the requested games
     */
    public void getCurrentUsersGames(String gameStatus) {

        ClientResponse response;
        String token = session.getJwtToken();

        switch (gameStatus){
            //Shows current users pending games
            //TODO: Could make static final Strings of the cases to simplify usage in the controllers
            case "pending":
                response = get("games/pending", token);
                if (response.getStatus()==200)
                    session.setPendingGames(new Gson().fromJson(response.getEntity(String.class), new TypeToken<List<Game>>(){}.getType()));
                break;
            //Shows games the current user hosted that are open or pending
            case "hosted":
                response = get("games/host", token);
                if (response.getStatus()==200)
                    session.setHostedGames(new Gson().fromJson(response.getEntity(String.class), new TypeToken<List<Game>>(){}.getType()));
                break;
            //Shows current users finished games
            case "finished":
                response = get("games/finished", token);
                if (response.getStatus()==200)
                    session.setFinishedGames(new Gson().fromJson(response.getEntity(String.class), new TypeToken<List<Game>>(){}.getType()));
                break;
        }
    }

    /**
     * Method for getting all open games in which the currentUser is not the host
     * If HTTP status code 200 is returned from the server the received list of games will be set in
     * the Session object's ArrayList "openJoinableGames"
     */
    public void getOpenGames() {

        String token = session.getJwtToken();
        ClientResponse response = get("games/open", token);

        if (response.getStatus()==200){
            getSession().setOpenJoinableGames(new Gson().fromJson(response.getEntity(String.class), new TypeToken<List<Game>>(){}.getType()));
        }
    }

    /**
     * Method for getting all the high scores
     * If HTTP status code 200 is returned from the server the received list of games will be set in
     * the CachedData object's ArrayList "highScores"
     */
    public void getHighScores() {

        String token = session.getJwtToken();
        ClientResponse response = get("scores", token);

        if(response.getStatus()==200) {
            ArrayList<Score> highscores = new Gson().fromJson(response.getEntity(String.class), new TypeToken<List<Score>>(){}.getType());
            cachedData.setHighScores(highscores);
        }
    }

    public Session getSession() {
        return session;
    }

    public CachedData getCachedData() {
        return cachedData;
    }


    /**
     * Use this method for parsing the Entity content of ClientResponses to a jsonObject
     * From this jsonObject it is possible to get specified data from the "key" in the JSON.
     * @param response Takes a ClientResponse as parameter
     *                 Use the ClientResponse from which you want to be able to get data
     * @return returns a JSONObject from which it is possible to get content of keys in the JSON string.
     */
    //Parsing the entity of a ClientResponse to json
    public JSONObject responseToJson(ClientResponse response){

        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(response.getEntity(String.class));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
