package com.ucar.test;

import java.util.ArrayList;
import java.util.List;

import com.ucar.common.Constant;
import com.ucar.util.GeoHashUtil;
import com.ucar.util.TypeChange;
import com.ucar.util.geohash.BoundingBox;
import com.ucar.util.geohash.GeoHash;
import com.ucar.vo.Point;


public class MainClass {
	
	public static void main(String[] args) {
		test();
	}

	private static void test(){
		Point point = new Point(116.383851, 39.968242);
		List<String> listRes = GeoHashUtil.getInstance().getGeoHashs(point, Constant.DISTANCE);
		
	
		StringBuilder sb = new StringBuilder();
		for(int i = 0, len = listRes.size(); i < len; i++){
			sb.append(listRes.get(i)).append(",");
		}
		
		System.out.println(sb.toString());
		
		
		List<Point> points = new ArrayList<Point>();
		for(int i = 0, len = listRes.size(); i < len; i++){
			String strGeo = listRes.get(i);
			GeoHash geo = GeoHash.fromGeohashString(strGeo);
			BoundingBox boundingBox = geo.getBoundingBox();
			points.add(new Point(boundingBox.getMaxLon(), boundingBox.getMaxLat()));
			points.add(new Point(boundingBox.getMaxLon(), boundingBox.getMinLat()));
			points.add(new Point(boundingBox.getMinLon(), boundingBox.getMaxLat()));
			points.add(new Point(boundingBox.getMinLon(), boundingBox.getMinLat()));
		}
		
		String str = TypeChange.getInstance().listPointToString(points);
		
		System.out.println(str);
		
	}
	

}
