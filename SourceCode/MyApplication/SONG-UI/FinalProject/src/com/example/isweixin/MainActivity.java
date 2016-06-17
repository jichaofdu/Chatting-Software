package com.example.isweixin;

import java.util.ArrayList;
import android.app.Activity;
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

public class MainActivity extends Activity implements OnViewChangeListener, OnClickListener{
	private MyScrollLayout mScrollLayout;	
	private LinearLayout[] mImageViews;	
	private int mViewCount;	
	private int mCurSel;
	private ImageView set;
	private ImageView add;
	private TextView liaotian;
	private TextView faxian;
	private TextView tongxunlu;
	private ListView listview1;
	private ListView listview2;
	private ListView listview3;
	SelectPicPopupWindow menuWindow;
	SelectAddPopupWindow menuWindow2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		liaotian = (TextView)findViewById(R.id.liaotian);
		faxian = (TextView)findViewById(R.id.faxian);
		tongxunlu = (TextView)findViewById(R.id.tongxunlu);
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
		//-------------------------------------------------------------------
		Friend h1 = new Friend();
		h1.setTxPath(R.drawable.icon+"");
		h1.setName("A");
		h1.setLastContent("????Ψ??????????????");
		h1.setLastTime("???? 18:00");
		//-------------------------------------------------------------------
		hhList.add(h1);
		return hhList;
	} 
	private ArrayList<ContactP> getContact(){
		ArrayList<ContactP> hcList = new ArrayList<>();
		//-------------------------------------------------------------------
		ContactP c0 = new ContactP();
		c0.setTxPath(R.drawable.icon+"");
		c0.setName("?????");
		hcList.add(c0);
		//-------------------------------------------------------------------
		return hcList;
	}
	
	private ArrayList<Painting> getHuahui(){
		ArrayList<Painting> hhList = new ArrayList<Painting>();
		//-------------------------------------------------------------------
		Painting h0 = new Painting();
		h0.setTxPath(R.drawable.icon+"");
		h0.setName("I");
		h0.setLastContent("????????????????????????????????????????????????");
		h0.setLastTime("???? 12:00");
		hhList.add(h0);
		//-------------------------------------------------------------------
		return hhList;
	} 
	
	 public void uploadImage(final Activity context){
		 menuWindow = new SelectPicPopupWindow(MainActivity.this, itemsOnClick);
			//???????
		menuWindow.showAtLocation(MainActivity.this.findViewById(R.id.set), Gravity.TOP|Gravity.RIGHT, 10, 230); //????layout??PopupWindow???????λ??
	 }
	 public void uploadImage2(final Activity context){
		 menuWindow2 = new SelectAddPopupWindow(MainActivity.this, itemsOnClick2);
		 //???????
		 menuWindow2.showAtLocation(MainActivity.this.findViewById(R.id.add), Gravity.TOP|Gravity.RIGHT, 10, 230); //????layout??PopupWindow???????λ??
	 }
	 
	 //?????????????????
	    private OnClickListener  itemsOnClick = new OnClickListener(){

			public void onClick(View v) {
				menuWindow.dismiss();
			}
	    };
	    
	    //?????????????????
	    private OnClickListener  itemsOnClick2 = new OnClickListener(){
	    	
	    	public void onClick(View v) {
	    		menuWindow2.dismiss();
	    	}
	    };
	    
	private void setCurPoint(int index)
    {
    	if (index < 0 || index > mViewCount - 1 || mCurSel == index){
    		return ;
    	}    	
    	mImageViews[mCurSel].setEnabled(true);
    	mImageViews[index].setEnabled(false);    	
    	mCurSel = index;
    	
    	if(index == 0){
    		liaotian.setTextColor(0xff228B22);
    		faxian.setTextColor(Color.BLACK);
    		tongxunlu.setTextColor(Color.BLACK);
    	}else if(index==1){
    		liaotian.setTextColor(Color.BLACK);
    		faxian.setTextColor(0xff228B22);
    		tongxunlu.setTextColor(Color.BLACK);
    	}else{
    		liaotian.setTextColor(Color.BLACK);
    		faxian.setTextColor(Color.BLACK);
    		tongxunlu.setTextColor(0xff228B22);
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
