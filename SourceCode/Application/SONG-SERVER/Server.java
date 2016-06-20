package song;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintStream;

public class Server implements Runnable {
    private Socket client;
    private Table table_temp;
    boolean flag = true;
    public Server(Socket client,Table table){
        this.client = client;
        table_temp = table; 
    }
    @Override
    public void run() {
        try{
            PrintStream sendBuf = new PrintStream(client.getOutputStream());
            BufferedReader recvBuf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            while(flag){
            	String str = receiveInfo(recvBuf);
            	if (str.length() <= 0){
                	sendBuf.close();
               	 	recvBuf.close();
               	 	client.close();
                    System.out.println("client:" + client.getInetAddress()+ ":" +client.getPort()+ " EXIT!");
                    break;
                }
                functionTell(sendBuf,str);
                table_temp.writeToFile();
            }
            
        }catch(Exception e){
        	flag = false;
        	System.out.println("client:" + client.getInetAddress()+ ":" +client.getPort()+ " EXIT!");
        }
    }
        
    public void functionTell(PrintStream sendBuf,String str){
    	String[] infoSet = str.split("\\|");
        System.out.println("---" + str);
        
        if("[Client-Register]".equals(infoSet[0])){
            handleRegister(sendBuf,infoSet);
        }else if("[Client-Login]".equals(infoSet[0])) {
            handleLogin(sendBuf, infoSet);
        }
        else{
        	System.out.println("无法辨认的指令");
        }
    }
    
    private void handleLogin(PrintStream sendBuf,String[] infoSet){
    	System.out.println("login...");
        String name = infoSet[1];
        String password = infoSet[2];
        int index ;
        String loginString = new String("");
        for(index = 0;index < table_temp.allUser.size();index++){
        	if (name.equals(table_temp.allUser.get(index).nickname)){
        		if (password.equals(table_temp.allUser.get(index).password)){
        			table_temp.allUser.get(index).isLogin = 1;
        			loginString= "[Server-LoginSuccess]|"+ table_temp.allUser.get(index).id;
        		}
        		else {
        			loginString = "[Server-LoginFail-wrongpassword]";
        		}
        		break;	
        	}
        }
        if (index == table_temp.allUser.size()){
        	loginString = "[Server-LoginFail-404]";
        }
        sendInfo(sendBuf,loginString);
    }

    private void handleRegister(PrintStream sendBuf,String[] infoSet){
    	System.out.println("register...");
        String name = infoSet[1];
        String password = infoSet[2];
        int index ;
        String RegisterString = new String("");
        for(index = 0;index < table_temp.allUser.size();index++){
        	if (name.equals(table_temp.allUser.get(index).nickname)){
        		RegisterString = "[Server-registerFail]";
        		break;	
        	}
        }
        if (index == table_temp.allUser.size()){
        	User user = new User(table_temp.allUser.size(),name,password);
        	table_temp.allUser.add(user);
        	RegisterString= "[Server-RegisterSuccess]";
        }
        sendInfo(sendBuf,RegisterString);
    }
    
    
    private String receiveInfo(BufferedReader recvBuf){
        try{
            String str = recvBuf.readLine();
            System.out.println("[Receive]" + str);
            return str;
        }catch(IOException e){
        	flag = false;
        	System.out.println("client:" + client.getInetAddress()+ ":" +client.getPort()+ " EXIT!");
            e.printStackTrace();
            return "";
        }
    }

    private void sendInfo(PrintStream sendBuf,String sendStr){
        sendBuf.println(sendStr);
        System.out.println("[Send]" + sendStr);
    }
}
