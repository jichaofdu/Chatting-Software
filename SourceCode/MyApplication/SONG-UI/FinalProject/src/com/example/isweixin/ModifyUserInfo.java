package com.example.isweixin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import client.*;
public class ModifyUserInfo extends Activity{
    private EditText modifyNickname;
    private EditText modifyPassword;
    private EditText modifyIntroduction;
    private Button submitModification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_user_info);
        modifyNickname = (EditText)findViewById(R.id.modifyPassword);//--------!!
        modifyNickname.setText(Client.getClient().getLocalUser().getNickname());
        modifyPassword = (EditText)findViewById(R.id.modifyNickname);//-----------!!
        modifyPassword.setText(Client.getClient().getLocalUser().getPassword());
        modifyIntroduction = (EditText)findViewById(R.id.modifyIntroduction);
        modifyIntroduction.setText(Client.getClient().getLocalUser().getIntroduction());
        submitModification = (Button)findViewById(R.id.modifyConfirm);
        submitModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id;
                String newNickname = modifyNickname.getText().toString();
                String newPassword = modifyPassword.getText().toString();
                String newIntroduction = modifyIntroduction.getText().toString();
                Client c = Client.getClient();
                id = c.getLocalUser().getId();
                c.handleUpdateProfile(id,newNickname,newPassword,newIntroduction);
                Toast.makeText(getApplicationContext(), "Update User Information Complete",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
