package com.example.isweixin;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import client.Client;

public class PaintingAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Painting> list = new ArrayList<Painting>();
	private Button sendButton;
	private EditText massageInputEditText;
	public PaintingAdapter(Context context, ArrayList<Painting> list){
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
		Painting hh = list.get(position);
		H h = null;
		if(view==null){
			h = new H();
			view = LayoutInflater.from(context).inflate(R.layout.liaotian, parent, false);
			h.name = (TextView)view.findViewById(R.id.name);
			h.time = (TextView)view.findViewById(R.id.time);
			h.lastmsg = (TextView)view.findViewById(R.id.lastmsg);
			sendButton = (Button)view.findViewById(R.id.sendButton);
			massageInputEditText = (EditText)view.findViewById(R.id.messageInput);
			sendButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					String inputMsg = massageInputEditText.getText().toString();
					Client.getClient().sendChatMessage(inputMsg);
				}
			});
			view.setTag(h);
		}else{
			h = (H)view.getTag();
		}
		
		h.name.setText(hh.getName());
		h.time.setText(hh.getLastTime());
		h.lastmsg.setText(hh.getLastContent());
		
		return view;
	}

	class H{
		TextView name;
		TextView time;
		TextView lastmsg;
	}
}
