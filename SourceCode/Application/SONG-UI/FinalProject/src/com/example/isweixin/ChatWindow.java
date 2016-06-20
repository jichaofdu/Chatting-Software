package com.example.isweixin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by chaoj on 2016-06-18.
 */
public class ChatWindow extends Activity {
    private EditText inputMessageEditText;
    private Button cancelButton;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_window);
        this.inputMessageEditText = (EditText)findViewById(R.id.inputMessage);
        this.cancelButton = (Button)findViewById(R.id.cancelButton);
        this.sendButton = (Button)findViewById(R.id.sendButton);
        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatMsg = inputMessageEditText.getText().toString();
                //将聊天消息发送到服务器，注意要同时并行接受对方发来的聊天信息

            }
        });
    }
}
