package client; /**
 * Created by Chao Ji on 2016-06-03.
 */
import java.util.Vector;

public class User {

    private int id; //The id of user,used to login.
    private String nickname; //The username of the user.
    private String password; //The password of the user.
    private String introduction;
    private String localClientAddress;
    private int localClientPort;
    private String localServerAddress;
    private int localServerPort;
    private Vector<Integer> friendList; //The friend list of the user.
    private boolean isLogin;

    public User(int id,String nickname,String password){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.introduction = "暂时没有添加用户介绍";
        this.isLogin = false;
        this.localClientAddress = "";
        this.localClientPort = 0;
        this.localServerAddress = "";
        this.localServerPort = 0;
        this.friendList = new Vector<>();
    }

    public User(int id,String nickname,String password,String introduction){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.introduction = introduction;
        this.isLogin = false;
        this.localClientAddress = "";
        this.localClientPort = 0;
        this.localServerAddress = "";
        this.localServerPort = 0;
        this.friendList = new Vector<>();
    }

    public int getId(){
        return this.id;
    }

    public String getNickname(){
        String username = this.nickname;
        return username;
    }

    public void setNickname(String newNick){
        this.nickname = newNick;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public String getIntroduction(){
        String retIntroduction = this.introduction;
        return retIntroduction;
    }

    public void setIntroduction(String newIntroduction){
        this.introduction = newIntroduction;
    }

    public boolean checkNamePasswdMatch(String inputPasswd){
        if((this.password).equals(inputPasswd) == true){
            return true;
        }else{
            return false;
        }
    }

    public boolean getIsLogin(){
        return this.isLogin;
    }

    public void setIsLogin(boolean status){
        this.isLogin = status;
    }

    public String getLocalClientAddress(){
        String clientAddress = this.localClientAddress;
        return clientAddress;
    }

    public void setLocalClientAddress(String newClientAddress){
        this.localClientAddress = newClientAddress;
    }

    public int getLocalClientPort(){
        return this.localClientPort;
    }

    public void setLocalClientPort(int newClientPort){
        this.localClientPort  = newClientPort;
    }

    public String getLocalServerAddress(){
        String myServerAddress = this.localServerAddress;
        return myServerAddress;
    }

    public void setLocalServerAddress(String newServerAddress){
        this.localServerAddress = newServerAddress;
    }

    public int getLocalServerPort(){
        return this.localServerPort;
    }

    public void setLocalServerPort(int newMyServerPort){
        this.localServerPort = newMyServerPort;
    }

    public boolean checkIsFriend(int userId){
        for(int i = 0;i < friendList.size();i++) {
            if (friendList.get(i) == userId) {
                return true;
            }
        }
        return false;
    }

    public void addFriend(int userId){
        friendList.add(userId);
    }

    public void deleteFriend(int userId){
        for(int i = 0;i < friendList.size();i++) {
            if (friendList.get(i) == userId) {
                friendList.remove(i);
                break;
            }
        }
    }

}
