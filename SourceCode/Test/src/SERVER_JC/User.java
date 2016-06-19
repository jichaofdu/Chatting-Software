/**
 * Created by Chao Ji on 2016-06-03.
 */
package SERVER_JC;
import java.util.Vector;

public class User {

    private int id;
    private String nickname;
    private String password;
    private String introduction;
    private Vector<Integer> friendList; //The friend list of the user.
    private boolean isLogin;

    public User(int id,String nickname,String password){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.introduction = "暂时没有添加用户介绍";
        this.isLogin = false;
        this.friendList = new Vector<>();
    }

    public User(int id,String nickname,String password,String introduction){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.introduction = introduction;
        this.isLogin = false;
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

    public void setIsLogin(boolean status){
        this.isLogin = status;
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

    public Vector<Integer> getFriendList(){
        return this.friendList;
    }

}
