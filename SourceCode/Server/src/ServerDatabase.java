import java.io.Serializable;
import java.util.Vector;

/**
 * Created by chaoj on 2016-06-17.
 */
public class ServerDatabase implements Serializable{
    private static ServerDatabase sd;
    public Vector<User> userList; //The friend list of the user.

    private ServerDatabase(){
        this.userList = new Vector<>();
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
