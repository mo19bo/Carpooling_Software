package com.example.carpool.adt;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo {
	private String username;
	private String password;
	private String phonenumber;
	private int sex;
	private String introduction;
	private String driverlicense;
	private String idcard;
	private int isdriver;
	private int iscustomer;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getDriverlicense() {
		return driverlicense;
	}

	public void setDriverlicense(String driverlicense) {
		this.driverlicense = driverlicense;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public int getIsdriver() {
		return isdriver;
	}

	public void setIsdriver(int isdriver) {
		this.isdriver = isdriver;
	}

	public int getIscustomer() {
		return iscustomer;
	}

	public void setIscustomer(int iscustomer) {
		this.iscustomer = iscustomer;
	}

	public static UserInfo parseJsonObj(String jsonStr) {
		try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			UserInfo userInfo = new UserInfo();

			userInfo.setDriverlicense(jsonObj.getString("driverlicense"));
			userInfo.setIdcard(jsonObj.getString("idcard"));
			userInfo.setIntroduction(jsonObj.getString("introduction"));
			userInfo.setIscustomer(jsonObj.getInt("iscustomer"));
			userInfo.setIsdriver(jsonObj.getInt("isdriver"));
			userInfo.setPassword(jsonObj.getString("password"));
			userInfo.setPhonenumber(jsonObj.getString("phonenumber"));
			userInfo.setSex(jsonObj.getInt("sex"));
			userInfo.setUsername(jsonObj.getString("username"));

			return userInfo;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String getJsonStr() {
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("username", username);
			jsonObj.put("password", password);
			jsonObj.put("phonenumber", phonenumber);
			jsonObj.put("sex", sex);
			jsonObj.put("introduction", introduction);
			jsonObj.put("driverlicense", driverlicense);
			jsonObj.put("idcard", idcard);
			jsonObj.put("isdriver", isdriver);
			jsonObj.put("iscustomer", iscustomer);
			return jsonObj.toString();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
