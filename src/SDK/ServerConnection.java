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

    public void logout(){
        session.clear();
    }

    public User getUser(int id){

        ClientResponse response = get("users/" + Integer.toString(id), null);

        return new Gson().fromJson(response.getEntity(String.class), User.class);
    }

    public void getAllUsers(){

        String token = session.getJwtToken();
        ClientResponse response = get("users", token);

        if(response.getStatus()==200){
            ArrayList<User> users = new Gson().fromJson(response.getEntity(String.class), new TypeToken<List<User>>(){}.getType());
            cachedData.setAllUsers(users);
        }
    }


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


    public String deleteGame(int gameId) {
        //Set the token
        String token = session.getJwtToken();
        //Sending the put and getting a response
        ClientResponse response = delete("games/" + Integer.toString(gameId), token);

        //return the response message
        return (String)responseToJson(response).get("message");
    }


    public Game getGame() {
        return null;
    }


    public void getCurrentUsersGames(String gameStatus) {

        ClientResponse response;
        String token = session.getJwtToken();

        switch (gameStatus){
            //Shows current users pending games
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


    public void getOpenGames() {

        String token = session.getJwtToken();
        ClientResponse response = get("games/open", token);

        if (response.getStatus()==200){
            getSession().setOpenJoinableGames(new Gson().fromJson(response.getEntity(String.class), new TypeToken<List<Game>>(){}.getType()));
        }
    }

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
