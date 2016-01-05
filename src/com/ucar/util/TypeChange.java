package com.ucar.util;

import java.util.ArrayList;
import java.util.List;

import com.ucar.vo.Point;
import com.vividsolutions.jts.geom.Coordinate;

public class TypeChange {

	private static TypeChange typeChange = new TypeChange();

	private TypeChange() {

	}

	public static TypeChange getInstance() {
		return typeChange;
	}

	/**
	 * List<Point> to String
	 * 
	 * @param listPoint
	 * @return
	 */
	public String listPointToString(List<Point> listPoint) {
		StringBuilder sbPoints = new StringBuilder();
		try {
			for (Point point : listPoint) {
				sbPoints.append(this.pointToString(point)).append(",");
			}
		} catch (Exception ex) {
		}
		return sbPoints.toString();
	}
	
	/**
	 * Point to String
	 * 
	 * @param point
	 * @return
	 */
	public String pointToString(Point point) {
		StringBuilder sbPoint = new StringBuilder();
		try {
			double dLon = point.getLongitude();
			dLon = ((long) (dLon * 1e6)) / 1.0e6;

			double dLat = point.getLatitude();
			dLat = ((long) (dLat * 1e6)) / 1.0e6;

			sbPoint.append(dLon).append(",").append(dLat);
		} catch (Exception ex) {

		}
		return sbPoint.toString();
	}
	
	/**
	 * 
	 * @param listPoint
	 * @param listSize
	 * @return
	 */
	public Coordinate[] listPointToArrCoord(List<Point> listPoint, int listSize) {
		Coordinate[] arrCoord = new Coordinate[listSize];
		Point point = null;
		for (int i = 0; i < listSize; i++) {
			point = listPoint.get(i);
			Coordinate coord = new Coordinate(point.getLongitude(), point.getLatitude());
			arrCoord[i] = coord;
		}
		return arrCoord;
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	public Coordinate pointToCoord(Point point) {
		if (null == point){
			return null;
		}
		return new Coordinate(point.getLongitude(), point.getLatitude());
	}
	
	/**
	 * 
	 * @param coord
	 * @return
	 */
	public Point coordToPoint(Coordinate coord) {
		if (null == coord){
			return null;
		}
		return new Point(coord.x, coord.y);
	}
}
