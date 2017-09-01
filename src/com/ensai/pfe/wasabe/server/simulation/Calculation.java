package com.ensai.pfe.wasabe.server.simulation;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

public class Calculation {
    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

    /**
     * Use the <em>Haversine formula</em> to calculate the distance
     *
     * @param startLat
     * @param startLon
     * @param endLat
     * @param endLon
     * @return distance between two points in km
     * @see <a
     * href="http://www.movable-type.co.uk/scripts/latlong.html">Reference</a>
     * <a href=
     * "http://developer.android.com/reference/android/location/Location.html#distanceTo%28android.location.Location%29"
     * > Android Implementations</a>
     */
    public static double calculateDistance(double startLat, double startLon
            , double endLat, double endLon) {
        double dLat = toRadians(endLat - startLat);
        double dLon = toRadians(endLon - startLon);
        double a = sin(dLat / 2) * sin(dLat / 2)
                + cos(toRadians(startLat)) * cos(toRadians(endLat))
                * sin(dLon / 2) * sin(dLon / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return AVERAGE_RADIUS_OF_EARTH * c;
    }

    /**
     * Calculate the bearing (directing) according to the stard and end
     * positions
     *
     * @param startLat
     * @param startLon
     * @param endLat
     * @param endLon
     * @return
     */
    public static double calculateBearing(double startLat, double startLon
            , double endLat, double endLon) {
        double startLatRadian = toRadians(startLat);
        double endLatRadian = toRadians(endLat);
        double dLon = toRadians(endLon) - toRadians(startLon);
        double y = sin(dLon) * cos(endLatRadian);
        double x = cos(startLatRadian) * sin(endLatRadian) -
                sin(startLatRadian) * cos(endLatRadian) * cos(dLon);
        return (toDegrees(atan2(y, x)) + 360) % 360;
    }

    // test
    public static void main(String[] args) {
       // System.out.println(calculateDistance(43.64018868764693, 3.858499795213016, 43.62812425397331,
      //          3.881141293959259));
      //  System.out.println(calculateBearing(43.64018868764693, 3.858499795213016, 43.62812425397331,
      //          3.881141293959259));
    }
}