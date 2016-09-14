package com.example.carpool;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carpool.adt.DriveRouteItem;
import com.example.carsharing.R;
import com.example.http.HttpContectionUtil;

public class RouteActivity extends Activity {
	MyStatus myStatus;
	private ListView lv_driveItems;
	List<DriveRouteItem> driveItems = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.route_activity);

		myStatus = (MyStatus) this.getApplication();

		lv_driveItems = (ListView) this.findViewById(R.id.lv_routeItems);
		getDriveItems();
	}

	private void getDriveItems() {
		new Thread() {
			public void run() {
				try {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("username", myStatus.getUsername());
					String url = myStatus.getUrlString() + "getdriverroute.php";
					HttpContectionUtil conn = new HttpContectionUtil();
					String responseStr = conn.ConnForResult(url, jsonObj);

					// 解析返回的数据
					driveItems = DriveRouteItem.parseJsonArray(responseStr);
					if (driveItems == null)
						return;
					// 设置数据适配器 必须在ui线程
					runOnUiThread(new Runnable() {
						public void run() {
							 lv_driveItems.setAdapter(new DriveItemsLVAdapter());
						}
					});
				} catch (Exception e) {
					return;
					// TODO Auto-generated catch block
				}
			}
		}.start();
	}
	
	private class DriveItemsLVAdapter extends BaseAdapter {
		private TextView tv_route;

		@Override
		public int getCount() {
			return driveItems.size();
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflator = RouteActivity.this.getLayoutInflater();
			View view = inflator.inflate(R.layout.route_item,
					null);

			tv_route = (TextView) view
					.findViewById(R.id.textView2);

			DriveRouteItem routeItem = driveItems.get(position);
			tv_route.setText(routeItem.getMap_begin() + "->"
					+ routeItem.getMap_end());

			return view;
		}
	}
}
