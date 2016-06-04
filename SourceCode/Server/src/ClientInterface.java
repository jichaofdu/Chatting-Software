import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientInterface {
    public static final String SERVERIP = "127.0.0.1";
    public static final int SERVERPORT = 1234;
    public Socket client;
    PrintStream sendBuf;
    BufferedReader receiveBuf;

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
