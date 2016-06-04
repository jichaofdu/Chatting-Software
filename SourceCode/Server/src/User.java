/**
 * Created by Chao Ji on 2016-06-03.
 */
import java.util.Vector;

public class User {

    private int id; //The id of user,used to login.
    private String nickname; //The username of the user.
    private String password; //The password of the user.
    private Vector<Integer> friendList; //The friend list of the user.

    User(int id,String nickname,String password){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        friendList = new Vector<Integer>();
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

    /**
     * Check a string is whether the user's password.
     * @param inputPasswd The string of the password that will be checked.
     * @return The check result. True if ok and false is wrong.
     */
    public boolean checkNamePasswdMatch(String inputPasswd){
        if((this.password).equals(inputPasswd) == true){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Check whether a user is the friend of the user.
     * @param userId The target user id you want to search.
     * @return The check result.
     */
    public boolean checkIsFriend(int userId){
        for(int i = 0;i < friendList.size();i++) {
            if (friendList.get(i) == userId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a new friend to the user.
     * @param userId The id of the new friend.
     */
    public void addFriend(int userId){
        friendList.add(userId);
    }

    /**
     * Delete the a friend in the friend list.
     * @param userId The id of the friend that will be deleted.
     */
    public void deleteFriend(int userId){
        for(int i = 0;i < friendList.size();i++) {
            if (friendList.get(i) == userId) {
                friendList.remove(i);
                break;
            }
        }
    }




}
