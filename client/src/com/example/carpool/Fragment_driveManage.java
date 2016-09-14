package com.example.carpool;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carpool.adt.DriveRouteItem;
import com.example.carsharing.R;
import com.example.http.HttpContectionUtil;

public class Fragment_driveManage extends Fragment {
	private ListView lv_driveItems;
	private Activity activity;
	private List<DriveRouteItem> driveItems;
	MyStatus myStatus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		activity = this.getActivity();
		myStatus = (MyStatus) this.getActivity().getApplication();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_drivemanage, container,
				false);

		lv_driveItems = (ListView) view
				.findViewById(R.id.lv_driveItems_fragment_drivemanage);
		getDriveItems();
		return view;
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
					activity.runOnUiThread(new Runnable() {
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
		private TextView tv_time;
		private TextView tv_description;
		private Button btn_cancel;
		private Button btn_modify;

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
			LayoutInflater inflator = activity.getLayoutInflater();
			View view = inflator.inflate(R.layout.fragment_drivemange_item,
					null);

			tv_route = (TextView) view
					.findViewById(R.id.tv_route_fragment_drivemanage_item);
			tv_time = (TextView) view
					.findViewById(R.id.tv_time_fragment_drivemanage_item);
			tv_description = (TextView) view
					.findViewById(R.id.tv_description_fragment_drivemanage_item);
			btn_cancel = (Button) view
					.findViewById(R.id.btn_cancelOrder_fragment_drivemanage_item);
			btn_modify = (Button) view.findViewById(R.id.btn_modify);

			DriveRouteItem routeItem = driveItems.get(position);
			tv_route.setText(routeItem.getMap_begin() + "->"
					+ routeItem.getMap_end());
			tv_time.setText(routeItem.getStart_time());
			tv_description.setText(routeItem.getBrief());

			// 设置删除事件
			btn_cancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					new Thread() {
						public void run() {
							try {
								JSONObject jsonObj = new JSONObject();
								jsonObj.put("pid", driveItems.get(position)
										.getPid());
								HttpContectionUtil conn = new HttpContectionUtil();

								// 删除一条路线
								String url = myStatus.getUrlString()
										+ "driverpublishcancel.php";

								String responseStr = conn.ConnForResult(url,
										jsonObj);
								JSONObject response = new JSONObject(
										responseStr);
								String result = response.getString("result");
								if ("failed".equals(result)) {
									activity.runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(activity,
													"Delete Failed", 0).show();
										}
									});
								} else {
									activity.runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(activity,
													"Delete Success", 0).show();
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

			// 设置修改事件
			btn_modify.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(activity, ModifyFormActivity.class);
					intent.putExtra("curDriveItem", driveItems.get(position)
							.toJsonStr());
					activity.startActivity(intent);
				}
			});

			return view;
		}
	}
}
