package com.toanhv22.routeoptimization.utils;

public class Common {

    // Haversine formula
    public static Double caculateDistanceFromTwoPoints(String lat1, String lng1, String lat2, String lng2){
        double R = 6378137;
        double latitude1 = Double.parseDouble(lat1);
        double longitude1 = Double.valueOf(lng1);
        double latitude2 = Double.valueOf(lat2);
        double longitude2 = Double.valueOf(lng2);

        double dLat = rad(latitude2-latitude1);
        double dLong = rad(longitude2-longitude1);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(rad(latitude1)) * Math.cos(rad(latitude2)) * Math.sin(dLong/2) * Math.sin(dLong/2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));

        return R * c; // mét
    }

    public static double rad(double x){
        return x * Math.PI / 180;
    }
}
