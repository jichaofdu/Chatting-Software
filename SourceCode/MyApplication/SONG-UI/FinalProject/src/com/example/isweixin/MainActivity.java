package com.example.isweixin;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import client.Client;

public class MainActivity extends Activity implements OnViewChangeListener, OnClickListener{
	private MyScrollLayout mScrollLayout;	
	private LinearLayout[] mImageViews;	
	private int mViewCount;	
	private int mCurSel;
	private ImageView set;
	private ImageView add;
	private TextView chatTextView;
	private TextView discoveryTextView;
	private TextView contactListTextView;
	private ListView listview1;
	private ListView listview2;
	private ListView listview3;
	SelectPicPopupWindow menuInfoWindow;
	SelectAddPopupWindow menuWindow2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		chatTextView = (TextView)findViewById(R.id.liaotian);
		discoveryTextView = (TextView)findViewById(R.id.faxian);
		contactListTextView = (TextView)findViewById(R.id.tongxunlu);
		listview1 = (ListView)findViewById(R.id.listView1);
		listview2 = (ListView)findViewById(R.id.listView2);
		listview3 = (ListView)findViewById(R.id.listView3);
		PaintingAdapter ha = new PaintingAdapter(this,getHuahui());
		listview1.setAdapter(ha);
		listview1.setCacheColorHint(0);
		FriendAdapter py = new FriendAdapter(this,getPengyou());
		listview2.setAdapter(py);
		listview2.setCacheColorHint(0);
		ContactAdapter hc = new ContactAdapter(this,getContact());
		listview3.setAdapter(hc);
		listview3.setCacheColorHint(0);
    	mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout); 	
    	LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lllayout);   	
    	mViewCount = mScrollLayout.getChildCount();
    	mImageViews = new LinearLayout[mViewCount];

		for(int i = 0; i < mViewCount; i++)    	{
    		mImageViews[i] = (LinearLayout) linearLayout.getChildAt(i);
    		mImageViews[i].setEnabled(true);
    		mImageViews[i].setOnClickListener(this);
    		mImageViews[i].setTag(i);
    	}    	
    	mCurSel = 0;
    	mImageViews[mCurSel].setEnabled(false);    	
    	mScrollLayout.SetOnViewChangeListener(this);
    	set = (ImageView)findViewById(R.id.set);
    	add = (ImageView)findViewById(R.id.add);
    	set.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				uploadImage(MainActivity.this);
			}
		});
    	add.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			uploadImage2(MainActivity.this);
    		}
    	});
    }
	private ArrayList<Friend> getPengyou(){
		ArrayList<Friend> hhList = new ArrayList<>();
		//--------------------------Loop Unit---------------------------------------
		Friend h1 = new Friend();
		h1.setTxPath(R.drawable.icon+"");
		h1.setName("Name Slot");
		h1.setLastContent("Content Slot");
		h1.setLastTime("Time Slot");
		//-------------------------------------------------------------------
		hhList.add(h1);
		return hhList;
	} 
	private ArrayList<ContactP> getContact(){
		ArrayList<ContactP> hcList = new ArrayList<>();
		//----------------------------Loop Unit---------------------------------------
		ContactP c0 = new ContactP();
		c0.setTxPath(R.drawable.icon+"");
		c0.setName("Name Slot");
		hcList.add(c0);
		//-------------------------------------------------------------------
		return hcList;
	}
	
	private ArrayList<Painting> getHuahui(){
		ArrayList<Painting> hhList = new ArrayList<Painting>();
		//----------------------------Loop Unit---------------------------------------
		Painting h0 = new Painting();
		h0.setTxPath(R.drawable.icon+"");
		h0.setName("Name Slot");
		h0.setLastContent("Content Slot");
		h0.setLastTime("Time slot");
		hhList.add(h0);
		//-------------------------------------------------------------------
		return hhList;
	} 
	
	 public void uploadImage(final Activity context){
		 menuInfoWindow = new SelectPicPopupWindow(MainActivity.this, itemsOnClick);
		 menuInfoWindow.showAtLocation(MainActivity.this.findViewById(R.id.set), Gravity.TOP|Gravity.RIGHT, 10, 230);
		 menuInfoWindow.changeInfoButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 System.out.println("Click Change Info");
				 Intent intent = new Intent(MainActivity.this,ModifyUserInfo.class);
				 startActivity(intent);
				 menuInfoWindow.dismiss();
				 menuInfoWindow = null;
			 }
		 });
		 menuInfoWindow.logoutButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 System.out.println("Click Change Logout");
				 Client client = Client.getClient();
				 client.logout();
				 Intent intent = new Intent(MainActivity.this,Login.class);
				 startActivity(intent);
				 menuInfoWindow.dismiss();
				 menuInfoWindow = null;
				 finish();
			 }
		 });
	 }
	 public void uploadImage2(final Activity context){
		 menuWindow2 = new SelectAddPopupWindow(MainActivity.this, itemsOnClick2);
		 menuWindow2.showAtLocation(MainActivity.this.findViewById(R.id.add), Gravity.TOP|Gravity.RIGHT, 10, 230); //????layout??PopupWindow???????λ??
	 }
	 

	    private OnClickListener  itemsOnClick = new OnClickListener(){
			public void onClick(View v) {
				menuInfoWindow.dismiss();
			}
	    };
	    

	    private OnClickListener  itemsOnClick2 = new OnClickListener(){
	    	public void onClick(View v) {
	    		menuWindow2.dismiss();
	    	}
	    };
	    
	private void setCurPoint(int index) {
    	if (index < 0 || index > mViewCount - 1 || mCurSel == index){
    		return ;
    	}    	
    	mImageViews[mCurSel].setEnabled(true);
    	mImageViews[index].setEnabled(false);    	
    	mCurSel = index;
    	if(index == 0){
			chatTextView.setTextColor(0xff228B22);
			discoveryTextView.setTextColor(Color.BLACK);
			contactListTextView.setTextColor(Color.BLACK);
    	}else if(index == 1){
			chatTextView.setTextColor(Color.BLACK);
			discoveryTextView.setTextColor(0xff228B22);
			contactListTextView.setTextColor(Color.BLACK);
    	}else{
			chatTextView.setTextColor(Color.BLACK);
			discoveryTextView.setTextColor(Color.BLACK);
			contactListTextView.setTextColor(0xff228B22);
    	}
    }

    @Override
	public void OnViewChange(int view) {
		// TODO Auto-generated method stub
		setCurPoint(view);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int pos = (Integer)(v.getTag());
		setCurPoint(pos);
		mScrollLayout.snapToScreen(pos);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if ((keyCode == KeyEvent.KEYCODE_MENU)) {       
	            return true;
	        }
		return super.onKeyDown(keyCode, event);
	}

}
