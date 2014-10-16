package model;

import java.util.List;

import main.Geometry;
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
      double[] relativeKm =
          Geometry.GPSToKm(station.getLatitude(), station.getLongitude(), LAT_START, LONG_START);

      float xCurrent = (float) relativeKm[0] * pixelPerKm;
      float yCurrent = (float) relativeKm[1] * pixelPerKm;

      app.pushMatrix();
      app.translate(xCurrent, yCurrent);
      app.fill(lineColor);
      app.sphere(stationRadius);

      // Dessiner le tunnel, si ce n'est pas la première station
      if (stationIndex != 0) {
        app.pushMatrix();
        float deltaX = xCurrent - xPrevious;
        float deltaY = yCurrent - yPrevious;
        float r = stationRadius / 2;
        float distance = Geometry.getDistance(deltaX, deltaY);
        float tunnelHorizonAngle = Geometry.getAngle(Math.abs(deltaX), deltaY);

        if (deltaX == 0) {
          app.rotateX((float) -Math.PI / 2 * Math.signum(deltaY));
        } else {
          float signum = Math.signum(deltaX);
          app.rotateY((float) -(signum * Math.PI / 2));
          app.rotateX(tunnelHorizonAngle);
        }
        scene.cylinder(r, distance);
        app.popMatrix();
      }

      app.popMatrix();
      // Avant l'itération suivante on met à jour les coordonnées
      xPrevious = xCurrent;
      yPrevious = yCurrent;
    }
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
