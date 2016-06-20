package song;
import java.util.Vector;

public class User {
	public int isLogin;
    public int id; 
    public String nickname; 
    public String password; 
    public Vector<Integer> friendList; 
   
    public User(){
        this.id = -1;
        this.nickname = "null";
        this.password = "null";
        this.isLogin = 0;
        this.friendList = new Vector<Integer>(20);
    }
    public User(int id,String nickname,String password){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.isLogin = 0;
        this.friendList = new Vector<Integer>(20);
    }
}
