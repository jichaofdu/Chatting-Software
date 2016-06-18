package NEW0;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	public static Table table = new Table();
    private static int serverport = 12345;
    private static ServerSocket serverSocket;
 
    // 用串列來儲存每個client
    // 程式進入點
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(serverport);
            System.out.println("Server is start.");
 
            // 當Server運作中時
            while (!serverSocket.isClosed()) {
                // 顯示等待客戶端連接
                System.out.println("Wait new clinet connect");
 
                // 呼叫等待接受客戶端連接
                waitNewPlayer();
            }
 
        } catch (IOException e) {
            System.out.println("Server Socket ERROR");
        }
 
    }
 
    // 等待接受客戶端連接
    public static void waitNewPlayer() {
        try {
            Socket socket = serverSocket.accept();
 
            // 呼叫創造新的使用者
            createNewPlayer(socket);
        } catch (IOException e) {
 
        }
 
    }
 
    // 創造新的使用者
    public static void createNewPlayer(final Socket socket) {
 
        // 以新的執行緒來執行
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 增加新的使用者
                    table.online.add(socket);
 
                    // 取得網路串流 
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    BufferedWriter bw;
                    bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
 
                    // 當Socket已連接時連續執行
                    while (socket.isConnected()) {
                        // 取得網路串流的訊息
                        String msg= br.readLine();
                        // 輸出訊息 
                        System.out.println(msg);
                        System.out.println("client:" + socket.getInetAddress()+ ":" +socket.getPort());
                        functionTell(socket,msg);
                        table.writeToFile();
                        //if(msg.equals("player01:"))sendInfo(socket , "yes");
                        // 廣播訊息給其它的客戶端
                        //castMsg(msg);
                    }
 
                } catch (IOException e) {
 
                }
 
                // 移除客戶端
                table.online.remove(socket);            
            }
        });
 
        // 啟動執行緒
        t.start();
    }
 
    public static void functionTell(Socket socket,String str){
    	String[] infoSet = str.split("\\|");
        
        if("[REGISTER]".equals(infoSet[0])){
            handleRegister(socket,infoSet);
        }else if("[LOGIN]".equals(infoSet[0])) {
            handleLogin(socket, infoSet);
        }
        else{
        	System.out.println("无法辨认的指令");
        }
    }
    
    private static void handleLogin(Socket socket,String[] infoSet){
    	System.out.println("login...");
    	String name = infoSet[1];
        String password = infoSet[2];
        int index ;
        String loginString = new String("");
        for(index = 0;index < table.allUser.size();index++){
        	if (name.equals(table.allUser.get(index).nickname)){
        		if (password.equals(table.allUser.get(index).password)){
        			table.allUser.get(index).isLogin = 1;
        			loginString= "[Server-LoginSuccess]|"+ table.allUser.get(index).id;
        		}
        		else {
        			loginString = "[Server-LoginFail-wrongpassword]";
        		}
        		break;	
        	}
        }
        if (index == table.allUser.size()){
        	loginString = "[Server-LoginFail-404]";
        }
        sendInfo(socket , loginString);
    }
    public static void sendInfo(Socket socket , String msg){
    	System.out.println("send:"+msg);
    	try {
            BufferedWriter bw;
            bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
            bw.write(msg+"\n");
            bw.flush();
        } catch (IOException e) {

        }
    }
    private static void handleRegister(Socket socket,String[] infoSet){
    	System.out.println("register...");
        String name = infoSet[1];
        String password = infoSet[2];
        int index ;
        String RegisterString = new String("");
        for(index = 0;index < table.allUser.size();index++){
        	if (name.equals(table.allUser.get(index).nickname)){
        		RegisterString = "[Server-registerFail]";
        		break;	
        	}
        }
        if (index == table.allUser.size()){
        	User user = new User(table.allUser.size(),name,password);
        	table.allUser.add(user);
        	RegisterString= "[Server-RegisterSuccess]";
        }
        sendInfo(socket,RegisterString);
    }
    
    // 廣播訊息給其它的客戶端
    public static void castMsg(String Msg){
        // 創造socket陣列
        Socket[] ps=new Socket[table.online.size()]; 
 
        // 將players轉換成陣列存入ps
        table.online.toArray(ps);
 
        // 走訪ps中的每一個元素
        for (Socket socket :ps ) {
            try {
                // 創造網路輸出串流
                BufferedWriter bw;
                bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
 
                // 寫入訊息到串流
                bw.write(Msg+"\n");
 
                // 立即發送
                bw.flush();
            } catch (IOException e) {
 
            }
        }
    }
}