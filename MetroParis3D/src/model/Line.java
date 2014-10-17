package model;

import java.util.List;

import main.Geometry;
import processing.core.PApplet;
import remixlab.proscene.Scene;
import dao.DAOException;

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

  public void draw(int x0, int y0, float stationRadius, int pixelPerKm, Scene scene)
      throws DAOException {

    int[] rgb = getRGBColor(this.getColor());
    int lineColor = app.color((float) rgb[0], (float) rgb[1], (float) rgb[2]);
    // float xPrevious = x0;
    // float yPrevious = y0;
    app.pushMatrix(); // Etat 0: pour préserver ControlP5
    app.translate(x0, y0, 0);

    // Pour toutes les stations:
    for (Station station : stations) {
      app.pushMatrix(); // Etat 1: (x0,y0)

      // 1. Dessiner des sphères
      double[] relativeKm =
          Geometry.GPSToKm(station.getLatitude(), station.getLongitude(), LAT_START, LONG_START);
      float relativeX = (float) relativeKm[0] * pixelPerKm;
      float relativeY = (float) relativeKm[1] * pixelPerKm;

      app.translate(relativeX, relativeY); // (x0,y0) -> (xStation, yStation)
      app.fill(lineColor);
      app.sphere(stationRadius);

      // Dessiner le tunnel depuis cette station jusqu'à TOUTES les stations voisines

      // for (Station neighbor : neighbors) {
      // app.pushMatrix(); // Etat 2 (xStation, yStation)
      //
      // double lat1 = station.getLatitude();
      // double long1 = station.getLongitude();
      // double lat2 = neighbor.getLatitude();
      // double long2 = neighbor.getLongitude();
      //
      // double[] deltaXY = Geometry.GPSToKm(lat1, long1, lat2, long2);
      // float deltaX = (float) deltaXY[0];
      // float deltaY = (float) deltaXY[1];
      // float r = stationRadius / 2;
      // float distance = Geometry.getDistance(deltaX, deltaY);
      // float tunnelHorizonAngle = Geometry.getAngle(Math.abs(deltaX), deltaY);
      // if (deltaX == 0) {
      // app.rotateX((float) -Math.PI / 2 * Math.signum(deltaY));
      // } else {
      // float signum = Math.signum(deltaX);
      // app.rotateY((float) -(signum * Math.PI / 2));
      // app.rotateX(tunnelHorizonAngle);
      // }
      //
      // scene.cylinder(r, distance);
      //
      // app.popMatrix(); // Etat 2
      // }

      app.popMatrix(); // Etat 1 (x0,y0)
    }

    // Avant l'itération suivante on met à jour les coordonnées
    // xPrevious = xCurrent;
    // yPrevious = yCurrent;

    app.popMatrix(); // Etat 0: pour redessiner le slider au bon endroit
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
