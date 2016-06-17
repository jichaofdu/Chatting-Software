package com.example.isweixin;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class SelectPicPopupWindow extends PopupWindow {

	//------------------------------------------------
	private Button changeInfoButton;
	private Button logoutButton;
	//------------------------------------------------
	private View mMenuView;

	public SelectPicPopupWindow(final Activity context,OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.bottomdialog, null);
		int h = context.getWindowManager().getDefaultDisplay().getHeight();
		int w = context.getWindowManager().getDefaultDisplay().getWidth();

		this.setContentView(mMenuView);
		this.setWidth(w/2+50);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.mystyle);
		ColorDrawable dw = new ColorDrawable(0000000000);
		this.setBackgroundDrawable(dw);
		mMenuView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}
				return true;
			}
		});
		//------------------------
		changeInfoButton = (Button)mMenuView.findViewById(R.id.changeInfo);
		logoutButton = (Button)mMenuView.findViewById(R.id.logout);
		changeInfoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("Click Change Info");
			}
		});
		logoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("Click Change Logout");
			}
		});
		//------------------------
	}

}
