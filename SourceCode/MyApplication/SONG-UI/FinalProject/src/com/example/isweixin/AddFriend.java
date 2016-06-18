package com.example.isweixin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import client.Client;
import client.User;

import java.util.Vector;

public class AddFriend extends Activity {
    private EditText searchNicknameEditText;
    private Button cancelButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);
        this.searchNicknameEditText = (EditText)findViewById(R.id.searchNickname);
        this.cancelButton = (Button)findViewById(R.id.cancelButton);
        this.searchButton = (Button)findViewById(R.id.searchButton);
        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchContent = searchNicknameEditText.getText().toString();
                Toast.makeText(getApplicationContext(), "Search Content:" + searchContent,Toast.LENGTH_SHORT).show();

                //将获取到的内容发送到服务端，获取client返回的vector然后逐一显示在下边的list view中
                Vector<User> searchResultList = Client.getClient().searchUserByName(searchContent);
                Toast.makeText(getApplicationContext(), "Search Result:" + searchResultList.get(0).getId(),Toast.LENGTH_SHORT).show();





                finish();
            }
        });
    }

}
