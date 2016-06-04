import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintStream;
import java.util.Vector;

public class Server implements Runnable {
    private Socket client;
    private static int userBaseId = 0;

    private Vector<User> userList; //The friend list of the user.

    public Server(Socket client){
        this.client = client;
        this.userList = new Vector<>();
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
                System.out.println("---" + infoSet[0]);
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
                }else{
                    handleEcho(sendBuf,str);
                }
            }
            sendBuf.close();
            client.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void handleLogin(PrintStream sendBuf,String[] infoSet){
        String idString = infoSet[1];
        int id = Integer.parseInt(idString);
        String password = infoSet[2];
        for(int i = 0;i < userList.size();i++){
            if(userList.get(i).getId() == id){
                if(userList.get(i).checkNamePasswdMatch(password) == true){
                    //登录成功
                    String nickname = userList.get(i).getNickname();
                    String loginString = "[Server-LoginSuccess]" + "|" + nickname;
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
        int newUserId = generateNewUserId();
        String nickname = infoSet[1];
        String password = infoSet[2];
        User user = new User(newUserId,nickname,password);
        userList.add(user);
        String registerString = "[Server-RegisterSuccess]" + "|" + newUserId;
        sendInfo(sendBuf,registerString);
    }

    private void handleGetFriendList(PrintStream sendBuf,String[] infoSet){
        //将用户的所有信息拼装然后向客户端发送
    }

    private void handleUpdateUserInfo(PrintStream sendBuf,String[] infoSet){

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

    private int generateNewUserId(){
        userBaseId += 1;
        return userBaseId;
    }
}
