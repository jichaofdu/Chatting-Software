package com.example.isweixin;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import client.Client;

public class ContactAdapter extends BaseAdapter {
	private Context context;
	public Button deletButton;
	private ArrayList<ContactP> list = new ArrayList<ContactP>();
	
	public ContactAdapter(Context context,ArrayList<ContactP> list){
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ContactP hh = list.get(position);
		H h = null;
		if(view==null){
			h = new H();
			view = LayoutInflater.from(context).inflate(R.layout.tongxunlu, parent, false);
			deletButton = (Button)view.findViewById(R.id.delete);
			deletButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Client.getClient().deleteFriend(hh.getID());
					Activity act = (Activity)context;
					Intent intent = new Intent(context,MainActivity.class);
					act.startActivity(intent);
					act.finish();
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
