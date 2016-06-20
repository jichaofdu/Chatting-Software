package client; /**
 * Created by Chao Ji on 2016-06-04.
 */

import java.io.IOException;
import java.util.Vector;

public class Client {
    private static boolean isClose = true;
    private static Client INSTANCE;
    private ClientInterface ci;
    private User localUser;

    public Client(){
        System.out.println("Begin Client");
        try{
            ci = new ClientInterface();
        }catch(IOException e){
            e.printStackTrace();
        }
        this.localUser = new User(-1,"","");
        System.out.println("End Client");
    }

    public static Client getClient(){
        if(isClose == true){
            INSTANCE = new Client();
            isClose = false;
            return INSTANCE;
        }else{
            return INSTANCE;
        }
    }

    public Vector<ChatMessage> sendChatMessage(String sendContent){
        int id = localUser.getId();
        String nickname = localUser.getNickname();
        String content = sendContent;
        String sendMsg = "[Client-ChatMessage]" + "|" + id + "|" + nickname + "|" + content;
        ci.sendToServer(sendMsg);
        System.out.println("Send Message");
        String replyMsg = ci.receiveFromServer();
        String[] replySet = replyMsg.split("\\|");
        int count = Integer.parseInt(replySet[1]);
        Vector<ChatMessage> returnMsgs  = new Vector<>();
        for(int i = 0;i < count;i++){
            String tempIdString = replySet[2 + i*4 + 0];
            int tempId = Integer.parseInt(tempIdString);
            String tempNickname = replySet[2 + i*4 + 1];
            String tempContent = replySet[2 + i*4 + 2];
            String tempTime = replySet[2 + i*4 + 3];
            ChatMessage tempMsg = new ChatMessage(tempId,tempNickname,tempContent,tempTime);
            returnMsgs.add(tempMsg);
        }
        return returnMsgs;
    }

    public Vector<ChatMessage> getChatMessage(){
        String getMsgs = "[Client-GetChatMessage]" + "|" + Client.getClient().getLocalUser().getId();
        ci.sendToServer(getMsgs);
        String replyMsg = ci.receiveFromServer();
        String[] replySet = replyMsg.split("\\|");
        int count = Integer.parseInt(replySet[1]);
        Vector<ChatMessage> returnMsgs  = new Vector<>();
        for(int i = 0;i < count;i++){
            String tempIdString = replySet[2 + i*4 + 0];
            int tempId = Integer.parseInt(tempIdString);
            String tempNickname = replySet[2 + i*4 + 1];
            String tempContent = replySet[2 + i*4 + 2];
            String tempTime = replySet[2 + i*4 + 3];
            ChatMessage tempMsg = new ChatMessage(tempId,tempNickname,tempContent,tempTime);
            returnMsgs.add(tempMsg);
        }
        return returnMsgs;
    }

    public void addFriend(int id){
    	String addMsg = "[Client-AddFriendConfirm]" + "|" + Client.getClient().getLocalUser().getId() + "|" + id;
        ci.sendToServer(addMsg);
        System.out.println("Add friend");
    }

    public void deleteFriend(int id){
        String deleteMsg = "[Client-DeleteFriend]" + "|" + Client.getClient().getLocalUser().getId() + "|" + id;
        ci.sendToServer(deleteMsg);
        System.out.println("Delete Friend");
    }
    
    public void addNewTweet(String content){
        String addTweetMsg = "[Client-AddTweet]" + "|" + localUser.getId() + "|" + content;
        ci.sendToServer(addTweetMsg);
        System.out.println("Add Tweet Complete");
    }

    public User getLocalUser(){
        return this.localUser;
    }

    public User handleRegister(String nickname, String password){
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
            System.out.println("[閿欒]娉ㄥ唽澶辫触");
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
            this.localUser = user;//Local user get if login success.
            return user;
        }else{
            System.out.println("[閿欒]" + replySet[1]);
            User failUser = new User(-1,replySet[1],"");
            return failUser;
        }
    }

    public void handleUpdateProfile(int id, String nickname, String password, String introduction){
        String totalMsg = "[Client-UpdateUserInfo]" + "|" + id + "|" + nickname + "|" + password + "|" + introduction;
        ci.sendToServer(totalMsg);
        System.out.println("[鎻愮ず]澶勭悊鏇存柊鐢ㄦ埛鐨勮祫鏂欎俊鎭凡缁忓彂閫�");
        this.localUser.setNickname(nickname);
        this.localUser.setPassword(password);
        this.localUser.setIntroduction(introduction);
    }

    public Vector<User> searchUserByName(String nameWanted){
        String searchRequest = "[Client-SearchUserByName]" + "|" + nameWanted;
        ci.sendToServer(searchRequest);
        String replyStr = ci.receiveFromServer();
        String[] replySet = replyStr.split("\\|");
        if("[Server-FindUserByName]".equals(replySet[0])){
            Vector<User> retUserList = new Vector<>();
            int num = Integer.parseInt(replySet[1]);
            for(int i = 0;i < num;i++){
                int id = Integer.parseInt(replySet[2 + 3*i]);
                String introduction = replySet[2 + 3*i + 2];
                User user = new User(id,nameWanted,"Cannot Get Others Password",introduction);
                retUserList.add(user);
            }
            return retUserList;
        }else{
            Vector<User> retUserList = new Vector<>();
            return retUserList;
        }
    }

    public Vector<User> getFriendList(int userId){
        String totalMsg = "[Client-GetFriendList]" + "|" + userId;
        ci.sendToServer(totalMsg);
        String getFriendListReply = ci.receiveFromServer();
        String[] replyList = getFriendListReply.split("\\|");
        Vector<User> friendList = new Vector<>();
        String countString = replyList[1];
        int count = Integer.parseInt(countString);
        for(int i = 0;i < count;i++){
            String idString = replyList[2 + 3*i + 0];
            int tempId = Integer.parseInt(idString);
            String nickname = replyList[2 + 3*i + 1];
            String introduction = replyList[2 + 3*i + 2];
            User tempUser = new User(tempId,nickname,introduction);
            friendList.add(tempUser);
        }
        return friendList;
    }

    public Vector<Tweet> getTweets(int userId){
        String totalMsg = "[Client-GetTweets]" + "|" + userId;
        ci.sendToServer(totalMsg);
        String getTweetsListReply = ci.receiveFromServer();
        String[] replyList = getTweetsListReply.split("\\|");
        Vector<Tweet> tweetList = new Vector<>();
        String countString = replyList[1];
        int count = Integer.parseInt(countString);
        for(int i = 0;i < count ;i++){
            String idString = replyList[2 + 4*i + 0];
            int tempId = Integer.parseInt(idString);
            String nickname = replyList[2 + 4*i + 1];
            String content = replyList[2 + 4*i + 2];
            String time = replyList[2 + 4*i + 3];
            Tweet tweet = new Tweet(tempId,nickname,content,time);
            tweetList.add(tweet);
        }
        return tweetList;
    }

    public void logout(){
        String logoutString = "[Client-Logout]" + "|" + localUser.getId();
        ci.sendToServer(logoutString);
        this.localUser = null;
        isClose = true;
        ci.breakConnection();
    }


    public static void main(String[] args){
        Client[] list = new Client[10];
        for(int i = 0;i < 10;i++){
            list[i] = new Client();
        }
        while(true){
            for(int i = 0;i < 10;i++){
                list[i].handleRegister("Name-" + i,"qwe");
            }
        }
    }

}