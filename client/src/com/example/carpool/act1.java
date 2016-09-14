package com.example.carpool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.carsharing.R;

public class act1 extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act1);
		setTitle("Welcome!");

		ImageButton Btn = (ImageButton) findViewById(R.id.imageButton1);
		ImageButton Btn1 = (ImageButton) findViewById(R.id.imageButton2);
		ImageButton Btn2 = (ImageButton) findViewById(R.id.imageButton3);
		ImageButton btn_go = (ImageButton) findViewById(R.id.go_drive);
		ImageButton btn_eval = (ImageButton) findViewById(R.id.evaluate);

		Btn.setOnClickListener(new ImageButton.OnClickListener() {// 
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(act1.this, From.class);
				startActivity(intent);
			}
		});
		Btn1.setOnClickListener(new Button.OnClickListener() {// search for
																// books
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(act1.this, Line.class);
				startActivity(intent);
			}
		});
		Btn2.setOnClickListener(new Button.OnClickListener() {// 
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(act1.this, MainActivity1.class);
				startActivity(intent);
			}
		});
		
		btn_go.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent();
				intent.setClass(act1.this, GoActivity.class);
				startActivity(intent);
			}
		});
		
		btn_eval.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent();
				intent.setClass(act1.this, DriveEvalActivity.class);
				startActivity(intent);
				
			}
		});
	}

}
