package song;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerLauncher {
	public static Table table = new Table();
	
    public static void main(String[] args) throws Exception{
    	table.showAllInfo();
    	ServerSocket server = new ServerSocket(12345);
        boolean f = true;
        while(f){
        	Socket client = server.accept();
            System.out.println("-------------------------");
            System.out.println("From Tcp Address:" + client.getInetAddress());
            System.out.println("From Tcp Port:" + client.getPort()+"");
            new Thread(new Server(client,table)).start();
            System.out.println("total user number  -> "+table.allUser.size());
            System.out.println("online number now  -> "+table.online.size());
            System.out.println("-------------------------");
        }
        server.close();
    }
}
