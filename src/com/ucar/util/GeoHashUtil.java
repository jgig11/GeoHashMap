package com.ucar.util;

import java.util.ArrayList;
import java.util.List;

import com.ucar.common.Constant;
import com.ucar.util.geohash.BoundingBox;
import com.ucar.util.geohash.GeoHash;
import com.ucar.vo.Point;

public class GeoHashUtil {

	private static GeoHashUtil geoHashUtil = new GeoHashUtil();

	private GeoHashUtil() {

	}

	public static GeoHashUtil getInstance() {
		return geoHashUtil;
	}

	private MathCalc mathCalc = MathCalc.getInstance();

	/**
	 * 
	 * @param point
	 * @param distance
	 * @return
	 */
	public List<String> getGeoHashs(Point point, int distance) {

		GeoHash geoHash = GeoHash.withCharacterPrecision(point.getLatitude(), point.getLongitude(), Constant.NO_OF_CHARACTERS);

		GeoHashUtil geoHashTest = new GeoHashUtil();
		List<String> listRes = new ArrayList<String>();

		listRes.add(geoHash.toBase32());

		GeoHash geoNorth = geoHash.getNorthernNeighbour(); // 北
		GeoHash geoEast = geoHash.getEasternNeighbour(); // 东
		GeoHash geoSouth = geoHash.getSouthernNeighbour(); // 南
		GeoHash geoWest = geoHash.getWesternNeighbour(); // 西

		GeoHash geoNorthEast = geoNorth.getEasternNeighbour(); // 东北
		GeoHash geoNorthWest = geoNorth.getWesternNeighbour(); // 西北

		GeoHash geoSouthEath = geoSouth.getEasternNeighbour(); // 东南
		GeoHash geoSouthWest = geoSouth.getWesternNeighbour(); // 西南

		geoHashTest.getNeighbor(listRes, point, distance, Constant.NORTH, geoNorth, true);
		geoHashTest.getNeighbor(listRes, point, distance, Constant.NORTH_EAST, geoNorthEast, true);
		geoHashTest.getNeighbor(listRes, point, distance, Constant.EAST, geoEast, true);
		geoHashTest.getNeighbor(listRes, point, distance, Constant.SOUTH_EAST, geoSouthEath, true);

		geoHashTest.getNeighbor(listRes, point, distance, Constant.SOUTH, geoSouth, true);
		geoHashTest.getNeighbor(listRes, point, distance, Constant.SOUTH_WEST, geoSouthWest, true);
		geoHashTest.getNeighbor(listRes, point, distance, Constant.WEST, geoWest, true);
		geoHashTest.getNeighbor(listRes, point, distance, Constant.NORTH_WEST, geoNorthWest, true);

		return listRes;
	}

