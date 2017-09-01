package com.ensai.pfe.wasabe.server.tools;

import com.ensai.pfe.wasabe.server.metier.DeviceInfo;

/**
 * 
 * 
 * @author Axel et Xiaofei
 *
 */

public class CalculDistanceDirection {
	public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

	/**
	 * Use the <em>Haversine formula</em> to calculate the distance
	 * 
	 * @param startLat
	 * @param startLon
	 * @param endLat
	 * @param endLon
	 * @return distance between two points in m
	 */
	public static double calculateDistance(double startLat, double startLon,
			double endLat, double endLon) {
		double dLat = Math.toRadians(endLat - startLat);
		double dLon = Math.toRadians(endLon - startLon);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(startLat))
				* Math.cos(Math.toRadians(endLat)) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return AVERAGE_RADIUS_OF_EARTH * c * 1000;
	}

	
	/**
	 * Utility function to calculate the distance using haverside's formula for two deviceinfos.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double calculateDistance(DeviceInfo d1, DeviceInfo d2) {

		return calculateDistance(d1.getLatitude(), d1.getLongitude(),
				d2.getLatitude(), d2.getLongitude());

	}

	/**
	 * Calculate the bearing (direction, in degrees between 0 and 360) according to the start and end
	 * positions
	 * 
	 * @param startLat
	 * @param startLon
	 * @param endLat
	 * @param endLon
	 * @return
	 */
	public static double calculateBearing(double startLat, double startLon,
			double endLat, double endLon) {
		double startLatRadian = Math.toRadians(startLat);
		double endLatRadian = Math.toRadians(endLat);
		double dLon = Math.toRadians(endLon) - Math.toRadians(startLon);
		double y = Math.sin(dLon) * Math.cos(endLatRadian);
		double x = Math.cos(startLatRadian) * Math.sin(endLatRadian)
				- Math.sin(startLatRadian) * Math.cos(endLatRadian)
				* Math.cos(dLon);
		return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
	}

	public static double calculateVitesse(double startLat, double startLon,
			double endLat, double endLon, double startDate, double endDate) {

		double distance = calculateDistance(startLat, startLon, endLat, endLon);

		double diffDate = endDate - startDate;

		double vitesse = distance / diffDate; // vitesse en m/s

		return vitesse;
	}

}
