package com.example.carpool;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carsharing.R;
import com.example.http.HttpContectionUtil;

public class Register extends Activity {
	public int isdriver = 0;
	public int iscustomer = 0;
	public int charsex;
	public String phonenumber;
	public String password;
	public String password1;
	public String username;
	public String Introduction;

	private String[] areas = new String[] { "司机", "乘客", "两者都是" };
	private boolean[] areaState = new boolean[] { true, false, false };
	private RadioOnClick radioOnClick = new RadioOnClick(1);
	TextView sex = null;

	private Button listButton;
	private Button confirm;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		listButton = (Button) findViewById(R.id.list);
		confirm = (Button) findViewById(R.id.confirm);
		// radioButton=(Button)findViewById(R.id.radioButton);
		confirm.setOnClickListener(new Button.OnClickListener() {// 创建监听

			@Override
			public void onClick(View arg0) {
				username = ((EditText) findViewById(R.id.editText1)).getText()
						.toString();
				password = ((EditText) findViewById(R.id.inputPassword))
						.getText().toString();
				password1 = ((EditText) findViewById(R.id.editText3)).getText()
						.toString();
				phonenumber = ((EditText) findViewById(R.id.editText4))
						.getText().toString();
				Introduction = ((EditText) findViewById(R.id.editText6))
						.getText().toString();

				// 用户名不能为空
				if (username.trim().isEmpty()) {
					Toast.makeText(Register.this, "用户名不能为空", 0).show();
					return;
				}

				// 密码不能为空
				if (password.isEmpty()) {
					Toast.makeText(Register.this, "密码不能为空", 0).show();
					return;
				}

				// 两次密码相符
				if (!password.equals(password1)) {
					Toast.makeText(Register.this, "两次输入密码不相符", 0).show();
					return;
				}

				// 手机号不合法
				if (phonenumber.length() < 11) {
					Toast.makeText(Register.this, "手机号不合法", 0).show();
					return;
				}

				// 组装json
				final JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("username", username);
					jsonObj.put("password", password);
					jsonObj.put("phonenumber", phonenumber);
					jsonObj.put("sex", charsex);
					jsonObj.put("introduction", Introduction);
					jsonObj.put("driverlicense", "");
					jsonObj.put("idcard", "");
					jsonObj.put("isdriver", isdriver);
					jsonObj.put("iscustomer", iscustomer);

				} catch (JSONException e) {
					e.printStackTrace();
				}

				new Thread(){
					public void run(){
						HttpContectionUtil conn = new HttpContectionUtil();
						final MyStatus myStatus = new MyStatus();
						
						// 设置访问服务器的地址
						String uri = myStatus.getUrlString() + "register.php";
						try {
							//拿到返回结果
							String responseStr = conn.ConnForResult(uri, jsonObj);
							JSONObject response = new JSONObject(responseStr);
							String result = response.getString("result");
							if ("exist".equals(result)) {
								runOnUiThread(new Runnable(){
									public void run(){
										Toast.makeText(Register.this, "用户名已存在", 0).show();
									}
								});
							} else if("failed".equals(result)){
								runOnUiThread(new Runnable(){
									public void run(){
								Toast.makeText(Register.this, "注册失败", 0).show();
									}
								});
							}else{
								runOnUiThread(new Runnable(){
									public void run(){
								Toast.makeText(Register.this, "注册成功", 0).show();
									}
								});
								Register.this.finish();
							}
						} catch (Exception e) {
							return;
						}
					}
				}.start();

				// tag = YQConServer.register(obj);
				// if (tag == true){
				// Intent intent = new Intent();
				// intent.setClass(register.this, MainActivity.class);
				// startActivity(intent);
				// }
				// else{
				// Toast.makeText(register.this, "fuck", 1000);
				// }

			}
		});
		listButton.setOnClickListener(new ListListener());
		// sex = (TextView)this.findViewById(R.id.radioGroup);
		// 根据ID找到RadioGroup实例
		RadioGroup group = (RadioGroup) this.findViewById(R.id.radioGroup);
		// 绑定一个匿名监听器
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// 获取变更后的选中项的ID
				int radioButtonId = arg0.getCheckedRadioButtonId();
				// 根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton) Register.this
						.findViewById(radioButtonId);
				// 更新文本内容，以符合选中项
				if (arg1 == R.id.radioMale) {
					charsex = 1;
				} else {
					charsex = 0;
				}

				Toast.makeText(Register.this, "您的性别是：" + rb.getText(),
						Toast.LENGTH_LONG).show();

			}
		});
	}

	class ListListener implements OnClickListener {
		public void onClick(View v) {
			AlertDialog ad = new AlertDialog.Builder(Register.this)
					.setTitle("选择角色")
					.setSingleChoiceItems(areas, radioOnClick.getIndex(),
							radioOnClick).create();
			ad.getListView();
			ad.show();
		}
	}

	class RadioOnClick implements DialogInterface.OnClickListener {
		private int index;

		public RadioOnClick(int index) {
			this.index = index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public void onClick(DialogInterface dialog, int whichButton) {
			setIndex(whichButton);
			Toast.makeText(Register.this,
					"您已经选择了： " + index + ":" + areas[index], Toast.LENGTH_LONG)
					.show();
			switch (index) {
			case 0:
				isdriver = 1;
				break;
			case 1:
				iscustomer = 1;
				break;
			case 2:
				isdriver = 1;
				iscustomer = 1;
			}
			dialog.dismiss();
		}
	}

}