	private void getNeighbor(List<String> listRes, Point pOriginal, int distance, int direction, GeoHash geoHash, boolean isAdd) {

		BoundingBox boundingBox = geoHash.getBoundingBox();

		Point pNorthEast = new Point(boundingBox.getMaxLon(), boundingBox.getMaxLat());
		Point pNorthWest = new Point(boundingBox.getMinLon(), boundingBox.getMaxLat());
		Point pSouthEast = new Point(boundingBox.getMaxLon(), boundingBox.getMinLat());
		Point pSouthWest = new Point(boundingBox.getMinLon(), boundingBox.getMinLat());

		double disMin = distance + 1;
		double disMax = distance + 1;

		switch (direction) {
		case Constant.EAST:
			disMax = this.calcGeoHash(listRes, pOriginal, pNorthEast, pSouthEast, pNorthWest, pSouthWest, distance, direction, geoHash, isAdd);
			if (disMax < distance) {
				this.getNeighbor(listRes, pOriginal, distance, direction, geoHash.getEasternNeighbour(), true);
			}
			break;
		case Constant.SOUTH_EAST:
			disMax = this.mathCalc.getDistance(pOriginal, pSouthEast);
			disMin = this.mathCalc.getDistance(pOriginal, pNorthWest);
			if (disMin < distance) {
				listRes.add(geoHash.toBase32());
				if (disMax < distance) {
					GeoHash geoHashSouth = geoHash.getSouthernNeighbour();
					this.getNeighbor(listRes, pOriginal, distance, Constant.SOUTH, geoHashSouth, true); // 南
					this.getNeighbor(listRes, pOriginal, distance, Constant.EAST, geoHash.getEasternNeighbour(), true); // 东
					this.getNeighbor(listRes, pOriginal, distance, Constant.SOUTH_EAST, geoHashSouth.getEasternNeighbour(), true); // 东南
				} else {
					disMax = this.mathCalc.getDistance(pOriginal, pNorthEast);
					if (disMax < distance) {
						this.getNeighbor(listRes, pOriginal, distance, Constant.EAST, geoHash.getEasternNeighbour(), true); // 东
					}
					disMax = this.mathCalc.getDistance(pOriginal, pSouthWest);
					if (disMax < distance) {
						this.getNeighbor(listRes, pOriginal, distance, Constant.SOUTH, geoHash.getSouthernNeighbour(), true); // 南
					}
				}
			}
			break;
		case Constant.SOUTH:
			disMax = this.calcGeoHash(listRes, pOriginal, pSouthEast, pSouthWest, pNorthEast, pNorthWest, distance, direction, geoHash, isAdd);
			if (disMax < distance) {
				this.getNeighbor(listRes, pOriginal, distance, direction, geoHash.getSouthernNeighbour(), true);
			}
			break;
		case Constant.SOUTH_WEST:
			disMax = this.mathCalc.getDistance(pOriginal, pSouthWest);
			disMin = this.mathCalc.getDistance(pOriginal, pNorthEast);
			if (disMin < distance) {
				listRes.add(geoHash.toBase32());
				if (disMax < distance) {
					GeoHash geoHashSouth = geoHash.getSouthernNeighbour();
					this.getNeighbor(listRes, pOriginal, distance, Constant.SOUTH, geoHashSouth, true); // 南
					this.getNeighbor(listRes, pOriginal, distance, Constant.WEST, geoHash.getWesternNeighbour(), true); // 西
					this.getNeighbor(listRes, pOriginal, distance, Constant.SOUTH_WEST, geoHashSouth.getWesternNeighbour(), true); // 西南
				} else {
					disMax = this.mathCalc.getDistance(pOriginal, pSouthEast);
					if (disMax < distance) {
						this.getNeighbor(listRes, pOriginal, distance, Constant.SOUTH, geoHash.getSouthernNeighbour(), true); // 南
					}
					disMax = this.mathCalc.getDistance(pOriginal, pNorthWest);
					if (disMax < distance) {
						this.getNeighbor(listRes, pOriginal, distance, Constant.WEST, geoHash.getWesternNeighbour(), true); // 西
					}
				}
			}
			break;
		case Constant.WEST:
			disMax = this.calcGeoHash(listRes, pOriginal, pNorthWest, pSouthWest, pNorthEast, pSouthEast, distance, direction, geoHash, isAdd);
			if (disMax < distance) {
				this.getNeighbor(listRes, pOriginal, distance, direction, geoHash.getWesternNeighbour(), true);
			}
			break;
		case Constant.NORTH_WEST:
			disMax = this.mathCalc.getDistance(pOriginal, pNorthWest);
			disMin = this.mathCalc.getDistance(pOriginal, pSouthEast);
			if (disMin < distance) {
				listRes.add(geoHash.toBase32());
				if (disMax < distance) {
					GeoHash geoHashNorth = geoHash.getNorthernNeighbour();
					this.getNeighbor(listRes, pOriginal, distance, Constant.NORTH, geoHashNorth, true); // 北
					this.getNeighbor(listRes, pOriginal, distance, Constant.WEST, geoHash.getWesternNeighbour(), true); // 西
					this.getNeighbor(listRes, pOriginal, distance, Constant.NORTH_WEST, geoHashNorth.getWesternNeighbour(), true); // 西北
				} else {
					disMax = this.mathCalc.getDistance(pOriginal, pSouthWest);
					if (disMax < distance) {
						this.getNeighbor(listRes, pOriginal, distance, Constant.WEST, geoHash.getWesternNeighbour(), true); // 西
					}
					disMax = this.mathCalc.getDistance(pOriginal, pNorthEast);
					if (disMax < distance) {
						this.getNeighbor(listRes, pOriginal, distance, Constant.NORTH, geoHash.getNorthernNeighbour(), true); // 北
					}
				}
			}
			break;
		case Constant.NORTH:
			disMax = this.calcGeoHash(listRes, pOriginal, pNorthEast, pNorthWest, pSouthEast, pSouthWest, distance, direction, geoHash, isAdd);
			if (disMax < distance) {
				this.getNeighbor(listRes, pOriginal, distance, direction, geoHash.getNorthernNeighbour(), true);
			}
			break;
		case Constant.NORTH_EAST:
			disMax = this.mathCalc.getDistance(pOriginal, pNorthEast);
			disMin = this.mathCalc.getDistance(pOriginal, pSouthWest);
			if (disMin < distance) {
				listRes.add(geoHash.toBase32());
				if (disMax < distance) {
					GeoHash geoHashNorth = geoHash.getNorthernNeighbour();
					this.getNeighbor(listRes, pOriginal, distance, Constant.NORTH, geoHashNorth, true); // 北
					this.getNeighbor(listRes, pOriginal, distance, Constant.EAST, geoHash.getEasternNeighbour(), true); // 东
					this.getNeighbor(listRes, pOriginal, distance, Constant.NORTH_EAST, geoHashNorth.getEasternNeighbour(), true); // 东北
				} else {
					disMax = this.mathCalc.getDistance(pOriginal, pNorthWest);
					if (disMax < distance) {
						this.getNeighbor(listRes, pOriginal, distance, Constant.NORTH, geoHash.getNorthernNeighbour(), true); // 北
					}
					disMax = this.mathCalc.getDistance(pOriginal, pSouthEast);
					if (disMax < distance) {
						this.getNeighbor(listRes, pOriginal, distance, Constant.EAST, geoHash.getEasternNeighbour(), true); // 东
					}
				}
			}
			break;
		}
	}

	/**
	 * 
	 * @param listRes
	 * @param pOriginal
	 * @param pFarFir
	 * @param pFarSen
	 * @param pNearFir
	 * @param pNearSen
	 * @param distance
	 * @param direction
	 * @param geoHash
	 * @param isAdd
	 * @return
	 */
	private double calcGeoHash(List<String> listRes, Point pOriginal, Point pFarFir, Point pFarSen, Point pNearFir, Point pNearSen, int distance,
			int direction, GeoHash geoHash, boolean isAdd) {

		Point pProjectFar = this.mathCalc.getProjectivePoint(pFarFir, pFarSen, pOriginal);

		double disMax = this.mathCalc.getDistance(pOriginal, pProjectFar);
		if (isAdd) {
			listRes.add(geoHash.toBase32());
		} else {
			Point pProjectNear = this.mathCalc.getProjectivePoint(pNearFir, pNearSen, pOriginal);
			double disMin = this.mathCalc.getDistance(pOriginal, pProjectNear);
			if (disMin > distance) {
				listRes.add(geoHash.toBase32());
			}
		}
		return disMax;
	}

}
