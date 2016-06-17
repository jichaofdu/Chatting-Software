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
	
	//?????????????
	SelectPicPopupWindow menuWindow; //??????
	SelectAddPopupWindow menuWindow2; //??????
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init()
    {
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
		ArrayList<Friend> hhList = new ArrayList<Friend>();
		Friend h1 = new Friend();
		h1.setTxPath(R.drawable.icon+"");
		h1.setName("A");
		h1.setLastContent("????Ψ??????????????");
		h1.setLastTime("???? 18:00");
		
		Friend h2 = new Friend();
		h2.setTxPath(R.drawable.icon+"");
		h2.setName("B");
		h2.setLastContent("?????????????У??????????????????");
		h2.setLastTime("???? 17:40");
		
		Friend h3 = new Friend();
		h3.setTxPath(R.drawable.icon+"");
		h3.setName("C");
		h3.setLastContent("?????????????У?");
		h3.setLastTime("???? 17:00");
		
		Friend h4 = new Friend();
		h4.setTxPath(R.drawable.icon+"");
		h4.setName("D");
		h4.setLastContent("?????????????????????????????????????????");
		h4.setLastTime("???? 16:22");
		
		Friend h5 = new Friend();
		h5.setTxPath(R.drawable.icon+"");
		h5.setName("E");
		h5.setLastContent("?紵?ú????????????????????");
		h5.setLastTime("???? 16:11");
		
		
		
		hhList.add(h1);
		hhList.add(h2);
		hhList.add(h3);
		hhList.add(h4);
		hhList.add(h5);
		return hhList;
	} 
	private ArrayList<ContactP> getContact(){
		ArrayList<ContactP> hcList = new ArrayList<ContactP>();
		ContactP c0 = new ContactP();
		c0.setTxPath(R.drawable.icon+"");
		c0.setName("?????");
		
		ContactP c1 = new ContactP();
		c1.setTxPath(R.drawable.icon+"");
		c1.setName("?????????");
		
		ContactP c2 = new ContactP();
		c2.setTxPath(R.drawable.icon+"");
		c2.setName("QQ???");
		
		ContactP c3 = new ContactP();
		c3.setTxPath(R.drawable.icon+"");
		c3.setName("??????");
		
		ContactP c4 = new ContactP();
		c4.setTxPath(R.drawable.icon+"");
		c4.setName("С??");
		
		ContactP c5 = new ContactP();
		c5.setTxPath(R.drawable.icon+"");
		c5.setName("????????");
		
		ContactP c6 = new ContactP();
		c6.setTxPath(R.drawable.icon+"");
		c6.setName("??????");
		
		ContactP c7 = new ContactP();
		c7.setTxPath(R.drawable.icon+"");
		c7.setName("Ф??");
		
		ContactP c8 = new ContactP();
		c8.setTxPath(R.drawable.icon+"");
		c8.setName("????????");
		
		ContactP c9 = new ContactP();
		c9.setTxPath(R.drawable.icon+"");
		c9.setName("EatEvery");
		
		ContactP c10 = new ContactP();
		c10.setTxPath(R.drawable.icon+"");
		c10.setName("????");
		
		ContactP c11 = new ContactP();
		c11.setTxPath(R.drawable.icon+"");
		c11.setName("??????");
		hcList.add(c0);
		hcList.add(c1);
		hcList.add(c2);
		hcList.add(c3);
		hcList.add(c4);
		hcList.add(c5);
		hcList.add(c6);
		hcList.add(c7);
		hcList.add(c8);
		hcList.add(c9);
		hcList.add(c10);
		hcList.add(c11);
		
		return hcList;
	}
	
	private ArrayList<Painting> getHuahui(){
		ArrayList<Painting> hhList = new ArrayList<Painting>();
		Painting h1 = new Painting();
		h1.setTxPath(R.drawable.icon+"");
		h1.setName("A");
		h1.setLastContent("????Ψ??????????????");
		h1.setLastTime("???? 18:00");
		
		Painting h2 = new Painting();
		h2.setTxPath(R.drawable.icon+"");
		h2.setName("B");
		h2.setLastContent("?????????????У??????????????????");
		h2.setLastTime("???? 17:40");
		
		Painting h3 = new Painting();
		h3.setTxPath(R.drawable.icon+"");
		h3.setName("C");
		h3.setLastContent("?????????????У?");
		h3.setLastTime("???? 17:00");
		
		Painting h4 = new Painting();
		h4.setTxPath(R.drawable.icon+"");
		h4.setName("D");
		h4.setLastContent("?????????????????????????????????????????");
		h4.setLastTime("???? 16:22");
		
		Painting h5 = new Painting();
		h5.setTxPath(R.drawable.icon+"");
		h5.setName("E");
		h5.setLastContent("?紵?ú????????????????????");
		h5.setLastTime("???? 16:11");
		
		Painting h6 = new Painting();
		h6.setTxPath(R.drawable.icon+"");
		h6.setName("EatEvery");
		h6.setLastContent("Don't look me, I will eat you, Are you know");
		h6.setLastTime("???? 15:08");
		
		Painting h7 = new Painting();
		h7.setTxPath(R.drawable.icon+"");
		h7.setName("F");
		h7.setLastContent("?????????????????????????????????????????");
		h7.setLastTime("???? 15:01");
		
		Painting h8 = new Painting();
		h8.setTxPath(R.drawable.icon+"");
		h8.setName("G");
		h8.setLastContent("?????????????????????????????????????????");
		h8.setLastTime("???? 14:50");
		
		Painting h9 = new Painting();
		h9.setTxPath(R.drawable.icon+"");
		h9.setName("H");
		h9.setLastContent("this is very good fill");
		h9.setLastTime("???? 14:00");
		
		Painting h0 = new Painting();
		h0.setTxPath(R.drawable.icon+"");
		h0.setName("I");
		h0.setLastContent("????????????????????????????????????????????????");
		h0.setLastTime("???? 12:00");
		
		hhList.add(h1);
		hhList.add(h2);
		hhList.add(h3);
		hhList.add(h4);
		hhList.add(h5);
		hhList.add(h6);
		hhList.add(h7);
		hhList.add(h8);
		hhList.add(h9);
		hhList.add(h0);
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
