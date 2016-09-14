package com.example.carpool;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.carsharing.R;
import com.example.http.HttpContectionUtil;

public class GoActivity extends Activity {
	MyStatus myStatus;
	private int pid = -1;
	private ImageView iv_eval;
	private ImageView iv_go;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.go_activity);
		
		iv_eval = (ImageView) this.findViewById(R.id.evaluate_psg);
		iv_go = (ImageView) this.findViewById(R.id.start_driving);
		
		//设置事件
		iv_eval.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(pid == -1){
					//等待checkGo()结束
					return;
				}
				Intent intent = new Intent();
				intent.setClass(GoActivity.this, EvalPassengerActivity.class);
				intent.putExtra("pid", pid);
				startActivity(intent);
			}
		});
		iv_go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(GoActivity.this, RouteActivity.class);
				startActivity(intent);
			}
		});
		
		myStatus = (MyStatus) this.getApplication();
		checkGo();
	}

	private void checkGo() {
		new Thread(){
			public void run(){
				try{
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("username", myStatus.getUsername());
					String url = myStatus.getUrlString() + "checkgo.php";
					HttpContectionUtil conn = new HttpContectionUtil();
					String responseStr = conn.ConnForResult(url, jsonObj);
					JSONObject response = new JSONObject(responseStr);
					String result = response.getString("result");
					if("go".equals(result)){
						//上面按钮失效
						runOnUiThread(new Runnable(){
							public void run(){
								iv_go.setEnabled(true);
								iv_eval.setEnabled(false);
							}
						});
						pid = -2;
					}else if("arrive".equals(result)){
						//下面按钮失效
						runOnUiThread(new Runnable(){
							public void run(){
								iv_go.setEnabled(false);
								iv_eval.setEnabled(true);
							}
						});
						pid = response.getInt("pid");
					}
				}catch(Exception e){
					return;
				}
			}
		}.start();
	}
}
