package client;

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
        this.introduction = "No Introduction Yet";
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

    public String getPassword(){
        return this.password;
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

}
