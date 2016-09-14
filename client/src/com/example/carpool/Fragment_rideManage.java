package com.example.carpool;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carpool.adt.OrderItem;
import com.example.carsharing.R;
import com.example.http.HttpContectionUtil;

public class Fragment_rideManage extends Fragment {

	private ListView lv_orderItems;
	private Activity activity;
	private List<OrderItem> orderItems = null;
	MyStatus myStatus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
//		Log.i("rideManage", "onCreate");
		activity = this.getActivity();
		myStatus = (MyStatus) this.getActivity().getApplication();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		Log.i("rideManage", "onCreateView");
		View view = inflater.inflate(R.layout.fragment_ridemanage, container,
				false);
		lv_orderItems = (ListView) view
				.findViewById(R.id.lv_rideItems_fragment_ridemanage);
		getOrderItems();
		return view;
	}

	/**
	 * 通过服务器传递username获得相关的OrderItems传递给orderItems成员变量
	 *只在onCtreate的时候执行
	 */
	private void getOrderItems() {
		new Thread(){
			public void run(){
				try {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("username", myStatus.getUsername());
					String url = myStatus.getUrlString() + "getpassengerorder.php";
					HttpContectionUtil conn = new HttpContectionUtil();
					String responseStr = conn.ConnForResult(url, jsonObj);		
					
					//解析返回的数据
					orderItems = OrderItem.parseJsonArray(responseStr);
					if(orderItems == null){
						return;
					}
					
					//设置数据适配器 必须在ui线程
					activity.runOnUiThread(new Runnable(){
						public void run(){
							lv_orderItems.setAdapter(new RideItemsLVAdapter());
						}
					});
				} catch (Exception e) {
					return;
				}
			}
		}.start();
	}

	private class RideItemsLVAdapter extends BaseAdapter {
		private TextView tv_route;
		private TextView tv_time;
		private TextView tv_description;
		private Button btn_cancel;

		@Override
		public int getCount() {
			return orderItems.size();
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
			LayoutInflater inflator = activity.getLayoutInflater();
			View view = inflator
					.inflate(R.layout.fragment_ridemange_item, null);

			tv_route = (TextView) view
					.findViewById(R.id.tv_route_fragment_ridemanage_item);
			tv_time = (TextView) view
					.findViewById(R.id.tv_time_fragment_ridemanage_item);
			tv_description = (TextView) view
					.findViewById(R.id.tv_description_fragment_ridemanage_item);
			btn_cancel = (Button) view.findViewById(R.id.btn_cancelOrder_fragment_ridemanage_item);

			OrderItem orderItem = orderItems.get(position);
			tv_route.setText(orderItem.getMap_begin() + "->" + orderItem.getMap_end());
			tv_time.setText(orderItem.getStart_time());
			tv_description.setText(orderItem.getBrief());
			btn_cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new Thread(){
						public void run(){
							try {
								JSONObject jsonObj = new JSONObject();
								jsonObj.put("opid", orderItems.get(position).getOpid());
								HttpContectionUtil conn = new HttpContectionUtil();
								
								//删除一个订单的php的url地址
								String url = myStatus.getUrlString() + "ordercancel.php";
								
								String responseStr = conn.ConnForResult(url, jsonObj);
								JSONObject response = new JSONObject(responseStr);
								Boolean result = response.getBoolean("result");
								if(!result){
									activity.runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(activity, "Cancel Failed", 0).show();
										}
									});
								}else{
									activity.runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(activity, "Cancel Success", 0).show();
										}
									});
								}
							} catch (Exception e) {
								return;
							}
						}
					}.start();
				}
			});
			return view;
		}
	}
}
