import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerLauncher {

    public static void main(String[] args) throws Exception{

        //Server port is 1234
        ServerSocket server = new ServerSocket(1234);
        boolean f = true;
        while(f){
            Socket client = server.accept();
            System.out.println("Server:Receive a new connect.");
            System.out.println("From Tcp Address:" + client.getInetAddress());
            System.out.println("From Tcp Port:" + client.getPort());
            new Thread(new Server(client)).start();
        }
        server.close();
    }
}
