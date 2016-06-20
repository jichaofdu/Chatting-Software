package com.example.isweixin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import client.Client;
import client.User;

import java.util.ArrayList;
import java.util.Vector;

public class AddFriend extends Activity {
    private EditText searchNicknameEditText;
    private Button searchButton;
    private ListView serchResultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);
               
        this.searchNicknameEditText = (EditText)findViewById(R.id.searchNickname);
        this.searchButton = (Button)findViewById(R.id.searchButton);
        
        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	getContact();
                //finish();
            }
        });
    }


    private void getContact(){

        String searchContent = searchNicknameEditText.getText().toString();

        Vector<User> searchResultList = Client.getClient().searchUserByName(searchContent);
        if (searchResultList.size() == 0){
        	Toast.makeText(getApplicationContext(), "No Result!",Toast.LENGTH_SHORT).show();

        }
        ArrayList<SearchUser> hcList = new ArrayList<>();
		int count = searchResultList.size();
		for(int i = 0;i < count;i++){
			SearchUser c0 = new SearchUser();
			c0.id = searchResultList.get(i).getId();
			c0.setTxPath(R.drawable.icon+"");
			c0.setName(searchResultList.get(i).getNickname());
			c0.setDesc(searchResultList.get(i).getIntroduction());
			
			hcList.add(c0);
		}
        /////////////
        SearchUserAdapter cz = new SearchUserAdapter(this,hcList);
        serchResultListView = (ListView)findViewById(R.id.listView);
        serchResultListView.setAdapter(cz);
        serchResultListView.setCacheColorHint(0);

	}

}
