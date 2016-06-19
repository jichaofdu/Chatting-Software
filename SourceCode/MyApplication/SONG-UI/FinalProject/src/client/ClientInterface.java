package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientInterface {
    private static final String SERVER_IP = "172.20.124.162";
    private static final int SERVER_PORT = 12345;
    private Socket client;
    private PrintStream sendBuf;
    private BufferedReader receiveBuf;

    public ClientInterface() throws IOException{
        client = new Socket(SERVER_IP,SERVER_PORT);
        sendBuf = new PrintStream(client.getOutputStream());
        receiveBuf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void breakConnection(){
        try{
            sendBuf.close();
        }catch(Exception e){

        }
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
