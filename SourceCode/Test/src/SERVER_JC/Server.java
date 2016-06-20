package SERVER_JC;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintStream;
import java.sql.Time;
import java.util.Vector;

public class Server implements Runnable {
    private Socket client;

    public Server(Socket client){
        this.client = client;
    }
    @Override
    public void run() {
        try{
            PrintStream sendBuf = new PrintStream(client.getOutputStream());
            BufferedReader recvBuf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            boolean flag = true;
            //Add default user
            addDefaultUser();
            //
            while(flag){
                String str = receiveInfo(recvBuf);
                String[] infoSet = str.split("\\|");
                if("[Shutdown]".equals(infoSet[0])) {
                    flag = false;
                }else if("[Client-Register]".equals(infoSet[0])){
                    handleRegister(sendBuf,infoSet);
                }else if("[Client-Login]".equals(infoSet[0])) {
                    handleLogin(sendBuf, infoSet);
                }else if("[Client-GetFriendList]".equals(infoSet[0])) {
                    handleGetFriendList(sendBuf, infoSet);
                }else if("[Client-UpdateUserInfo]".equals(infoSet[0])) {
                    handleUpdateUserInfo(sendBuf, infoSet);
                }else if("[Client-Logout]".equals(infoSet[0])) {
                    handleLogout(infoSet);
                    flag = false;
                }else if("[Client-SearchUserByName]".equals(infoSet[0])) {
                    handleSearchUserByName(sendBuf, infoSet);
                }else if("[Client-AddTweet]".equals(infoSet[0])) {
                    handleAddTweet(infoSet);
                }else if("[Client-AddFriendConfirm]".equals(infoSet[0])) {
                    handleAddFriendConfirm(infoSet);
                }else if("[Client-GetTweets]".equals(infoSet[0])) {
                    handleGetTweetList(sendBuf, infoSet);
                }else if("[Client-ChatMessage]".equals(infoSet[0])) {
                    handleAddChatMessage(sendBuf, infoSet);
                }else if("[Client-GetChatMessage]".equals(infoSet[0])) {
                    handleGetMessages(sendBuf, infoSet);
                }else if("[Client-DeleteFriend]".equals(infoSet[0])){
                    handleDeleteFriend(infoSet);
                }else{
                    //handleEcho(sendBuf,str);
                }
            }
            sendBuf.close();
        }catch(Exception e){
        	e.printStackTrace();
        }
    }

    private void addDefaultUser(){
        for(int i = 0; i < 3;i++){
            int newUserId = ServerDatabase.getServerDatabase().generateNewUserId();
            User user = new User(newUserId,"Test User.No." + newUserId,"123456");
            ServerDatabase.getServerDatabase().userList.add(user);
            if(newUserId > 1){
                int idFrom = newUserId;
                int idTo = 1;
                for(int j = 0;j < ServerDatabase.getServerDatabase().userList.size();j++){
                    if(ServerDatabase.getServerDatabase().userList.get(j).getId() == idFrom){
                        ServerDatabase.getServerDatabase().userList.get(j).addFriend(idTo);
                        break;
                    }
                }
                for(int j = 0;j < ServerDatabase.getServerDatabase().userList.size();j++){
                    if(ServerDatabase.getServerDatabase().userList.get(j).getId() == idTo){
                        ServerDatabase.getServerDatabase().userList.get(j).addFriend(idFrom);
                        break;
                    }
                }
            }
            if(newUserId > 0){
                int id = newUserId;
                String content = "User " + "Test User.No." + newUserId + "create his/get account";
                Time newTime = new Time(System.currentTimeMillis());
                Tweet newTweet = new Tweet(id,content,newTime);
                ServerDatabase.getServerDatabase().tweetList.add(newTweet);
            }
            ServerDatabase.getServerDatabase().chatGroup.addMember(newUserId);
            String time = new Time(System.currentTimeMillis()).toString();
            ChatMessage msg = new ChatMessage(newUserId,"Test User.No." + newUserId,"Hello World",time);
            ServerDatabase.getServerDatabase().chatGroup.addMessage(msg);
        }
    }

