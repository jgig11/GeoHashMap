package com.ucar.vo;

/**
 * 点
 * @author dl.zhao
 *
 */
public class Point{
	
	
	private double longitude = 0;         //经度	
	private double latitude = 0;          //纬度
	
	public Point(){
	}
	
	public Point(double _longitude, double _latitude)
	{
		this.longitude = _longitude;
		this.latitude = _latitude;
	}
	

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
}
