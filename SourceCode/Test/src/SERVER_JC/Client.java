/**
 * Created by Chao Ji on 2016-06-04.
 */
package SERVER_JC;
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
            return user;
        }else{
            System.out.println("[閿欒]" + replySet[1]);
            User failUser = new User(-1,"","");
            return failUser;
        }
    }

    public void handleUpdateProfile(int id,String nickname,String password,String introduction){
        String totalMsg = "[Client-UpdateUserInfo]" + "|" + id + "|" + nickname + "|" + password + "|" + introduction;
        ci.sendToServer(totalMsg);
        System.out.println("[鎻愮ず]澶勭悊鏇存柊鐢ㄦ埛鐨勮祫鏂欎俊鎭凡缁忓彂閫�");
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

    //杩欎釜鍑芥暟杩樻病琛ュ厖瀹屾暣
    public void uploadClientServerAddress(){
        //鑾峰彇瀹㈡埛绔骇鐢熺殑鐢ㄤ簬p2p杩炴帴鐨勮嚜宸辩殑鏈嶅姟鍣ㄧ殑鍦板潃

    }

    //杩欎釜鍑芥暟杩樻病琛ュ厖瀹屾暣
    public Vector<User> getFriendList(int userId){
        String totalMsg = "[Client-GetFriendList]" + "|" + userId;
        ci.sendToServer(totalMsg);
        String getFriendListReply = ci.receiveFromServer();
        String[] replyList = getFriendListReply.split("\\|");
        Vector<User> friendList = new Vector<>();

        //鍏蜂綋鐨勬暣鐞嗗洖澶嶆柟娉曚互鍚庡啀鍋氬疄鐜�
        //To-do

        return friendList;
    }

    public void handleLogout(){
        ci.sendToServer("[Shutdown]");
    }

    public static void main(String[] args){
        while(true) {
            Client c = new Client();
            //娴嬭瘯娉ㄥ唽鏄惁鏈夋晥
            User u = c.handleRegister("jichaofdu", "fuckingproject");
            System.out.println("鏀跺埌鐨勭敤鎴风殑id:" + u.getId());
            //娴嬭瘯鐧诲綍鏄惁鏈夋晥
            User u2 = c.handleLogin(u.getId(), "fuckingproject");
            System.out.println("鏀跺埌鐨勭敤鎴风殑鏄电О:" + u2.getNickname());
            System.out.println("鐢ㄦ埛鐨勪粙缁�:" + u.getIntroduction());
            //娴嬭瘯鏇存柊鐢ㄦ埛淇℃伅鏄惁鏈夋晥銆備慨鏀逛俊鎭悗鍐嶇櫥褰曟槸涓轰簡鑾峰緱鐢ㄦ埛鏄电О
            c.handleUpdateProfile(u.getId(), "asdadasda", "fuckingproject", "I love travel");
            User u3 = c.handleLogin(u.getId(), "fuckingproject");
            System.out.println("鏀跺埌鐨勭敤鎴风殑鏄电О:" + u3.getNickname());
            System.out.println("鐢ㄦ埛鐨勪粙缁�:" + u3.getIntroduction());
            //
            //c.ci.sendToServer("[Shutdown]");
        }
    }

}