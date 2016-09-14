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

	private String[] areas = new String[] { "˾��", "�˿�", "���߶���" };
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
		confirm.setOnClickListener(new Button.OnClickListener() {// ��������

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

				// �û�������Ϊ��
				if (username.trim().isEmpty()) {
					Toast.makeText(Register.this, "�û�������Ϊ��", 0).show();
					return;
				}

				// ���벻��Ϊ��
				if (password.isEmpty()) {
					Toast.makeText(Register.this, "���벻��Ϊ��", 0).show();
					return;
				}

				// �����������
				if (!password.equals(password1)) {
					Toast.makeText(Register.this, "�����������벻���", 0).show();
					return;
				}

				// �ֻ��Ų��Ϸ�
				if (phonenumber.length() < 11) {
					Toast.makeText(Register.this, "�ֻ��Ų��Ϸ�", 0).show();
					return;
				}

				// ��װjson
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
						
						// ���÷��ʷ������ĵ�ַ
						String uri = myStatus.getUrlString() + "register.php";
						try {
							//�õ����ؽ��
							String responseStr = conn.ConnForResult(uri, jsonObj);
							JSONObject response = new JSONObject(responseStr);
							String result = response.getString("result");
							if ("exist".equals(result)) {
								runOnUiThread(new Runnable(){
									public void run(){
										Toast.makeText(Register.this, "�û����Ѵ���", 0).show();
									}
								});
							} else if("failed".equals(result)){
								runOnUiThread(new Runnable(){
									public void run(){
								Toast.makeText(Register.this, "ע��ʧ��", 0).show();
									}
								});
							}else{
								runOnUiThread(new Runnable(){
									public void run(){
								Toast.makeText(Register.this, "ע��ɹ�", 0).show();
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
		// ����ID�ҵ�RadioGroupʵ��
		RadioGroup group = (RadioGroup) this.findViewById(R.id.radioGroup);
		// ��һ������������
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// ��ȡ������ѡ�����ID
				int radioButtonId = arg0.getCheckedRadioButtonId();
				// ����ID��ȡRadioButton��ʵ��
				RadioButton rb = (RadioButton) Register.this
						.findViewById(radioButtonId);
				// �����ı����ݣ��Է���ѡ����
				if (arg1 == R.id.radioMale) {
					charsex = 1;
				} else {
					charsex = 0;
				}

				Toast.makeText(Register.this, "�����Ա��ǣ�" + rb.getText(),
						Toast.LENGTH_LONG).show();

			}
		});
	}

	class ListListener implements OnClickListener {
		public void onClick(View v) {
			AlertDialog ad = new AlertDialog.Builder(Register.this)
					.setTitle("ѡ���ɫ")
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
					"���Ѿ�ѡ���ˣ� " + index + ":" + areas[index], Toast.LENGTH_LONG)
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
