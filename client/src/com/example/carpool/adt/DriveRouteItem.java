package com.example.carpool.adt;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriveRouteItem {
	private int pid;
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

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
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

	public static List<DriveRouteItem> parseJsonArray(String jsonStr) {
		List<DriveRouteItem> driveItemsList = new ArrayList<DriveRouteItem>();
		try {
			JSONArray driveItems = new JSONArray(jsonStr);
			for (int i = 0; i < driveItems.length(); i++) {
				JSONObject jsonObj = driveItems.getJSONObject(i);

				DriveRouteItem driveItem = new DriveRouteItem();
				driveItem.setPid(jsonObj.getInt("pid"));
				driveItem.setServer_num(jsonObj.getInt("server_num"));
				driveItem.setStart_time(jsonObj.getString("start_time"));
				driveItem.setWeeks1(jsonObj.getInt("weeks1"));
				driveItem.setWeeks2(jsonObj.getInt("weeks2"));
				driveItem.setWeeks3(jsonObj.getInt("weeks3"));
				driveItem.setWeeks4(jsonObj.getInt("weeks4"));
				driveItem.setWeeks5(jsonObj.getInt("weeks5"));
				driveItem.setWeeks6(jsonObj.getInt("weeks6"));
				driveItem.setWeeks7(jsonObj.getInt("weeks7"));
				driveItem.setPublish_date(jsonObj.getString("publish_date"));
				driveItem.setEnd_date(jsonObj.getString("end_date"));
				driveItem.setThetotalprice(jsonObj.getDouble("thetotalprice"));
				driveItem.setBrief(jsonObj.getString("brief"));
				driveItem.setLocation(jsonObj.getString("location"));
				driveItem.setMap_begin(jsonObj.getString("map_begin"));
				driveItem.setMap_end(jsonObj.getString("map_end"));

				driveItemsList.add(driveItem);
			}
			return driveItemsList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String toJsonStr() {
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("pid", this.getPid());
			jsonObj.put("server_num", this.getServer_num());
			jsonObj.put("start_time", this.getStart_time());
			jsonObj.put("weeks1", this.getWeeks1());
			jsonObj.put("weeks2", this.getWeeks2());
			jsonObj.put("weeks3", this.getWeeks3());
			jsonObj.put("weeks4", this.getWeeks4());
			jsonObj.put("weeks5", this.getWeeks5());
			jsonObj.put("weeks6", this.getWeeks6());
			jsonObj.put("weeks7", this.getWeeks7());
			jsonObj.put("publish_date", this.getPublish_date());
			jsonObj.put("end_date", this.getEnd_date());
			jsonObj.put("thetotalprice", this.getThetotalprice());
			jsonObj.put("brief", this.getBrief());
			jsonObj.put("location", this.getLocation());
			jsonObj.put("map_begin", this.getMap_begin());
			jsonObj.put("map_end", this.getMap_end());
			return jsonObj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static DriveRouteItem parseJsonObj(String jsonStr) {
		try {
			JSONObject jsonObj = new JSONObject(jsonStr);

			DriveRouteItem driveItem = new DriveRouteItem();
			driveItem.setPid(jsonObj.getInt("pid"));
			driveItem.setServer_num(jsonObj.getInt("server_num"));
			driveItem.setStart_time(jsonObj.getString("start_time"));
			driveItem.setWeeks1(jsonObj.getInt("weeks1"));
			driveItem.setWeeks2(jsonObj.getInt("weeks2"));
			driveItem.setWeeks3(jsonObj.getInt("weeks3"));
			driveItem.setWeeks4(jsonObj.getInt("weeks4"));
			driveItem.setWeeks5(jsonObj.getInt("weeks5"));
			driveItem.setWeeks6(jsonObj.getInt("weeks6"));
			driveItem.setWeeks7(jsonObj.getInt("weeks7"));
			driveItem.setPublish_date(jsonObj.getString("publish_date"));
			driveItem.setEnd_date(jsonObj.getString("end_date"));
			driveItem.setThetotalprice(jsonObj.getDouble("thetotalprice"));
			driveItem.setBrief(jsonObj.getString("brief"));
			driveItem.setLocation(jsonObj.getString("location"));
			driveItem.setMap_begin(jsonObj.getString("map_begin"));
			driveItem.setMap_end(jsonObj.getString("map_end"));

			return driveItem;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
