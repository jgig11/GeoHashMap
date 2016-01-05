package com.ucar.util;

import java.util.ArrayList;
import java.util.List;

import com.ucar.common.Constant;
import com.ucar.vo.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.operation.distance.DistanceOp;
import com.vividsolutions.jts.operation.distance.GeometryLocation;

public class MathCalc {

	private static MathCalc mathCalc = new MathCalc();

	private MathCalc() {

	}

	public static MathCalc getInstance() {
		return mathCalc;
	}

	/**
	 * 获取图上两点直线距离
	 * 
	 * @param pStart
	 * @param pEnd
	 * @return
	 */
	public double getDistance(Point pStart, Point pEnd) {

		double rStartLat = pStart.getLatitude() * Math.PI / 180.0;
		double rStartLon = pStart.getLongitude() * Math.PI / 180.0;

		double rEndLat = pEnd.getLatitude() * Math.PI / 180.0;
		double rEndLon = pEnd.getLongitude() * Math.PI / 180.0;

		double ec = Constant.RJ + (Constant.RC - Constant.RJ) * (90.0 - pStart.getLatitude()) / 90.0;
		double ed = ec * Math.cos(rStartLat);
		double dx = (rEndLon - rStartLon) * ed;
		double dy = (rEndLat - rStartLat) * ec;
		return Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * 直线外一点到该直线的投影�?
	 * @param pStart
	 * @param pEnd
	 * @param pPosition
	 * @return
	 */
	public Point getProjectivePoint(Point pStart, Point pEnd, Point pPosition) {
		
		List<Point> listPoint = new ArrayList<Point>();
		listPoint.add(pStart);
		listPoint.add(pEnd);

		GeometryFactory gf = new GeometryFactory();

		Coordinate[] arrCoord = TypeChange.getInstance().listPointToArrCoord(listPoint, listPoint.size());
		LineString lineString = gf.createLineString(arrCoord);

		Coordinate coordPosition = TypeChange.getInstance().pointToCoord(pPosition);
		com.vividsolutions.jts.geom.Point pt = gf.createPoint(coordPosition);
		GeometryLocation[] gls = new DistanceOp(lineString, pt).nearestLocations(); // [线上垂点,

		Point pProject = TypeChange.getInstance().coordToPoint(gls[0].getCoordinate());
		
		return pProject;
	}

}