    private void handleDeleteFriend(String[] infoSet){
        String fromIdString = infoSet[1];
        String toIdString = infoSet[2];
        int idFrom = Integer.parseInt(fromIdString);
        int idTo = Integer.parseInt(toIdString);
        //Clear the friend if they are already friend
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getId() == idFrom){
                ServerDatabase.getServerDatabase().userList.get(i).deleteFriend(idTo);
                break;
            }
        }
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getId() == idTo){
                ServerDatabase.getServerDatabase().userList.get(i).deleteFriend(idFrom);
                break;
            }
        }
    }

    private void handleGetMessages(PrintStream sendBuf,String[] infoSet){
        String idString = infoSet[1];
        int id = Integer.parseInt(idString);
        String sendMsgBack = "[Server-SendMsgBack]" + "|";
        int count = ServerDatabase.getServerDatabase().chatGroup.messageList.size();
        sendMsgBack += count + "|";
        for(int i = 0;i < count;i++){
            sendMsgBack += ServerDatabase.getServerDatabase().chatGroup.messageList.get(i).getUserId() + "|"
                    + ServerDatabase.getServerDatabase().chatGroup.messageList.get(i).getUsername() + "|"
                    + ServerDatabase.getServerDatabase().chatGroup.messageList.get(i).getContent() + "|"
                    + ServerDatabase.getServerDatabase().chatGroup.messageList.get(i).getTime() + "|";
        }
        sendInfo(sendBuf,sendMsgBack);
    }

    private void handleAddChatMessage(PrintStream sendBuf,String[] infoSet){
        String idString = infoSet[1];
        int id = Integer.parseInt(idString);
        String nickname = infoSet[2];
        String content = infoSet[3];
        String time = new Time(System.currentTimeMillis()).toString();
        ChatMessage msg = new ChatMessage(id,nickname,content,time);
        ServerDatabase.getServerDatabase().chatGroup.addMessage(msg);
        //Send All Message Back
        String sendMsgBack = "[Server-SendMsgBack]" + "|";
        int count = ServerDatabase.getServerDatabase().chatGroup.messageList.size();
        sendMsgBack += count + "|";
        for(int i = 0;i < count;i++){
            sendMsgBack += ServerDatabase.getServerDatabase().chatGroup.messageList.get(i).getUserId() + "|"
                    + ServerDatabase.getServerDatabase().chatGroup.messageList.get(i).getUsername() + "|"
                    + ServerDatabase.getServerDatabase().chatGroup.messageList.get(i).getContent() + "|"
                    + ServerDatabase.getServerDatabase().chatGroup.messageList.get(i).getTime() + "|";
        }
        sendInfo(sendBuf,sendMsgBack);
    }

    private void handleAddFriendConfirm(String[] infoSet){
        String idStringFrom = infoSet[1];
        String idStringTo = infoSet[2];
        int idFrom = Integer.parseInt(idStringFrom);
        int idTo = Integer.parseInt(idStringTo);
        //Clear the friend if they are already friend
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getId() == idFrom){
                ServerDatabase.getServerDatabase().userList.get(i).deleteFriend(idTo);
                break;
            }
        }
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getId() == idTo){
                ServerDatabase.getServerDatabase().userList.get(i).deleteFriend(idFrom);
                break;
            }
        }
        //After clear, add friendship to them
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getId() == idFrom){
                ServerDatabase.getServerDatabase().userList.get(i).addFriend(idTo);
                break;
            }
        }
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getId() == idTo){
                ServerDatabase.getServerDatabase().userList.get(i).addFriend(idFrom);
                break;
            }
        }
    }

    private void handleAddTweet(String[] infoSet){
        String idString = infoSet[1];
        int id = Integer.parseInt(idString);
        String content = infoSet[2];
        Time newTime = new Time(System.currentTimeMillis());
        Tweet newTweet = new Tweet(id,content,newTime);
        ServerDatabase.getServerDatabase().tweetList.add(newTweet);
    }

    private void handleSearchUserByName(PrintStream sendBuf,String[] infoSet){
        String wantedName = infoSet[1];
        String returnString = "[Server-FindUserByName]" + "|";
        String actualInfo = "|";
        int count = 0;
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getNickname().equals(wantedName)){
                actualInfo += ServerDatabase.getServerDatabase().userList.get(i).getId() + "|"
                            + ServerDatabase.getServerDatabase().userList.get(i).getNickname() + "|"
                            + ServerDatabase.getServerDatabase().userList.get(i).getIntroduction() + "|";
                count += 1;
            }
        }
        if(count == 0){
            returnString = "[Server-FindUserNoneByName]";
        }else{
            returnString = returnString + count + actualInfo;
        }
        sendInfo(sendBuf,returnString);
    }

    private void handleLogin(PrintStream sendBuf,String[] infoSet){
        String idString = infoSet[1];
        int id = Integer.parseInt(idString);
        String password = infoSet[2];
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getId() == id){
                if(ServerDatabase.getServerDatabase().userList.get(i).checkNamePasswdMatch(password) == true){
                    //登录成功以后设置用户的登录状态为已经登录，并将登录的客户端的IP地址记录下来
                    ServerDatabase.getServerDatabase().userList.get(i).setIsLogin(true);
                    String clientAddress = client.getInetAddress().toString();
                    int clientPort = client.getPort();
                    ServerDatabase.getServerDatabase().userList.get(i).setLocalClientAddress(clientAddress);
                    ServerDatabase.getServerDatabase().userList.get(i).setLocalClientPort(clientPort);
                    //登录成功
                    String nickname = ServerDatabase.getServerDatabase().userList.get(i).getNickname();
                    String introduction = ServerDatabase.getServerDatabase().userList.get(i).getIntroduction();
                    String loginString = "[Server-LoginSuccess]" + "|" + nickname + "|" + introduction;
                    sendInfo(sendBuf,loginString);
                    return;
                }else{
                    String loginString = "[Server-LoginFail]" + "|" + "Wrong Password.";
                    sendInfo(sendBuf,loginString);
                    return;
                }
            }
        }
        String loginString = "[Server-LoginFail]" + "|" + "No such person.";
        sendInfo(sendBuf,loginString);
    }

    private void handleRegister(PrintStream sendBuf,String[] infoSet){
        int newUserId = ServerDatabase.getServerDatabase().generateNewUserId();
        String nickname = infoSet[1];
        String password = infoSet[2];
        User user = new User(newUserId,nickname,password);
        ServerDatabase.getServerDatabase().userList.add(user);
        String registerString = "[Server-RegisterSuccess]" + "|" + newUserId;
        sendInfo(sendBuf,registerString);
        //---------------作弊程序---------------------
        if(newUserId > 1){
            int idFrom = newUserId;
            int idTo = 1;
            for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
                if(ServerDatabase.getServerDatabase().userList.get(i).getId() == idFrom){
                    ServerDatabase.getServerDatabase().userList.get(i).addFriend(idTo);
                    break;
                }
            }
            for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
                if(ServerDatabase.getServerDatabase().userList.get(i).getId() == idTo){
                    ServerDatabase.getServerDatabase().userList.get(i).addFriend(idFrom);
                    break;
                }
            }
        }

        if(newUserId > 0){
            int id = newUserId;
            String content = "User " + nickname + "create his/get account";
            Time newTime = new Time(System.currentTimeMillis());
            Tweet newTweet = new Tweet(id,content,newTime);
            ServerDatabase.getServerDatabase().tweetList.add(newTweet);
        }

        ServerDatabase.getServerDatabase().chatGroup.addMember(newUserId);
        String time = new Time(System.currentTimeMillis()).toString();
        ChatMessage msg = new ChatMessage(newUserId,nickname,"Hello World",time);
        ServerDatabase.getServerDatabase().chatGroup.addMessage(msg);
        //--------------------------------------------
    }

    private void handleGetFriendList(PrintStream sendBuf,String[] infoSet){
        int id = Integer.parseInt(infoSet[1]);
        String resultFriendList = "[Server-ReturnFriendList]" + "|";
        String actualInfo = "|";
        int count;
        Vector<Integer> myList = new Vector<>();
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getId() == id){
                myList = ServerDatabase.getServerDatabase().userList.get(i).getFriendList();
                break;
            }
        }
        count = myList.size();
        for(int i = 0;i < count;i++){
            int localId = myList.get(i);
            for(int k = 0;k < ServerDatabase.getServerDatabase().userList.size();k++){
                if(ServerDatabase.getServerDatabase().userList.get(k).getId() == localId){
                    actualInfo += ServerDatabase.getServerDatabase().userList.get(k).getId() + "|"
                                + ServerDatabase.getServerDatabase().userList.get(k).getNickname() + "|"
                                + ServerDatabase.getServerDatabase().userList.get(k).getIntroduction() + "|";
                }
            }
        }
        resultFriendList += count + actualInfo;
        sendInfo(sendBuf,resultFriendList);
    }

    private void handleGetTweetList(PrintStream sendBuf,String[] infoSet){
        int id = Integer.parseInt(infoSet[1]);
        String resultTweetList = "[Server-ReturnTweetList]" + "|";
        String actualInfo = "|";
        int count = 0;
        User myUser = new User(-1,"","");
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getId() == id){
                myUser = ServerDatabase.getServerDatabase().userList.get(i);
                break;
            }
        }
        for(int i = ServerDatabase.getServerDatabase().tweetList.size() - 1;i >= 0;i--){
            int writerId = ServerDatabase.getServerDatabase().tweetList.get(i).getWriterId();
            boolean isFriend = myUser.checkIsFriend(writerId) || (writerId == id);
            if(isFriend){
                count += 1;
                for(int k = 0;k < ServerDatabase.getServerDatabase().userList.size();k++){
                    if(ServerDatabase.getServerDatabase().userList.get(k).getId() == writerId){
                        actualInfo += ServerDatabase.getServerDatabase().tweetList.get(i).getWriterId() + "|"
                                   + ServerDatabase.getServerDatabase().userList.get(k).getNickname() + "|"
                                   + ServerDatabase.getServerDatabase().tweetList.get(i).getContent() + "|"
                                   + ServerDatabase.getServerDatabase().tweetList.get(i).getPostTime().toString() + "|";
                    }
                }
            }
        }
        resultTweetList += count + actualInfo;
        sendInfo(sendBuf,resultTweetList);
    }

    private void handleUpdateUserInfo(PrintStream sendBuf,String[] infoSet){
        String idString = infoSet[1];
        int userId = Integer.parseInt(idString);
        String nickname = infoSet[2];
        String password = infoSet[3];
        String introduction = infoSet[4];
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getId() == userId) {
                ServerDatabase.getServerDatabase().userList.get(i).setNickname(nickname);
                ServerDatabase.getServerDatabase().userList.get(i).setPassword(password);
                ServerDatabase.getServerDatabase().userList.get(i).setIntroduction(introduction);
                break;
            }
        }
    }

    private void handleLogout(String[] infoSet){
        int id = Integer.parseInt(infoSet[1]);
        for(int i = 0;i < ServerDatabase.getServerDatabase().userList.size();i++){
            if(ServerDatabase.getServerDatabase().userList.get(i).getId() == id) {
                ServerDatabase.getServerDatabase().userList.get(i).setIsLogin(false);
                break;
            }
        }
    }

    private void handleEcho(PrintStream sendBuf,String sendStr){
        sendInfo(sendBuf,sendStr);
    }

    private String receiveInfo(BufferedReader recvBuf){
        try{
            String str = recvBuf.readLine();
            System.out.println("[Receive]" + str);
            return str;
        }catch(IOException e){
            e.printStackTrace();
            return "";
        }
    }

    private void sendInfo(PrintStream sendBuf,String sendStr){
        sendBuf.println(sendStr);
        System.out.println("[Send]" + sendStr);
    }

}
