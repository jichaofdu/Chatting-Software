/**
 * Created by Chao Ji on 2016-06-04.
 */

import java.util.Vector;

public class Client {
    ClientInterface ci;

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
        String[] replySet = loginReply.split("\\|");
        if(replySet[0] == "[Server-LoginSuccess]"){
            String nickname = replySet[1];
            User user = new User(userId,nickname,password);
            return user;
        }else{
            System.out.println("[错误]" + replySet[1]);
            User failUser = new User(-1,"","");
            return failUser;
        }
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

}