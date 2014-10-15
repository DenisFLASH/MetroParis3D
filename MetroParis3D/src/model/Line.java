package model;

import java.util.List;

import processing.core.PApplet;
import remixlab.proscene.Scene;

public class Line {

  PApplet app; // The parent PApplet that we will render ourselves onto
  private int id;
  private String name;
  private String color;
  private List<Station> stations;

  // Coordonnées de la station La Défense (L1) serviront les coordonnées de départ.
  private static final float LAT_START = 48.891826754836515f;
  private static final float LONG_START = 2.237992043215619f;

  public Line() {}

  // Ce constructeur était utilisé pour instancier des lignes "en dur", avant d'implémenter JDBC.
  // public Line(PApplet p, int id, String name, String color, List<Station> stations) {
  // this.app = p;
  // this.id = id;
  // this.name = name;
  // this.color = color;
  // this.stations = stations;
  // }

  public void draw(int x0, int y0, float stationRadius, int pixelPerKm, Scene scene) {

    int[] rgb = getRGBColor(this.getColor());
    int lineColor = app.color((float) rgb[0], (float) rgb[1], (float) rgb[2]);
    float xPrevious = x0;
    float yPrevious = y0;
    app.pushMatrix(); // Etat 0: pour préserver ControlP5
    app.translate(x0, y0, 0);

    for (int stationIndex = 0; stationIndex < stations.size(); stationIndex++) {
      Station station = stations.get(stationIndex);
      double[] deltaXY =
          GPSToXY(station.getLatitude(), station.getLongitude(), LAT_START, LONG_START, pixelPerKm);
      float xCurrent = (float) deltaXY[0];
      float yCurrent = (float) deltaXY[1];

      app.pushMatrix(); // Etat 1: (x0, y0)
      app.translate(xCurrent, yCurrent);
      app.fill(lineColor);
      app.sphere(stationRadius);

      // Dessiner le tunnel, si ce n'est pas la station de départ
      if (stationIndex != 0) {
        float r = stationRadius / 2;
        float distance = getDistance(xPrevious, yPrevious, xCurrent, yCurrent);
        float tunnelHorizonAngle = getAngle(xPrevious, yPrevious, xCurrent, yCurrent);
        app.pushMatrix(); // Etat 2: avant dessin du tunnel
        app.rotateY((float) -Math.PI / 2);
        app.rotateX(tunnelHorizonAngle);
        scene.cylinder(r, distance);
        app.popMatrix(); // Etat 2
      }

      app.popMatrix(); // Etat 1
      // Avant l'itération suivante on met à jour les coordonnées
      xPrevious = xCurrent;
      yPrevious = yCurrent;
    }
    app.popMatrix(); // Etat 0: pour redessiner le slider au bon endroit
  }

  public static float getDistance(float x1, float y1, float x2, float y2) {
    double deltaX = x2 - x1;
    double deltaY = y2 - y1;
    return (float) Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
  }

  public static float getAngle(float x1, float y1, float x2, float y2) {
    double deltaX = x2 - x1;
    double deltaY = y2 - y1;
    return (float) Math.atan(deltaY / deltaX);
  }

  public static int[] getRGBColor(String hexColorString) {
    int[] result = new int[3];
    if (hexColorString != null && hexColorString.length() == 6) {
      result[0] = Integer.parseInt(hexColorString.substring(0, 2), 16);
      result[1] = Integer.parseInt(hexColorString.substring(2, 4), 16);
      result[2] = Integer.parseInt(hexColorString.substring(4, 6), 16);
    }
    return result;
  }

  public static double[] GPSToXY(double latitude, double longitude, double latitudeStart,
      double longitudeStart, int pixPerKm) {

    double[] coords = new double[2];
    double kmPerRad = 40075 / 360;
    coords[0] = (longitude - longitudeStart) * pixPerKm * kmPerRad * Math.cos(latitudeStart);
    coords[1] = -(latitude - latitudeStart) * pixPerKm * kmPerRad;
    return coords;
  }


  // public double getDistanceBetweenTwoStations(int indexStation1, int indexStation2) {
  // double distance = 0.0;
  //
  // return distance;
  // }

  @Override
  public String toString() {
    return getId() + ": " + getName() + ", color = " + getColor();
  }


  public PApplet getApp() {
    return app;
  }

  public void setApp(PApplet app) {
    this.app = app;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public List<Station> getStations() {
    return stations;
  }

  public void setStations(List<Station> stations) {
    this.stations = stations;
  }
}
