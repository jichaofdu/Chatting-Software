package com.example.isweixin;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import client.Client;

public class SearchUserAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<SearchUser> list = new ArrayList<SearchUser>();
	public Button add;
	public SearchUserAdapter(Context context,ArrayList<SearchUser> list){
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		final SearchUser hh = list.get(position);
		H h = null;
		if(view==null){
			h = new H();
			view = LayoutInflater.from(context).inflate(R.layout.searchuser, parent, false);
			add = (Button)view.findViewById(R.id.add);
	        add.setOnClickListener(new View.OnClickListener() {
	    		@Override
	    		public void onClick(View arg0) {
					Client.getClient().addFriend(hh.getID());
	    		}
	    	});
			
			h.pic = (ImageView)view.findViewById(R.id.tx1);
			h.name = (TextView)view.findViewById(R.id.tx2);
			h.introduction = (TextView)view.findViewById(R.id.tx3);
			
			view.setTag(h);
		}else{
			h = (H)view.getTag();
		}
		
		h.pic.setImageResource(Integer.parseInt(hh.getTxPath()));
		h.name.setText(hh.getName());
		h.introduction.setText(hh.getDesc());
		return view;
	}
	
	class H{
		int id;
		ImageView pic;
		TextView name;
		TextView introduction;
	}
}
