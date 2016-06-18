package com.example.isweixin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTweet extends Activity {
    private EditText tweetContentEditText;
    private Button cancelButton;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tweet);
        this.tweetContentEditText = (EditText)findViewById(R.id.tweetContent);
        this.cancelButton = (Button)findViewById(R.id.cancelButton);
        this.submitButton = (Button)findViewById(R.id.submitButton);
        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = tweetContentEditText.getText().toString();
                //将tweet内容提交到服务器


            }
        });

    }
}
