package SERVER_JC;
import java.io.Serializable;
import java.util.Vector;

public class ServerDatabase implements Serializable{
    private static ServerDatabase sd;
    public Vector<User> userList; //The friend list of the user.
    public Vector<Tweet> tweetList;

    private ServerDatabase(){
        this.userList = new Vector<>();
        this.tweetList = new Vector<>();
    }

    public static ServerDatabase getServerDatabase(){
        if(sd == null){
            sd = new ServerDatabase();
            return sd;
        }else{
            return sd;
        }
    }

}
