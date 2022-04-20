package com.M2IProject.eventswipe.utils;

/*::  Function parameters:                                                   :*/

/*::    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)  :*/
/*::    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)  :*/
/*::    unit = the unit you desire for results                               :*/
/*::           where: 'M' is statute miles (default)                         :*/
/*::                  'K' is kilometers                                      :*/
/*::                                                                         :*/

public class DistanceCalculator {
    public static void main(String[] args) throws java.lang.Exception {
	System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "M") + " Miles\n");
	System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "K") + " Kilometers\n");
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
	if ((lat1 == lat2) && (lon1 == lon2)) {
	    return 0;
	} else {
	    double theta = lon1 - lon2;
	    double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
		    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
	    dist = Math.acos(dist);
	    dist = Math.toDegrees(dist);
	    dist = dist * 60 * 1.1515;
	    if (unit.equals("K")) {
		dist = dist * 1.609344;
	    } else if (unit.equals("N")) {
		dist = dist * 0.8684;
	    }
	    return (dist);
	}
    }
}
