package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientInterface {
    private static final String SERVERIP = "172.20.124.162";
    private static final int SERVERPORT = 12345;
    private Socket client;
    private PrintStream sendBuf;
    private BufferedReader receiveBuf;

    public ClientInterface() throws IOException{
        System.out.println("Begin Client Interface");
        client = new Socket(SERVERIP,SERVERPORT);
        System.out.println("End Client Interface - 1");
        sendBuf = new PrintStream(client.getOutputStream());
        System.out.println("End Client Interface - 2");
        receiveBuf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println("End Client Interface");
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
