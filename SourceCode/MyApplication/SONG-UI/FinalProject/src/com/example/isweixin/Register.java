package com.example.isweixin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import client.Client;
import client.User;

public class Register extends Activity {
	private EditText name;
	private EditText password;
	private EditText passwordAgain;
	private Button login;
	private Button register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register);
		
		name = (EditText)findViewById(R.id.name);
		password = (EditText)findViewById(R.id.password);
		passwordAgain = (EditText)findViewById(R.id.password1);
		
		login = (Button)findViewById(R.id.login);
		register = (Button)findViewById(R.id.register);
		
		login.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			Intent intent = new Intent(Register.this,Login.class);
    			startActivity(intent);
    			finish();
    		}
    	});

		//Code by jichao
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//--------------TO DO----------------------

				//向服务器端发送注册消息
				String usernameString = name.getText().toString();
				String passwordString = password.getText().toString();
				String passwordAgainString = passwordAgain.getText().toString();
				if(passwordString != null && passwordAgainString != null && passwordAgainString.equals(passwordString)){
					//向服务器发送注册信息
					Client c = Client.getClient();
					User user = c.handleRegister(usernameString,passwordString);
					boolean flag = false;
					if(user.getId() == -1){
						//Register fail
						flag = false;
					}else{
						flag = true;
						//Register Success
					}
					//接受服务器注册消息并采取对应操作
					if (flag == true){
						Toast.makeText(getApplicationContext(), "Register Success",Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(Register.this,Login.class);
						startActivity(intent);
						finish();
					}else{
						Toast.makeText(getApplicationContext(), "Sorry,Register Fail.",Toast.LENGTH_SHORT).show();
						return;
					}
				}else{
					Toast.makeText(getApplicationContext(), "Password and PasswordAgain Not match.",Toast.LENGTH_SHORT).show();
					return;
				}
				//-----------------------------------------
			}
		});

	}	
}
