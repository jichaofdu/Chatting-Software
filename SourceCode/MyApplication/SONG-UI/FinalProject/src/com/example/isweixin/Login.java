package com.example.isweixin;

import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends Activity {
	private EditText name;
	private EditText password;
	private Button login;
	private Button register;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		name = (EditText)findViewById(R.id.name);
		password = (EditText)findViewById(R.id.password);
		login = (Button)findViewById(R.id.login);
		register = (Button)findViewById(R.id.register);
		
		login.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			Intent intent = new Intent(Login.this,
    					MainActivity.class);
    			startActivity(intent);
    			finish();
    		}
    	});
		register.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			Intent intent = new Intent(Login.this,
    					Register.class);
    			startActivity(intent);
    			finish();
    		}
    	});
	}	
}
