package com.example.carpool;

import com.example.carsharing.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ConfirmOrder extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Requestorder info = new Requestorder();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		TextView theway = (TextView)findViewById(R.id.textView1);
		TextView user = (TextView)findViewById(R.id.textView2);
		TextView state = (TextView)findViewById(R.id.textView3);
		TextView thenum = (TextView)findViewById(R.id.textView4);
		Button Btn = (Button)findViewById(R.id.button1);
		theway.setText("当前路线：" + info.theway);
		user.setText("当前乘客：" + info.username);
		state.setText("当前状态：" + info.state);
		thenum.setText("已确认：" + info.thetotalprice);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirm_order, menu);
		return true;
	}

}
