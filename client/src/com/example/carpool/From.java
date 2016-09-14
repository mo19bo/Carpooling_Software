package com.example.carpool;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;






import com.example.carsharing.R;
import com.example.http.HttpContectionUtil;

public class From extends Activity {
	private DatePicker datePicker;
	
	private CheckBox cb_mon;
	private CheckBox cb_tue;
	private CheckBox cb_wed;
	private CheckBox cb_thu;
	private CheckBox cb_fri;
	private CheckBox cb_sat;
	private CheckBox cb_sun;
	
	private EditText et_hour;
	private EditText et_minute;
	private EditText et_description;
	
	private Spinner spinner_personNum;
	private Spinner spinner_howManyDaysAfter;
	
	private TextView tv_username;
	private TextView tv_serverTime;
	private TextView tv_curLocation;
	private TextView tv_price;
	
	private Button btn_map;
	private Button btn_publish;
	
	MyStatus myStatus;
	/* （非 Javadoc）
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_from);
		
		//绑定UI控件
		datePicker = (DatePicker) this.findViewById(R.id.datePicker);
		
		cb_mon = (CheckBox) this.findViewById(R.id.checkBox1);
		cb_tue = (CheckBox) this.findViewById(R.id.checkBox2);
		cb_wed = (CheckBox) this.findViewById(R.id.checkBox3);
		cb_thu = (CheckBox) this.findViewById(R.id.checkBox4);
		cb_fri = (CheckBox) this.findViewById(R.id.checkBox5);
		cb_sat = (CheckBox) this.findViewById(R.id.checkBox6);
		cb_sun = (CheckBox) this.findViewById(R.id.checkBox7);
		
		et_description = (EditText) this.findViewById(R.id.et_description);
		et_hour = (EditText) this.findViewById(R.id.et_hour);
		et_minute = (EditText) this.findViewById(R.id.et_minute);
		
		tv_curLocation = (TextView) this.findViewById(R.id.tv_curLocation);
		tv_price = (TextView) this.findViewById(R.id.tv_price);
		tv_serverTime = (TextView) this.findViewById(R.id.tv_serverTime);
		tv_username = (TextView) this.findViewById(R.id.tv_username);
		
		btn_map = (Button) this.findViewById(R.id.btn_map);
	
		btn_publish = (Button) this.findViewById(R.id.btn_publish);
		
		spinner_personNum = (Spinner) findViewById(R.id.spinner_personNum);
		spinner_howManyDaysAfter = (Spinner) findViewById(R.id.spinner_howManyDaysAfter);

		//绑定UI控件
		// 建立数据源
		String[] mItems = getResources().getStringArray(R.array.spinnername);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
		//绑定 Adapter到控件
		spinner_personNum.setAdapter(_Adapter);
		
		
		// 建立数据源
		String[] mItems1 = getResources().getStringArray(R.array.spinnerafteday);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> _Adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems1);
		//绑定 Adapter到控件
		spinner_howManyDaysAfter.setAdapter(_Adapter1);
		
		//显示用户名
		myStatus = (MyStatus) getApplication();
		tv_username.setText(myStatus.getUsername());
	}

	
	//按下地图按钮
	public void onClick_btn_map(View view){
		
	}
	
	//按下发布按钮
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)//新加的
	public void onClick_btn_publish(View view){
		//填写发车时间
		String hourStr = et_hour.getText().toString().trim();
		String minuteStr = et_minute.getText().toString().trim();
		if(hourStr.isEmpty() || minuteStr.isEmpty()){
			Toast.makeText(this, "请填写发车时间", 0).show();
			return;
		}
		int hour = Integer.parseInt(hourStr);
		int minute = Integer.parseInt(minuteStr);
		
		int weeks1 = bool2int(cb_mon);
		int weeks2 = bool2int(cb_tue);
		int weeks3 = bool2int(cb_wed);
		int weeks4 = bool2int(cb_thu);
		int weeks5 = bool2int(cb_fri);
		int weeks6 = bool2int(cb_sat);
		int weeks7 = bool2int(cb_sun);
		//选择服务时段
		if((weeks1 + weeks2 + weeks3 + weeks4 + weeks5 + weeks6 + weeks7) == 0){
			Toast.makeText(this, "请选择服务时段", 0).show();
			return ;
		}
		
		
		
		//准备其他数据
		int publishAfterDaysNum = spinner_howManyDaysAfter.getSelectedItemPosition() + 1;
		
		int server_num = spinner_personNum.getSelectedItemPosition() + 1;
		
		//两个日期：发布 and 终止
		String time = String.format("%02d:%02d", hour, minute);
		int year = datePicker.getYear();
		int month = datePicker.getMonth();
		int day = datePicker.getDayOfMonth();
		String endDateStr = String.format("%04d-%02d-%02d", year, month + 1, day);
		Date publishDate = new Date(System.currentTimeMillis() + publishAfterDaysNum * 24 * 3600 * 1000);
		String publishDateStr = String.format("%04d-%02d-%02d", publishDate.getYear() + 1900, publishDate.getMonth() + 1, publishDate.getDay());
		
		String brief = et_description.getText().toString();
		final JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("username",myStatus.getUsername());
			jsonObject.put("server_num",server_num);
			jsonObject.put("start_time",time);
			jsonObject.put("publish_date",publishDateStr);
			jsonObject.put("end_date",endDateStr);
			jsonObject.put("weeks1",weeks1);
			jsonObject.put("weeks2",weeks2);
			jsonObject.put("weeks3",weeks3);
			jsonObject.put("weeks4",weeks4);
			jsonObject.put("weeks5",weeks5);
			jsonObject.put("weeks6",weeks6);
			jsonObject.put("weeks7",weeks7);
			jsonObject.put("thetotalprice", 1.00);
			jsonObject.put("brief", brief);

			jsonObject.put("map_begin","");
			jsonObject.put("map_end","");
			jsonObject.put("location","");
			
			new Thread(){
				@Override
				public void run() {
					try {
					final HttpContectionUtil conn = new HttpContectionUtil();
					final String url = myStatus.getUrlString() + "driverpublish.php";
					String responseStr = conn.ConnForResult(url, jsonObject);
						JSONObject response = new JSONObject(responseStr);
						String result = response.getString("result");
						if("success".equals(result)){
							runOnUiThread(new Runnable(){
								@Override
								public void run() {
									Toast.makeText(From.this, "发布成功", 0).show();
									From.this.finish();
								}
							});
						}else if("exist".equals(result)){
							runOnUiThread(new Runnable(){
								@Override
								public void run(){
									Toast.makeText(From.this, "线路已存在", 0).show();
								}
							});
						}
					} catch (Exception e) {
						return;
					}
				}
			}.start();
		} catch (Exception e) {
			return;
		}
	}
	
	private int bool2int(CheckBox checkBox){
		if(checkBox.isChecked()){
			return 1;
		}else{
			return 0;
		}
	}

}
