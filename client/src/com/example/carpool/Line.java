package com.example.carpool;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carpool.adt.SearchItem;
import com.example.carpool.adt.UserInfo;
import com.example.carsharing.R;
import com.example.http.HttpContectionUtil;

public class Line extends Activity {

	MyStatus myStatus;
	private List<SearchItem> searchItems;
	private ListView lv_searchItems;
	private Button button1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_line);
		
		myStatus = (MyStatus) this.getApplicationContext();
		lv_searchItems = (ListView) this.findViewById(R.id.lv_driveItems);
		button1 = (Button)this.findViewById(R.id.button1);
		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		lv_searchItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				AlertDialog.Builder builder = new Builder(Line.this);
				builder.setMessage("Add to order list?").setTitle("Note").setNegativeButton("No", null).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new Thread(){
							public void run(){
								try{
									JSONObject jsonObj = new JSONObject();
									jsonObj.put("pid", searchItems.get(position).getPid());
									jsonObj.put("username", myStatus.getUsername());
									String url = myStatus.getUrlString() + "makeorder.php";
									HttpContectionUtil conn = new HttpContectionUtil();
									String responseStr = conn.ConnForResult(url, jsonObj);
									JSONObject response = new JSONObject(responseStr);
									String result = response.getString("result");
									if("exist".equals(result)){
										runOnUiThread(new Runnable(){
											public void run(){
												Toast.makeText(Line.this, "Already exist", 0).show();
											}
										});
									}else if("success".equals(result)){
										runOnUiThread(new Runnable(){
											public void run(){
												Toast.makeText(Line.this, "Success", 0).show();
											}
										});
									}
								}catch(Exception e){
									return;
								}
							}
						}.start();
					}
				}).show();
				return true;
			}
		});
		
		getDriveItems();
	}
	
	private void getDriveItems() {
		new Thread(){
			public void run(){
				try {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("map_end", "333");
					jsonObj.put("username", myStatus.getUsername());
					String url = myStatus.getUrlString() + "selectorder.php";
					HttpContectionUtil conn = new HttpContectionUtil();
					String responseStr = conn.ConnForResult(url, jsonObj);		
					
					//解析返回的数据
					searchItems = SearchItem.parseJsonArray(responseStr);
					if(searchItems == null)
						return;
					//设置数据适配器 必须在ui线程
					runOnUiThread(new Runnable(){
						public void run(){
							lv_searchItems.setAdapter(new SearchItemsLVAdapter());
						}
					});
				} catch (Exception e) {
					return;
				}
			}
		}.start();
	}
	
	private class SearchItemsLVAdapter extends BaseAdapter{
		private TextView tv_route;
		private TextView tv_time;
		private TextView tv_description;
		private Button btn_map;
		private Button btn_driverInfo;
		
		@Override
		public int getCount() {
			return searchItems.size();
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
			LayoutInflater inflator = Line.this.getLayoutInflater();
			View view = inflator.inflate(R.layout.line_item, null);
			
			tv_route = (TextView) view.findViewById(R.id.tv_route_fragment_drivemanage_item);
			tv_time = (TextView) view.findViewById(R.id.tv_time_fragment_drivemanage_item);
			tv_description = (TextView) view.findViewById(R.id.tv_description_fragment_drivemanage_item);
			btn_map = (Button) view.findViewById(R.id.btn_map);
			btn_driverInfo = (Button) view.findViewById(R.id.btn_driverInfo);
			
			//地图按钮事件
			btn_map.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			});
			
			//司机信息点击事件
			btn_driverInfo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new Thread(){
						public void run(){
							try{
								JSONObject jsonObj = new JSONObject();
								jsonObj.put("username", searchItems.get(position).getUsername());
								HttpContectionUtil conn = new HttpContectionUtil();
								String url  = myStatus.getUrlString() + "driverinfo.php";
								String responseStr = conn.ConnForResult(url, jsonObj);
								
								Intent intent = new Intent();
								intent.setClass(Line.this, DriverDetailActivity.class);
								intent.putExtra("userinfo", responseStr);
								startActivity(intent);
							}catch(Exception e){
								return;
							}
						}
					}.start();
				}
			});
			
			SearchItem searchItem = searchItems.get(position);
			tv_route.setText(searchItem.getMap_begin() + "->" + searchItem.getMap_end());
			tv_time.setText(searchItem.getStart_time());
			tv_description.setText(searchItem.getBrief());
			
			return view;
		}
	}
}
