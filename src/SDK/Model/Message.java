package SDK.Model;

/**
 * Created by Kahlen on 31-10-2015.
 */
public class Message {
    private String message;
    private int status;
    private int userid;


    public Message(){

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserid() {
        return userid;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
