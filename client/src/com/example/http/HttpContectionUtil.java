package com.example.http;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.StrictMode;

public class HttpContectionUtil {
	// 连接服务器并得到返回的数据
	public String ConnForResult(String url, String str) {
		HttpClient hc = new DefaultHttpClient();
		HttpPost hp = new HttpPost(url);
		HttpParams params = hc.getParams();
		String resultString = "failed";

		try {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("json", str));
			hp.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
			//设置连接超时、读取超时
//			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 300);
//			params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 200);
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 20000);
			HttpResponse response = hc.execute(hp);
			if(response != null){
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					resultString = EntityUtils.toString(response.getEntity());
				} else {					
					return "failed";
				}
			}else {
				return "failed";
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//return "failed";
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultString;
	}
	
	public String ConnForResult(String url, JSONObject json) {
		HttpClient hc = new DefaultHttpClient();
		HttpPost hp = new HttpPost(url);
		HttpParams params = hc.getParams();
		String resultString = "failed";

		try {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("json", json.toString()));
			hp.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
			//设置连接超时、读取超时
//			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 300);
//			params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 200);
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 20000);
			HttpResponse response = hc.execute(hp);
			if(response != null){
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					resultString = EntityUtils.toString(response.getEntity());
				} else {					
					return "failed";
				}
			}else {
				return "failed";
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//return "failed";
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultString;
	}
	


	public static void start() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectAll().penaltyLog()
				.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
	}
}
