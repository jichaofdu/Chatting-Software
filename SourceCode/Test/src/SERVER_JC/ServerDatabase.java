package SERVER_JC;
import java.io.Serializable;
import java.util.Vector;

public class ServerDatabase implements Serializable{
    private static ServerDatabase sd;
    public Vector<User> userList; //The friend list of the user.
    public Vector<Tweet> tweetList;
    public Group chatGroup;
    private int userBaseId = 10000;

    private ServerDatabase(){
        this.userList = new Vector<>();
        this.tweetList = new Vector<>();
        this.chatGroup = new Group(0);
    }

    public static ServerDatabase getServerDatabase(){
        if(sd == null){
            sd = new ServerDatabase();
            return sd;
        }else{
            return sd;
        }
    }

    public int generateNewUserId(){
        this.userBaseId += 1;
        return this.userBaseId;
    }

}
