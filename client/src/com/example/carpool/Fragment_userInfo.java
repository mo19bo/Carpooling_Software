package com.example.carpool;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.carsharing.R;

public class Fragment_userInfo extends Fragment {

	private TextView tv_userName;
	private TextView tv_gender;
	private TextView tv_tel;
	private TextView tv_driveCredit;
	private TextView tv_rideCredit;
	private TextView tv_selfIntro;
	private ImageView iv_photo;
	private RatingBar rb_driver;
	private RatingBar rb_passenger;
	
	MyStatus myStatus;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		myStatus = (MyStatus) this.getActivity().getApplication();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_userinfo, container,
				false);

		tv_userName = (TextView) view
				.findViewById(R.id.tv_userName_fragment_userInfo);
		tv_gender = (TextView) view
				.findViewById(R.id.tv_gender_fragment_userInfo);
		tv_tel = (TextView) view.findViewById(R.id.tv_tel_fragment_userInfo);
		tv_driveCredit = (TextView) view
				.findViewById(R.id.tv_drivercredit);
		tv_rideCredit = (TextView) view
				.findViewById(R.id.tv_passengercredit);
		tv_selfIntro = (TextView) view
				.findViewById(R.id.tv_selfIntro_fragment_userInfo);
		iv_photo = (ImageView) view
				.findViewById(R.id.contactBadge_fragment_userInfo);
		rb_driver = (RatingBar) view.findViewById(R.id.ratingBar1);
		rb_passenger = (RatingBar) view.findViewById(R.id.ratingBar2);

//		String userInfoJson = getUserInfoJson();
//		UserInfo userInfo = getUserInfo(userInfoJson);
//		showUserInfo(userInfo);
		showUserInfo();
		
		return view;
	}

	private void showUserInfo() {
		tv_userName.setText(myStatus.getUsername());
		
		//ÐÔ±ð
		if(myStatus.getSex() == 0){
			tv_gender.setText("female");
		}else{
			tv_gender.setText("male");
		}
		
		tv_tel.setText(myStatus.getPhonenumber());
		tv_driveCredit.setText(myStatus.getDrivercredit() + "");
		tv_rideCredit.setText(myStatus.getPassengercredit() + "");
		tv_selfIntro.setText(myStatus.getJob() + " " + myStatus.getIntroduction());
		rb_driver.setRating(myStatus.getDrivercredit());
		rb_passenger.setRating(myStatus.getPassengercredit());
	}
}
