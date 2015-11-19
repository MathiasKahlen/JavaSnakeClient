package SDK.Model;

/**
 * Created by Kahlen on 18-11-2015.
 */
public class Session {

    private String jwtToken;
    private User currentUser;


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
