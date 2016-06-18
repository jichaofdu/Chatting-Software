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

    private Client(){
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

    public void addFriend(int id){
    	String addMsg = "[Client-AddFriendConfirm]" + "|" + Client.getClient().getLocalUser().getId() + "|" + id;
        ci.sendToServer(addMsg);
        System.out.println("Send Message");
    }
    
    public void addNewTweet(String content){
        String addTweetMsg = "[Client-AddTweet]" + "|" + localUser.getId() + "|" + content;
        ci.sendToServer(addTweetMsg);
        System.out.println("Add Tweet Complete");
    }

    public User getLocalUser(){
        return this.localUser;
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

    public void handleUpdateProfile(int id,String nickname,String password,String introduction){
        String totalMsg = "[Client-UpdateUserInfo]" + "|" + id + "|" + nickname + "|" + password + "|" + introduction;
        ci.sendToServer(totalMsg);
        System.out.println("[鎻愮ず]澶勭悊鏇存柊鐢ㄦ埛鐨勮祫鏂欎俊鎭凡缁忓彂閫�");
        this.localUser.setNickname(nickname);
        this.localUser.setPassword(password);
        this.localUser.setIntroduction(introduction);
    }

    /*
    杩斿洖淇℃伅鏍煎紡:   [鎶ュご] | [鐢ㄦ埛鍚峕 | [鐢ㄦ埛浠嬬粛]
    鎶ュご涓�:  [Server-FindUserById] 鐨勬椂鍊欒〃绀烘湁鏌ユ壘鍒伴偅涓�涓敤鎴�
             [Server-FindNoneByName] 鐨勬椂鍊欒〃绀烘湁鏌ユ壘澶辫触
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
    杩斿洖淇℃伅鏍煎紡:   [鎶ュご] | [缁撴灉鏁伴噺] | [鐢ㄦ埛id] | [鐢ㄦ埛浠嬬粛] | [鐢ㄦ埛id] | [鐢ㄦ埛浠嬬粛]
    鎶ュご涓�:   [Server-FindUserByName] 鐨勬椂鍊欒〃绀烘湁鏌ユ壘鍒伴偅涓�涓敤鎴�
              [Server-FindNoneByName] 鐨勬椂鍊欒〃绀烘湁鏌ユ壘澶辫触
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

    //杩欎釜鍑芥暟杩樻病琛ュ厖瀹屾暣
    public void uploadClientServerAddress(){
        //鑾峰彇瀹㈡埛绔骇鐢熺殑鐢ㄤ簬p2p杩炴帴鐨勮嚜宸辩殑鏈嶅姟鍣ㄧ殑鍦板潃
    }

    /*
     * 鍥炴姤鏍煎紡锛� 鎶ュご | 鏁伴噺 | id | nickname | introduction
     *
     */
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

    /*
     * 鍥炴姤鏍煎紡锛� 鎶ュご | 鏁伴噺 | 浣滆�卛d | 浣滆�呮樀绉� | tweet鍐呭 | 鏃堕棿
     *
     */
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


}