package SERVER_JC;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerLauncher {

    public static void main(String[] args) throws Exception{

        ServerSocket server = new ServerSocket(12345);
        boolean f = true;
        System.out.println("Server Open Success");
        while(f == true){
            Socket client = server.accept();
            System.out.println("Server:Receive a new connect.");
            System.out.println("From Tcp Address:" + client.getInetAddress());
            System.out.println("From Tcp Port:" + client.getPort());
            new Thread(new Server(client)).start();
        }
        server.close();
    }
}
