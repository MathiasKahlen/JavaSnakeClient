package SDK.Model;

import java.util.ArrayList;

/**
 * Created by Kahlen on 18-11-2015.
 */
public class CachedData {

    private ArrayList<User> allUsers;
//    private ArrayList<Game> allGames;

    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }
    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

//    public void setAllGames(ArrayList<Game> allGames) {
//        this.allGames = allGames;
//    }
//    public ArrayList<Game> getAllGames() {
//        return allGames;
//    }
//    public void updateGames(ArrayList<Game> gamesToUpdate){
//        for(Game g : allGames){
//            if(g.getStatus.equals("pending")){
//                allGames.remove(g);
//            }
//        for (Game g : gamesToUpdate){
//            allGames.add(g);
//        }
//        }
//    }
}
