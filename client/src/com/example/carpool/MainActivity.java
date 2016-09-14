package com.example.carpool;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carsharing.R;
import com.example.http.HttpContectionUtil;

public class MainActivity extends Activity {
	private EditText et_username;
	private EditText et_password;
	public static String username;
	MyStatus myStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ��ȡ��ť��Դ
		Button login = (Button) findViewById(R.id.login);
		Button register = (Button) findViewById(R.id.register);
		et_username = (EditText) findViewById(R.id.editText1);
		et_password = (EditText) findViewById(R.id.editText2);

		login.setOnClickListener(new Button.OnClickListener() {// ��������

			@Override
			public void onClick(View arg0) {
				String account = ((EditText) findViewById(R.id.editText1))
						.getText().toString();
				String password = ((EditText) findViewById(R.id.editText2))
						.getText().toString();
				login(account, password);
			}

		});

		register.setOnClickListener(new Button.OnClickListener() {// ��������

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Register.class);
				startActivity(intent);
			}
		});
	}

	// ��½
	public void login(final String account, final String password) {
		// ��������ַ��������������������������������������������������
		// �������failed��Ϊ��½ʧ��
		
		new Thread() {
			@Override
			public void run() {
				final JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("username", account);
					jsonObj.put("password", password);
					HttpContectionUtil conn = new HttpContectionUtil();
					myStatus = (MyStatus) getApplication();
					String uri = myStatus.getUrlString() + "login.php";
					String responseStr = conn.ConnForResult(uri, jsonObj);
					JSONObject response = new JSONObject(responseStr);
					String result = response.getString("result");
					if ("failed".equals(result)) {
						runOnUiThread(new Runnable(){
							public void run(){
								Toast.makeText(MainActivity.this, "Login Failed", 0).show();
							}
						});
					} else {
						runOnUiThread(new Runnable(){
							public void run(){
								Toast.makeText(MainActivity.this, "Login Success", 0).show();
							}
						});
						// �������浽ȫ�ֱ�����
						JSONObject userObj = new JSONObject(responseStr);
						myStatus.setUsername(userObj.getString("username"));
						myStatus.setPhonenumber(userObj
								.getString("phonenumber"));
						myStatus.setSex(userObj.getInt("sex"));
						myStatus.setIntroduction(userObj
								.getString("introduction"));
						myStatus.setDriverlicense(userObj
								.getString("driverlicense"));
						myStatus.setIdcard(userObj.getString("idcard"));
						myStatus.setIsdriver(userObj.getInt("isdriver"));
						myStatus.setIscustomer(userObj.getInt("iscustomer"));
						myStatus.setDrivercredit((float) userObj.getDouble("asdriverrate"));
						myStatus.setPassengercredit((float) userObj.getDouble("aspassenger"));
						myStatus.setJob(userObj.getString("job"));
						// ���뵽4��ѡ���ҳ��
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, act1.class);
						startActivity(intent);
					}
				} catch (Exception e) {
					runOnUiThread(new Runnable(){
						public void run(){
							Toast.makeText(MainActivity.this, "Login Failed", 0).show();
						}
					});
				}
			}
		}.start();
	}

	private void register() {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, Register.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// ��½��ť�ĵ���¼�
	public void onClick_login(View view) {
		String username = et_username.getText().toString().trim();
		String password = et_username.getText().toString();

		login(username, password);
	}

	public void onClick_register(View view) {
		register();
	}
}
