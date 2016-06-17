package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientInterface {
    private static final String SERVERIP = "140.114.128.181";
    private static final int SERVERPORT = 12345;
    private Socket client;
    private PrintStream sendBuf;
    private BufferedReader receiveBuf;

    public ClientInterface() throws IOException{
        client = new Socket(SERVERIP,SERVERPORT);
        sendBuf = new PrintStream(client.getOutputStream());
        receiveBuf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void sendToServer(String message){
        sendBuf.println(message);
    }

    public String receiveFromServer(){
        try{
            String str = receiveBuf.readLine();
            return str;
        }catch(IOException e){
            e.printStackTrace();
            return "";
        }
    }

}
