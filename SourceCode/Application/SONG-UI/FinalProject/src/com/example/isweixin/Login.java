package com.example.isweixin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import client.*;

public class Login extends Activity {
	private EditText name;
	private EditText password;
	private Button login;
	private Button register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		name = (EditText)findViewById(R.id.login_name);
		password = (EditText)findViewById(R.id.login_password);
		login = (Button)findViewById(R.id.Login);
		register = (Button)findViewById(R.id.login_register);
		login.setOnClickListener(new View.OnClickListener() {
			String idString ;
			String passwordString ;
    		@Override
    		public void onClick(View arg0) {
    			idString = name.getText().toString();
    			passwordString = password.getText().toString();
    			if (idString.equals("") ||passwordString.equals("")){
    				Toast.makeText(getApplicationContext(), "Empty String",Toast.LENGTH_SHORT).show();
					return;
				}
				try {
					//---------------TO DO--------------------------
					int id = Integer.parseInt(idString);
					//向服务器发送登录信息
					Client c = Client.getClient();
					User user = c.handleLogin(id,passwordString);
					boolean flag;
					if(user.getId() == -1){
						flag = false;
						//Login fail
					}else{
						flag = true;
						//Login Success
					}
					//----------------------------------------------
					if (flag == true){
						Toast.makeText(getApplicationContext(), "Login Success.Name:" + user.getNickname(),Toast.LENGTH_LONG).show();
						Intent intent = new Intent(Login.this,MainActivity.class);
						startActivity(intent);
						finish();
					}else{
						Toast.makeText(getApplicationContext(), "Sorry,Login Fail.Reason:" + user.getNickname(),Toast.LENGTH_SHORT).show();
						return;
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "ID you input must be DIGIT.",Toast.LENGTH_SHORT).show();
					return;
				}
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

}
