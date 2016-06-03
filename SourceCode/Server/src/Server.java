/**
 * Created by Chao Ji on 2016-06-03.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 测试Android客户端与PC服务器通过socket进行交互
 * 服务器端：接收客户端的信息并回送给客户
 * @author Ameyume
 *
 */
public class Server implements Runnable {
    public static final String SERVERIP = "127.0.0.1";
    public static final int SERVERPORT = 51706;

    public void run() {
        try {
            System.out.println("Server: Connecting...");
            ServerSocket serverSocket = new ServerSocket(SERVERPORT);
            while (true) {
                // 等待接受客户端请求
                Socket client = serverSocket.accept();
                System.out.println("Server: Receiving...");
                try {
                    // 接受客户端信息
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(client.getInputStream()));
                    // 发送给客户端的消息
                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(client.getOutputStream())),true);
                    System.out.println("Server: Ready for read from server");
                    String str = in.readLine(); // 读取客户端的信息
                    System.out.println("Server: Complete for read from server");
                    if (str != null ) {
                        // 设置返回信息，把从客户端接收的信息再返回给客户端
                        out.println("You sent to server message is:" + str);
                        out.flush();
                        // 把客户端发送的信息保存到文件中
                        File file = new File ("F://android.txt");
                        FileOutputStream fops = new FileOutputStream(file);
                        byte [] b = str.getBytes();
                        for ( int i = 0 ; i < b.length; i++ ) {
                            fops.write(b[i]);
                        }
                        System.out.println("S: Received: '" + str + "'");
                    } else {
                        System.out.println("Not receiver anything from client!");
                    }
                } catch (Exception e) {
                    System.out.println("Server: Error 1");
                    e.printStackTrace();
                } finally {
                    client.close();
                    System.out.println("Server: 在循环接受信息的过程中发生了错误");
                }
            }
        } catch (Exception e) {
            System.out.println("Server Error: 包含liste的整个区块发生了错误");
            e.printStackTrace();
        }
    }

    public static void main(String [] args ) {
        Thread desktopServerThread = new Thread(new Server());
        desktopServerThread.start();
    }
}
