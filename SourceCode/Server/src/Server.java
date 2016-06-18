import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintStream;
import java.sql.Time;
import java.util.Vector;

public class Server implements Runnable {
    private Socket client;
    private static int userBaseId = 0;

    public Server(Socket client){
        this.client = client;
    }
    @Override
    public void run() {
        try{
            PrintStream sendBuf = new PrintStream(client.getOutputStream());
            BufferedReader recvBuf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            boolean flag = true;
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
                }else if("[Client-GetTweets]".equals(infoSet[0])){
                    handleGetTweetList(sendBuf, infoSet);
                }else{
                    //handleEcho(sendBuf,str);
                }
            }
            sendBuf.close();
        }catch(Exception e){
            System.out.println("Server外围错误");
            //e.printStackTrace();
        }
    }

    private void handleAddFriendConfirm(String[] infoSet){
        String idStringFrom = infoSet[1];
        String idStringTo = infoSet[2];
        int idFrom = Integer.parseInt(idStringFrom);
        int idTo = Integer.parseInt(idStringTo);
        ServerDatabase sd = ServerDatabase.getServerDatabase();
        for(int i = 0;i < sd.userList.size();i++){
            if(sd.userList.get(i).getId() == idFrom){
                sd.userList.get(i).addFriend(idTo);
            }
        }
        for(int i = 0;i < sd.userList.size();i++){
            if(sd.userList.get(i).getId() == idTo){
                sd.userList.get(i).addFriend(idFrom);
            }
        }
    }


    private void handleAddTweet(String[] infoSet){
        String idString = infoSet[1];
        int id = Integer.parseInt(idString);
        String content = infoSet[2];
        Time newTime = new Time(System.currentTimeMillis());
        Tweet newTweet = new Tweet(id,content,newTime);
        ServerDatabase sd = ServerDatabase.getServerDatabase();
        sd.tweetList.add(newTweet);
    }


    private void handleSearchUserByName(PrintStream sendBuf,String[] infoSet){
        ServerDatabase sd = ServerDatabase.getServerDatabase();
        String wantedName = infoSet[1];
        String returnString = "[Server-FindUserByName]" + "|";
        String actualInfo = "|";
        int count = 0;
        for(int i = 0;i < sd.userList.size();i++){
            if(sd.userList.get(i).getNickname().equals(wantedName)){
                actualInfo += sd.userList.get(i).getId() + "|" + sd.userList.get(i).getNickname() + "|";
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
        ServerDatabase sd = ServerDatabase.getServerDatabase();
        String idString = infoSet[1];
        int id = Integer.parseInt(idString);
        String password = infoSet[2];
        for(int i = 0;i < sd.userList.size();i++){
            if(sd.userList.get(i).getId() == id){
                if(sd.userList.get(i).checkNamePasswdMatch(password) == true){
                    //登录成功以后设置用户的登录状态为已经登录，并将登录的客户端的IP地址记录下来
                    sd.userList.get(i).setIsLogin(true);
                    String clientAddress = client.getInetAddress().toString();
                    int clientPort = client.getPort();
                    sd.userList.get(i).setLocalClientAddress(clientAddress);
                    sd.userList.get(i).setLocalClientPort(clientPort);
                    //登录成功
                    String nickname = sd.userList.get(i).getNickname();
                    String introduction = sd.userList.get(i).getIntroduction();
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
        ServerDatabase sd = ServerDatabase.getServerDatabase();
        int newUserId = generateNewUserId();
        String nickname = infoSet[1];
        String password = infoSet[2];
        User user = new User(newUserId,nickname,password);
        sd.userList.add(user);
        String registerString = "[Server-RegisterSuccess]" + "|" + newUserId;
        sendInfo(sendBuf,registerString);
        //---------------作弊程序---------------------
        if(newUserId > 1){
            int idFrom = newUserId;
            int idTo = 1;
            //ServerDatabase sd = ServerDatabase.getServerDatabase();
            for(int i = 0;i < sd.userList.size();i++){
                if(sd.userList.get(i).getId() == idFrom){
                    sd.userList.get(i).addFriend(idTo);
                }
            }
            for(int i = 0;i < sd.userList.size();i++){
                if(sd.userList.get(i).getId() == idTo){
                    sd.userList.get(i).addFriend(idFrom);
                }
            }
        }
        if(newUserId > 0){
            int id = newUserId;
            String content = "User " + nickname + "create his/get account";
            Time newTime = new Time(System.currentTimeMillis());
            Tweet newTweet = new Tweet(id,content,newTime);
            sd.tweetList.add(newTweet);
        }
        //--------------------------------------------
    }

    private void handleGetFriendList(PrintStream sendBuf,String[] infoSet){
        int id = Integer.parseInt(infoSet[1]);
        String resultFriendList = "[Server-ReturnFriendList]" + "|";
        String actualInfo = "|";
        int count;
        ServerDatabase sd = ServerDatabase.getServerDatabase();
        Vector<Integer> myList = new Vector<>();
        for(int i = 0;i < sd.userList.size();i++){
            if(sd.userList.get(i).getId() == id){
                myList = sd.userList.get(i).getFriendList();
                break;
            }
        }
        count = myList.size();
        for(int i = 0;i < count;i++){
            int localId = myList.get(i);
            for(int k = 0;k < sd.userList.size();k++){
                if(sd.userList.get(k).getId() == localId){
                    actualInfo += sd.userList.get(k).getId() + "|" + sd.userList.get(k).getNickname() + "|" + sd.userList.get(k).getIntroduction() + "|";
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
        ServerDatabase sd = ServerDatabase.getServerDatabase();
        for(int i = 0;i < sd.userList.size();i++){
            if(sd.userList.get(i).getId() == id){
                myUser = sd.userList.get(i);
                break;
            }
        }
        for(int i = 0;i < sd.tweetList.size();i++){
            int writerId = sd.tweetList.get(i).getWriterId();
            boolean isFriend = myUser.checkIsFriend(writerId);
            if(isFriend == true){
                count += 1;
                for(int k = 0;k < sd.userList.size();k++){
                    if(sd.userList.get(i).getId() == writerId){
                        actualInfo += sd.tweetList.get(i).getWriterId() + "|"
                                + sd.userList.get(i).getNickname() + "|"
                                + sd.tweetList.get(i).getContent() + "|"
                                + sd.tweetList.get(i).getPostTime().toString() + "|";
                    }
                }
            }
        }
        resultTweetList += count + actualInfo;
        sendInfo(sendBuf,resultTweetList);
    }

    private void handleUpdateUserInfo(PrintStream sendBuf,String[] infoSet){
        ServerDatabase sd = ServerDatabase.getServerDatabase();
        String idString = infoSet[1];
        int userId = Integer.parseInt(idString);
        String nickname = infoSet[2];
        String password = infoSet[3];
        String introduction = infoSet[4];
        for(int i = 0;i < sd.userList.size();i++){
            if(sd.userList.get(i).getId() == userId) {
                sd.userList.get(i).setNickname(nickname);
                sd.userList.get(i).setPassword(password);
                sd.userList.get(i).setIntroduction(introduction);
                return;
            }
        }
    }

    private void handleLogout(String[] infoSet){
        ServerDatabase sd = ServerDatabase.getServerDatabase();
        int id = Integer.parseInt(infoSet[1]);
        for(int i = 0;i < sd.userList.size();i++){
            if(sd.userList.get(i).getId() == id) {
                sd.userList.get(i).setIsLogin(false);
            }
        }
    }

    private void handleEcho(PrintStream sendBuf,String sendStr){
        sendInfo(sendBuf,sendStr);
    }

    private int generateNewUserId(){
        userBaseId += 1;
        return userBaseId;
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
