package client; /**
 * Created by Chao Ji on 2016-06-04.
 */

import java.io.IOException;
import java.util.Vector;

public class Client {
    private static Client INSTANCE;
    private ClientInterface ci;

    private Client(){
        try{
            ci = new ClientInterface();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static Client getClient(){
        if(INSTANCE == null){
            INSTANCE = new Client();
            return INSTANCE;
        }else{
            return INSTANCE;
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
            String introduction = replySet[2];
            User user = new User(userId,nickname,password,introduction);
            return user;
        }else{
            System.out.println("[错误]" + replySet[1]);
            User failUser = new User(-1,replySet[1],"");
            return failUser;
        }
    }

    public void handleUpdateProfile(int id,String nickname,String password,String introduction){
        String totalMsg = "[Client-UpdateUserInfo]" + "|" + id + "|" + nickname + "|" + password + "|" + introduction;
        ci.sendToServer(totalMsg);
        System.out.println("[提示]处理更新用户的资料信息已经发送");
    }

    /*
    返回信息格式:   [报头] | [用户名] | [用户介绍]
    报头为:  [Server-FindUserById] 的时候表示有查找到那一个用户
             [Server-FindNoneByName] 的时候表示有查找失败
     */
    public User searchUserById(int id){
        String searchRequest = "[Client-SearchUserById]" + "|" + id;
        ci.sendToServer(searchRequest);
        String replyStr = ci.receiveFromServer();
        String[] replySet = replyStr.split("\\|");
        User user;
        if("[Server-FindUserById]".equals(replySet[0])){
            String nickname = replySet[1];
            String introduction = replySet[2];
            user = new User(id,nickname,"Cannot Get Others Password",introduction);
        }else{
            user = new User(-1,"No such user","No such user","No such user");
        }
        return user;
    }

    /*
    返回信息格式:   [报头] | [结果数量] | [用户id] | [用户介绍] | [用户id] | [用户介绍]
    报头为:   [Server-FindUserByName] 的时候表示有查找到那一个用户
              [Server-FindNoneByName] 的时候表示有查找失败
    */
    public Vector<User> searchUserByName(String nameWanted){
        String searchRequest = "[Client-SearchUserByName]" + "|" + nameWanted;
        ci.sendToServer(searchRequest);
        String replyStr = ci.receiveFromServer();
        String[] replySet = replyStr.split("\\|");
        if("[Server-FindUserByName]".equals(replySet[0])){
            Vector<User> retUserList = new Vector<>();
            int num = Integer.parseInt(replySet[1]);
            for(int i = 0;i < num;i++){
                int id = Integer.parseInt(replySet[2 + 2*i]);
                String introduction = replySet[2 + 2*i + 1];
                User user = new User(id,nameWanted,"Cannot Get Others Password",introduction);
                retUserList.add(user);

            }
            return retUserList;
        }else{
            Vector<User> retUserList = new Vector<>();
            return retUserList;
        }
    }

    //这个函数还没补充完整
    public void uploadClientServerAddress(){
        //获取客户端产生的用于p2p连接的自己的服务器的地址

    }

    //这个函数还没补充完整
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
        while(true) {
            Client c = new Client();
            //测试注册是否有效
            User u = c.handleRegister("jichaofdu", "fuckingproject");
            System.out.println("收到的用户的id:" + u.getId());
            //测试登录是否有效
            User u2 = c.handleLogin(u.getId(), "fuckingproject");
            System.out.println("收到的用户的昵称:" + u2.getNickname());
            System.out.println("用户的介绍:" + u.getIntroduction());
            //测试更新用户信息是否有效。修改信息后再登录是为了获得用户昵称
            c.handleUpdateProfile(u.getId(), "asdadasda", "fuckingproject", "I love travel");
            User u3 = c.handleLogin(u.getId(), "fuckingproject");
            System.out.println("收到的用户的昵称:" + u3.getNickname());
            System.out.println("用户的介绍:" + u3.getIntroduction());
            //
            //c.ci.sendToServer("[Shutdown]");
        }
    }

}