import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InterfaceAddress;
import java.net.Socket;
import java.io.PrintStream;
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
                }else if("[Client-Logout]".equals(infoSet[0])){
                    handleLogout(infoSet);
                    flag = false;
                }else{
                    //handleEcho(sendBuf,str);
                }
            }
            sendBuf.close();
            client.close();
        }catch(Exception e){
            System.out.println("Server外围错误");
            //e.printStackTrace();
        }
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
    }

    private void handleGetFriendList(PrintStream sendBuf,String[] infoSet){
        //将用户的所有信息拼装然后向客户端发送
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
