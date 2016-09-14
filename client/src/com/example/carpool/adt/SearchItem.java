package com.example.carpool.adt;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchItem {
	//[{"pid":"1","username":"test1","server_num":"0","start_time":"4:00","weeks1":"1","weeks2":"0","weeks3":"0","weeks4":"0","weeks5":"0","weeks6":"0","weeks7":"0","publish_date":"2014-11-13","end_date":"2014-11-14","thetotalprice":"11.11","brief":"hello","location":"111","map_begin":"222","map_end":"333","isgo":"0","sum":"1"}]
	private int pid;
	private String username;
	private int server_num;
	private String start_time;
	private int weeks1;
	private int weeks2;
	private int weeks3;
	private int weeks4;
	private int weeks5;
	private int weeks6;
	private int weeks7;
	private String publish_date;
	private String end_date;
	private double thetotalprice;
	private String brief;
	private String location;
	private String map_begin;
	private String map_end;
	private int isgo;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getServer_num() {
		return server_num;
	}
	public void setServer_num(int server_num) {
		this.server_num = server_num;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
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
	public String getPublish_date() {
		return publish_date;
	}
	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public double getThetotalprice() {
		return thetotalprice;
	}
	public void setThetotalprice(double thetotalprice) {
		this.thetotalprice = thetotalprice;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	public int getIsgo() {
		return isgo;
	}
	public void setIsgo(int isgo) {
		this.isgo = isgo;
	}
	public static List<SearchItem> parseJsonArray(String jsonStr){
		List<SearchItem> searchItemsList = new ArrayList<SearchItem>();
		try {
			JSONArray searchItems = new JSONArray(jsonStr);
			for (int i = 0; i < searchItems.length(); i++) {
				JSONObject jsonObj = searchItems.getJSONObject(i);
				//[{"pid":"1","username":"test1","server_num":"0","start_time":"4:00","weeks1":"1","weeks2":"0","weeks3":"0","weeks4":"0","weeks5":"0","weeks6":"0","weeks7":"0","publish_date":"2014-11-13","end_date":"2014-11-14","thetotalprice":"11.11","brief":"hello","location":"111","map_begin":"222","map_end":"333","isgo":"0","sum":"1"}]
				SearchItem searchItem = new SearchItem();
				searchItem.setPid(jsonObj.getInt("pid"));
				searchItem.setUsername(jsonObj.getString("username"));
				searchItem.setServer_num(jsonObj.getInt("server_num"));
				searchItem.setStart_time(jsonObj.getString("start_time"));
				searchItem.setWeeks1(jsonObj.getInt("weeks1"));
				searchItem.setWeeks2(jsonObj.getInt("weeks2"));
				searchItem.setWeeks3(jsonObj.getInt("weeks3"));
				searchItem.setWeeks4(jsonObj.getInt("weeks4"));
				searchItem.setWeeks5(jsonObj.getInt("weeks5"));
				searchItem.setWeeks6(jsonObj.getInt("weeks6"));
				searchItem.setWeeks7(jsonObj.getInt("weeks7"));
				searchItem.setPublish_date(jsonObj.getString("publish_date"));
				searchItem.setEnd_date(jsonObj.getString("end_date"));
				searchItem.setThetotalprice(jsonObj.getDouble("thetotalprice"));
				searchItem.setBrief(jsonObj.getString("brief"));
				searchItem.setLocation(jsonObj.getString("location"));
				searchItem.setIsgo(jsonObj.getInt("isgo"));
				searchItem.setMap_begin(jsonObj.getString("map_begin"));
				searchItem.setMap_end(jsonObj.getString("map_end"));

				searchItemsList.add(searchItem);
			}
			return searchItemsList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
