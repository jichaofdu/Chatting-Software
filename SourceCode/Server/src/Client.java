/**
 * Created by Chao Ji on 2016-06-04.
 */

import java.io.IOException;
import java.util.Vector;

public class Client {
    ClientInterface ci;

    public Client(){
        try{
            ci = new ClientInterface();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public User handleRegister(String nickname,String password){
        String totalMsg = "[Client-Register]" + "|" + nickname + "|" + password;
        ci.sendToServer(totalMsg);
        String registerReply = ci.receiveFromServer();
        String[] replySet = registerReply.split("\\|");
        if("[Server-RegisterSuccess]".equals(replySet[0])){
            String idString = replySet[1];
            System.out.println(idString);
            int id = Integer.parseInt(idString);
            User newUser = new User(id,nickname,password);
            return newUser;
        }else{
            System.out.println("[错误]注册失败");
            User failUser = new User(-1,"","");
            return failUser;
        }
    }

    public User handleLogin(int userId,String password){
        String totalMsg = "[Client-Login]" + "|" + userId + "|" + password;
        ci.sendToServer(totalMsg);
        String loginReply = ci.receiveFromServer();
        System.out.println("--" + loginReply);
        String[] replySet = loginReply.split("\\|");
        if("[Server-LoginSuccess]".equals(replySet[0])){
            String nickname = replySet[1];
            User user = new User(userId,nickname,password);
            return user;
        }else{
            System.out.println("[错误]" + replySet[1]);
            User failUser = new User(-1,"","");
            return failUser;
        }
    }

    public void handleUpdateProfile(int id,String nickname,String password,String introduction){
        String totalMsg = "[Client-UpdateUserInfo]" + "|" + id + "|" + nickname + "|" + password + "|" + introduction;
        ci.sendToServer(totalMsg);
        System.out.println("[提示]处理更新用户的资料信息已经发送");
    }

    public void uploadClientServerAddress(){
        //获取客户端产生的用于p2p连接的自己的服务器的地址

    }

    public Vector<User> getFriendList(int userId){
        String totalMsg = "[Client-GetFriendList]" + "|" + userId;
        ci.sendToServer(totalMsg);
        String getFriendListReply = ci.receiveFromServer();
        String[] replyList = getFriendListReply.split("\\|");
        Vector<User> friendList = new Vector<>();

        //具体的整理回复方法以后再做实现
        //To-do

        return friendList;
    }

    public static void main(String[] args){
        Client c = new Client();
        User u = c.handleRegister("jichaofdu","fuckingproject");
        System.out.println("收到的用户的id:" + u.getId());
        User u2 = c.handleLogin(u.getId(),"fuckingproject");
        System.out.println("收到的用户的昵称:" + u2.getNickname());
        c.ci.sendToServer("[Shutdown]");
    }

}