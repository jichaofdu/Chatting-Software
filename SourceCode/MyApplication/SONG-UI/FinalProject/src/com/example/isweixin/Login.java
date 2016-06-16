package com.example.isweixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private EditText name;
	private EditText password;
	private Button login;
	private Button register;
	public static final String SERVERIP = "172.20.124.162";
    public static final int SERVERPORT = 12345;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		name = (EditText)findViewById(R.id.login_name);
		password = (EditText)findViewById(R.id.login_password);
		login = (Button)findViewById(R.id.Login);
		register = (Button)findViewById(R.id.login_register);
		login.setOnClickListener(new View.OnClickListener() {
			String name1 ;
			String password1 ;
			
    		@Override
    		public void onClick(View arg0) {
    			name1 = name.getText().toString();
    			password1 = password.getText().toString();
    			
    			if (name1.equals("") ||password1.equals("")){
    				Toast.makeText(getApplicationContext(), "信息不完整！",
    					     Toast.LENGTH_SHORT).show();
    				return;
    			}
    			Thread t = new Thread(new Runnable(){  
    				public void run(){
    					try {
    						Socket client = new Socket(SERVERIP,SERVERPORT);
    						PrintStream sendBuf = new PrintStream(client.getOutputStream());
    			            BufferedReader recvBuf = new BufferedReader(new InputStreamReader(client.getInputStream()));
    						String massage = new String ("[Client-Login]|" +name1 + "|" + password1);
    						
    						sendToServer(sendBuf,massage) ;
    			            String reply = "0";
    			            receiveFromServer(recvBuf,reply);
    			            
    			            client.close();
    			            
    			            if (reply.equals("0")){
        						Intent intent = new Intent(Login.this,MainActivity.class);
        		    			startActivity(intent);
        		    			finish();
        					}
    						
    					} catch (Exception e) {
    						e.printStackTrace();
    						
    					}
    					
    				}
    			});
    			t.start();
    		}
    	});
		register.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			Intent intent = new Intent(Login.this,Register.class);
    			startActivity(intent);
    			finish();
    		}
    	});
	}	
	
	public void sendToServer(PrintStream sendBuf,String message){
        sendBuf.println(message);
    }
	
    public void receiveFromServer(BufferedReader receiveBuf,String reply){
        try{
            reply = receiveBuf.readLine();
            
        }catch(IOException e){
            e.printStackTrace();
           
        }
    }
}
