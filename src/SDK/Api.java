package SDK;

import SDK.Model.*;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * Created by Kahlen on 31-10-2015.
 */
public class Api implements SnakeClient{


    private Session session;
    private CachedData cachedData;

    private String hostAdress;
    private int port;

    public Api(){
        session = new Session();
        cachedData = new CachedData();
        this.hostAdress = "http://localhost";
        this.port = 9998;
    }


    //Set token to null in methods that don't require a token
    public ClientResponse get(String path, String token){
        Client client = Client.create();

        WebResource webResource = client.resource(getHostAdress() + ":" + getPort() + "/api/" + path);
        ClientResponse response = webResource
                .header("jwt", token)
                .type("application/json")
                .get(ClientResponse.class);

        return response;
    }

    //Set token to null in methods that don't require a token
    public ClientResponse post(String json, String path, String token){
        Client client = Client.create();

        WebResource webResource = client.resource(getHostAdress() + ":" + getPort() + "/api/" + path);
        ClientResponse response = webResource
                .header("jwt", token)
                .type("application/json")
                .post(ClientResponse.class, json);

        return response;
    }

    public ClientResponse put(String json, String path, String token){
        Client client = Client.create();

        WebResource webResource = client.resource(getHostAdress() + ":" + getPort() + "/api/" + path);
        ClientResponse response = webResource
                .header("jwt", token)
                .type("application/json")
                .put(ClientResponse.class, json);

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
        session.setCurrentUser(null);
        session.setJwtToken(null);
    }

    public User getUser(int id){
        Client client = new Client();

        User user;

        WebResource webResource = client.resource(getHostAdress() + ":" + getPort() + "/api/users/" + Integer.toString(id));
        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

        String test = response.getEntity(String.class);

        user = new Gson().fromJson(test, User.class);

        return user;
    }

    public boolean getAllUsers(){

        String token = session.getJwtToken();
        ClientResponse response = get("users", token);

        if(response.getStatus()==200){
            ArrayList<User> users = new Gson().fromJson(response.getEntity(String.class), ArrayList.class);
            cachedData.setAllUsers(users);
            return true;
        } else if (response.getStatus()==401){
            return false;
        }
        return false;
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


    public String createGame(String name, int opponentId, String hostControls) {

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

        //Sets the name of the game
        game.setName(name);

        String token = session.getJwtToken();
        String json = new Gson().toJson(game);

        ClientResponse response = post(json, "games", token);

        JSONObject jsonObject = responseToJson(response);

        return (String)jsonObject.get("message");
    }


    public String joinGame() {
        String token = session.getJwtToken();
        String json = null;

        ClientResponse response = put(json, "games/join", token);

        return (String)responseToJson(response).get("message");
    }


    public void startGame() {

    }


    public void deleteGame() {

    }


    public Game getGame() {
        return null;
    }


    public ArrayList<Game> getCurrentUsersGames() {
        return null;
    }


    public ArrayList<Game> getOpenGames() {
        return null;
    }


    public ArrayList<Score> getCurrentUsersScores() {
        return null;
    }


    public void getHighScores() {

        String token = session.getJwtToken();
        ClientResponse response = get("scores", token);

        if(response.getStatus()==200) {
            ArrayList<Score> highscores = new Gson().fromJson(response.getEntity(String.class), ArrayList.class);
            cachedData.setHighScores(highscores);
        }
    }

    public Session getSession() {
        return session;
    }

    public CachedData getCachedData() {
        return cachedData;
    }

    public void setHostAdress(String hostAdress) {
        this.hostAdress = hostAdress;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostAdress() {
        return hostAdress;
    }

    public int getPort() {
        return port;
    }

    /*
    Method for getting the "message" key from json in response entity
     */
    public JSONObject responseToJson(ClientResponse response){
        //Used for getting the message in the response
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(response.getEntity(String.class));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
