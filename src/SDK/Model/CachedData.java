package SDK.Model;

import java.util.ArrayList;

/**
 * Created by Kahlen on 18-11-2015.
 */
public class CachedData {

    private ArrayList<User> allUsers;
    private ArrayList<Game> openGames;
    private ArrayList<Score> highScores;

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


    public void setOpenGames(ArrayList<Game> openGames) {
        this.openGames = openGames;
    }

    public ArrayList<Game> getOpenGames() {
        return openGames;
    }

    public void setHighScores(ArrayList<Score> highScores) {
        this.highScores = highScores;
    }

    public ArrayList<Score> getHighScores() {
        return highScores;
    }
}
