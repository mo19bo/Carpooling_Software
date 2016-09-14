package com.example.carpool.adt;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;

/**
 * 用户管理中订单管理的便捷类
 * @author 1
 *
 */
public class OrderItem {
	private int weeks1;
	private int weeks2;
	private int weeks3;
	private int weeks4;
	private int weeks5;
	private int weeks6;
	public int getWeeks1() {
		return weeks1;
	}
	public void setWeeks1(int weeks1) {
		this.weeks1 = weeks1;
	}
	public int getWeeks2() {
		return weeks2;
	}
	public void setWeeks2(int weeks2) {
		this.weeks2 = weeks2;
	}
	public int getWeeks3() {
		return weeks3;
	}
	public void setWeeks3(int weeks3) {
		this.weeks3 = weeks3;
	}
	public int getWeeks4() {
		return weeks4;
	}
	public void setWeeks4(int weeks4) {
		this.weeks4 = weeks4;
	}
	public int getWeeks5() {
		return weeks5;
	}
	public void setWeeks5(int weeks5) {
		this.weeks5 = weeks5;
	}
	public int getWeeks6() {
		return weeks6;
	}
	public void setWeeks6(int weeks6) {
		this.weeks6 = weeks6;
	}
	public int getWeeks7() {
		return weeks7;
	}
	public void setWeeks7(int weeks7) {
		this.weeks7 = weeks7;
	}
	private int opid;
	public int getOpid() {
		return opid;
	}
	public void setOpid(int opid) {
		this.opid = opid;
	}
	private int weeks7;
	private String brief;
	private String start_time;
	private String map_begin;
	private String map_end;
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getMap_begin() {
		return map_begin;
	}
	public void setMap_begin(String map_begin) {
		this.map_begin = map_begin;
	}
	public String getMap_end() {
		return map_end;
	}
	public void setMap_end(String map_end) {
		this.map_end = map_end;
	}
	public static List<OrderItem> parseJsonArray(String jsonStr){
		List<OrderItem> orderItemsList = new ArrayList<OrderItem>();
		try{
			JSONArray orderItems = new JSONArray(jsonStr);
			int count = Integer.parseInt(orderItems.getJSONObject(0).getString("sum"));
			if(count == 0){
				return null;
			}
			for(int i=0;i<orderItems.length();i++){
				JSONObject jsonObj = orderItems.getJSONObject(i);
				
				OrderItem orderItem = new OrderItem();
				orderItem.setBrief(jsonObj.getString("brief"));
				orderItem.setMap_begin(jsonObj.getString("map_begin"));
				orderItem.setMap_end(jsonObj.getString("map_end"));
				orderItem.setStart_time(jsonObj.getString("start_time"));
				orderItem.setWeeks1(jsonObj.getInt("weeks1"));
				orderItem.setWeeks2(jsonObj.getInt("weeks2"));
				orderItem.setWeeks3(jsonObj.getInt("weeks3"));
				orderItem.setWeeks4(jsonObj.getInt("weeks4"));
				orderItem.setWeeks5(jsonObj.getInt("weeks5"));
				orderItem.setWeeks6(jsonObj.getInt("weeks6"));
				orderItem.setWeeks7(jsonObj.getInt("weeks7"));
				orderItem.setOpid(jsonObj.getInt("opid"));
				
				orderItemsList.add(orderItem);
			}
			return orderItemsList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
