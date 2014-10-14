package model;

import java.util.List;

import processing.core.PApplet;

public class Line {

  PApplet app; // The parent PApplet that we will render ourselves onto
  private int id;
  private String name;
  private String color;
  private List<Station> stations;

  public Line(PApplet p, int id, String name, String color, List<Station> stations) {
    this.app = p;
    this.id = id;
    this.name = name;
    this.color = color;
    this.stations = stations;
  }

  public void draw(int xStart, int yStart, float stationRadius, double LAT_START,
      double LONG_START, int pixelPerKm) {

    int count = 0;

    int lineColor = getRGBColor(this.getColor());
    app.pushMatrix(); // Etat 0: (0, 0, 0)

    app.translate(xStart, yStart, 0);
    app.pushMatrix(); // Etat 1: première station

    for (Station station : stations) {
      // System.out.println("drawing station. " + station);

      count++;
      double[] deltaXY =
          convertGPSToDeltaXY(station.getLatitude(), station.getLongitude(), LAT_START, LONG_START,
              pixelPerKm);
      System.out.println("deltaXY = [" + deltaXY[0] + "," + deltaXY[1]);

      app.pushMatrix();
      app.translate((float) deltaXY[0], (float) deltaXY[1]);
      app.fill(lineColor);
      app.sphere(stationRadius);
      app.popMatrix();
    }

    app.popMatrix(); // Etat 1
    app.popMatrix(); // Etat 0
  }

  public int getRGBColor(String hexColorString) {
    int result = app.color(150);
    if (hexColorString != null && hexColorString.length() == 6) {
      int r = Integer.parseInt(hexColorString.substring(0, 2), 16);
      int g = Integer.parseInt(hexColorString.substring(2, 4), 16);
      int b = Integer.parseInt(hexColorString.substring(4, 6), 16);
      result = app.color((float) r, (float) g, (float) b);
    }
    return result;
  }

  public static double[] convertGPSToDeltaXY(double latitude, double longitude,
      double latitudeStart, double longitudeStart, int pixPerKm) {

    double[] coords = new double[2];
    double kmPerRad = 40075 / 360;
    double deltaX = (longitude - longitudeStart) * pixPerKm * kmPerRad * Math.cos(latitudeStart);
    double deltaY = -(latitude - latitudeStart) * pixPerKm * kmPerRad; // (kmParRadian=40000/2*PI)
    coords[0] = deltaX;
    coords[1] = deltaY;
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
