package com.example.carpool;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.example.carsharing.R;
import com.example.http.HttpContectionUtil;

public class EvalPassengerActivity extends Activity{

	MyStatus myStatus;
	private boolean completed = false;
	private List<String> pusernames = null;
	private float[] ratings = null;
	
	private ListView lv_pusernames;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.evaluate_passenger);
		
		lv_pusernames = (ListView) findViewById(R.id.lv_pusernames);
		myStatus = (MyStatus) this.getApplication();
		Intent intent = this.getIntent();
		int pid = intent.getIntExtra("pid", -1);
		if(pid != -1){
			//说明传递过来的pid无误
			getPassengers(pid);
		}
	}
	private void getPassengers(final int pid) {
		new Thread(){
			public void run(){
				try{
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("pid", pid);
					String url = myStatus.getUrlString() + "arrive.php";
					HttpContectionUtil conn = new HttpContectionUtil();
					String responseStr = conn.ConnForResult(url, jsonObj);
					JSONArray array = new JSONArray(responseStr);
					if(array.getJSONObject(0).getInt("sum") == 0)
						return;
					pusernames = new ArrayList<String>();
					for(int i=0;i<array.length();i++){
						JSONObject passenger = array.getJSONObject(i);
						String pusername = passenger.getString("pusername");
						pusernames.add(pusername);
					}
					ratings = new float[array.length()];
					runOnUiThread(new Runnable(){
						public void run(){
							lv_pusernames.setAdapter(new MyAdapter());
						}
					});
				}catch(Exception e){
					return;
				}
			}
		}.start();
	}
	
	public void btn_confirmRatings(View view){
		new Thread(){
			public void run(){
				try{
					JSONArray array = new JSONArray();
					for(int i=0;i<pusernames.size();i++){
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("pusername", pusernames.get(i));
						jsonObj.put("aspassenger", ratings[i]);
						array.put(jsonObj);
					}
					String url = myStatus.getUrlString() + "rateforpassenger.php";
					HttpContectionUtil conn = new HttpContectionUtil();
					String arrayStr = array.toString();
					String responseStr = conn.ConnForResult(url, arrayStr);
					JSONObject response = new JSONObject(responseStr);
					String result = response.getString("result");
					if("success".equals(result)){
						runOnUiThread(new Runnable(){
							public void run(){
								Toast.makeText(EvalPassengerActivity.this, "Rate Complete", 0).show();
							}
						});
					}else{
						runOnUiThread(new Runnable(){
							public void run(){
								Toast.makeText(EvalPassengerActivity.this, "Rate Failed", 0).show();
							}
						});
					}
				}catch(Exception e){
					return;
				}
			}
		}.start();
	}
	
	private class MyAdapter extends BaseAdapter{
		private TextView tv_pusername;
		private RatingBar ratingBar;
		@Override
		public int getCount() {
			return pusernames.size();
		}
		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = EvalPassengerActivity.this.getLayoutInflater();
			View view = inflater.inflate(R.layout.evaluate_passenger_item, null);
			
			tv_pusername = (TextView) view.findViewById(R.id.passenger_name);
			ratingBar = (RatingBar) view.findViewById(R.id.ratingBar1);
			
			tv_pusername.setText(pusernames.get(position));
			
			//初始化ratings
			for(int i=0;i<this.getCount();i++){
				ratings[i] = ratingBar.getRating();
			}
			
			ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating,
						boolean fromUser) {
					ratings[position] = rating;
				}
			});
			return view;
		}
		
	}
}
