package main;

import model.Station;

public class Geometry {

  public static float getDistance(float deltaX, float deltaY) {
    return (float) Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
  }

  public static float getAngle(float deltaX, float deltaY) {
    return (float) Math.atan(deltaY / deltaX);
  }

  public static double[] GPSToKm(double latitude, double longitude, double latitudeStart,
      double longitudeStart) {

    double avgLat = (latitude + latitudeStart) / 2;

    double[] deltaXY = new double[2];
    double kmPerRad = 40075 / 360;
    deltaXY[0] = (longitude - longitudeStart) * kmPerRad * Math.cos(avgLat);
    deltaXY[1] = -(latitude - latitudeStart) * kmPerRad;
    return deltaXY;
  }

  public static double getDistanceBetweenTwoStations(Station station1, Station station2) {
    double lat1 = station1.getLatitude();
    double long1 = station1.getLongitude();
    double lat2 = station2.getLatitude();
    double long2 = station2.getLongitude();

    double[] deltaXY = Geometry.GPSToKm(lat1, long1, lat2, long2);

    return Geometry.getDistance((float) deltaXY[0], (float) deltaXY[1]);
  }
}
