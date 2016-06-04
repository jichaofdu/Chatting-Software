/**
 * Created by Chao Ji on 2016-06-04.
 */

import java.io.*;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketTimeoutException;

public class Client implements Runnable {
    public static final String SERVERIP = "127.0.0.1";
    public static final int SERVERPORT = 1234;

    public void run() {
        try{
            //客户端请求与本机在20006端口建立TCP连接
            Socket client = new Socket(SERVERIP,SERVERPORT);
            client.setSoTimeout(10000);
            //获取键盘输入
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            //获取Socket的输出流，用来发送数据到服务端
            PrintStream out = new PrintStream(client.getOutputStream());
            //获取Socket的输入流，用来接收从服务端发送过来的数据
            BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
            boolean flag = true;
            System.out.println(client.getLocalPort());//获得client的服务器端的端口号
            System.out.println(client.getPort());//获取到client的client那边的端口号
            while(flag){
                System.out.print("输入信息：");
                String str = input.readLine();
                //发送数据到服务端
                out.println(str);
                if("bye".equals(str)){
                    flag = false;
                }else{
                    try{
                        //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
                        String echo = buf.readLine();
                        System.out.println(echo);
                    }catch(SocketTimeoutException e){
                        System.out.println("Client: Time out. No response received.");
                    }
                }
            }
            input.close();
            if(client != null){
                //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
                client.close(); //只关闭socket，其关联的输入输出流也会被关闭
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws IOException{
        new Thread(new Client()).start();
    }

}