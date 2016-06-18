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
 
    // �ô��Ё탦��ÿ��client
    // ��ʽ�M���c
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(serverport);
            System.out.println("Server is start.");
 
            // ��Server�\���Еr
            while (!serverSocket.isClosed()) {
                // �@ʾ�ȴ��͑����B��
                System.out.println("Wait new clinet connect");
 
                // ���еȴ����ܿ͑����B��
                waitNewPlayer();
            }
 
        } catch (IOException e) {
            System.out.println("Server Socket ERROR");
        }
 
    }
 
    // �ȴ����ܿ͑����B��
    public static void waitNewPlayer() {
        try {
            Socket socket = serverSocket.accept();
 
            // ���Є����µ�ʹ����
            createNewPlayer(socket);
        } catch (IOException e) {
 
        }
 
    }
 
    // �����µ�ʹ����
    public static void createNewPlayer(final Socket socket) {
 
        // ���µĈ��оw�����
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // �����µ�ʹ����
                    table.online.add(socket);
 
                    // ȡ�þW·���� 
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    BufferedWriter bw;
                    bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
 
                    // ��Socket���B�ӕr�B�m����
                    while (socket.isConnected()) {
                        // ȡ�þW·������ӍϢ
                        String msg= br.readLine();
                        // ݔ��ӍϢ 
                        System.out.println(msg);
                        System.out.println("client:" + socket.getInetAddress()+ ":" +socket.getPort());
                        functionTell(socket,msg);
                        table.writeToFile();
                        //if(msg.equals("player01:"))sendInfo(socket , "yes");
                        // �V��ӍϢ�o�����Ŀ͑���
                        //castMsg(msg);
                    }
 
                } catch (IOException e) {
 
                }
 
                // �Ƴ��͑���
                table.online.remove(socket);            
            }
        });
 
        // ���ӈ��оw
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
        	System.out.println("�޷����ϵ�ָ��");
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
    
    // �V��ӍϢ�o�����Ŀ͑���
    public static void castMsg(String Msg){
        // ����socket���
        Socket[] ps=new Socket[table.online.size()]; 
 
        // ��players�D�Q����д���ps
        table.online.toArray(ps);
 
        // ���Lps�е�ÿһ��Ԫ��
        for (Socket socket :ps ) {
            try {
                // ����W·ݔ������
                BufferedWriter bw;
                bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
 
                // ����ӍϢ������
                bw.write(Msg+"\n");
 
                // �����l��
                bw.flush();
            } catch (IOException e) {
 
            }
        }
    }
}