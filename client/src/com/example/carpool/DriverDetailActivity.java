package com.example.carpool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.carpool.adt.UserInfo;
import com.example.carsharing.R;

public class DriverDetailActivity extends Activity{

	private TextView tv_username;
	private TextView tv_gender;
	private TextView tv_tel;
	private TextView tv_driveCredit;
	private RatingBar ratingBar;
	private TextView tv_intro;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driverdetail);
		
		tv_username = (TextView) this.findViewById(R.id.tv_userName_fragment_userInfo);
		tv_gender = (TextView) this.findViewById(R.id.tv_gender_fragment_userInfo);
		tv_tel = (TextView) this.findViewById(R.id.tv_tel_fragment_userInfo);
		tv_driveCredit = (TextView) this.findViewById(R.id.tv_driveCredit_fragment_userInfo);
		ratingBar = (RatingBar) this.findViewById(R.id.ratingBar1);
		tv_intro = (TextView) this.findViewById(R.id.tv_selfIntro_fragment_userInfo);
		
		Intent intent = getIntent();
		UserInfo userInfo = UserInfo.parseJsonObj(intent.getStringExtra("userinfo"));
		
		tv_username.setText(userInfo.getUsername());
		if(userInfo.getSex()==0){
			tv_gender.setText("Female");
		}else{
			tv_gender.setText("Male");
		}
		tv_tel.setText(userInfo.getPhonenumber());
		//tv_driveCredit.setText(userInfo.get) ÆÀ¼¶
		tv_intro.setText(userInfo.getIntroduction());
	}
}
