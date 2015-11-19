//package SDK;
//
//import SDK.Model.Message;
//import SDK.Model.User;
//import com.google.gson.Gson;
//import com.sun.jersey.api.client.ClientResponse;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
///**
// * Created by Kahlen on 31-10-2015.
// */
//public class Logic {
//
//    private Api connection = new Api();
//
//    public Message login(String username, String password){
//
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//
//        Message message = new Message();
//
//
//        ClientResponse login = connection.post(new Gson().toJson(user), "login");
//
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = (JSONObject) new JSONParser().parse(login.getEntity(String.class));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        if (login.getStatus() == 200) {
//            message.setMessage((String) jsonObject.get("message"));
//            message.setUserid((int) (long) jsonObject.get("userid"));
//            message.setStatus(login.getStatus());
//
//            getUser(message.getUserid());
//        }
//        else{
//            message.setMessage((String) jsonObject.get("message"));
//            message.setStatus(login.getStatus());
//        }
//
//        return message;
//    }
//
//    public User getUser(int id){
//        User user = new User();
//        String path = "user/" + Integer.toString(id);
//
//        connection.get(path);
//
//        return user;
//    }
//
//
//
//}
