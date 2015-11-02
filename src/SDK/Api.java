package SDK;

import SDK.Model.User;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Kahlen on 31-10-2015.
 */
public class Api {


    private User currentUser = null;

    private String hostAdress;
    private int port;

    public Api(){
        this.hostAdress = "http://localhost";
        this.port = 9998;
    }



    public void get(String path){
        Client client = Client.create();

        WebResource webResource = client.resource(getHostAdress() + ":" + getPort() + "/api/" + path);
        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

        String output = response.getEntity(String.class);
        System.out.println(output);
    }

    public ClientResponse post(String json, String path){
        Client client = Client.create();

        WebResource webResource = client.resource(getHostAdress() + ":" + getPort() + "/api/" + path);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, json);

        return response;
    }

    public String login(String username, String password){
        Client client = Client.create();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        String json = new Gson().toJson(user);


        WebResource webResource = client.resource(getHostAdress() + ":" + getPort() + "/api/login/");
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, json);

        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(response.getEntity(String.class));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == 200){

            int id = (int)(long)jsonObject.get("userid");

            setCurrentUser(getUser(id));
        }

        return (String)jsonObject.get("message");
    }

    public void logout(){
        setCurrentUser(null);
    }



    public User getUser(int id){
        Client client = new Client();

        User user;

        WebResource webResource = client.resource(getHostAdress() + ":" + getPort() + "/api/user/" + Integer.toString(id));
        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);

        String test = response.getEntity(String.class);

        user = new Gson().fromJson(test, User.class);

        return user;
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

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
