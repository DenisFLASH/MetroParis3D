package model;

import java.util.List;

public class Station {

  private int id;
  private String name;
  private int line;
  private double latitude;
  private double longitude;
  private List<Station> transferStations;

  public Station() {}

  // Ce constructeur était utilisé pour instancier des stations "en dur", avant d'implémenter JDBC.
  // public Station(int id, String name, int line, double latitude, double longitude) {
  // this.id = id;
  // this.name = name;
  // this.line = line;
  // this.latitude = latitude;
  // this.longitude = longitude;
  // }
  //
  // public Station(int id, String name, int line, double latitude, double longitude,
  // List<Station> transferStations) {
  // this.id = id;
  // this.name = name;
  // this.line = line;
  // this.latitude = latitude;
  // this.longitude = longitude;
  // this.transferStations = transferStations;
  // }

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

  public int getLine() {
    return line;
  }

  public void setLine(int line) {
    this.line = line;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public List<Station> getTransferStations() {
    return transferStations;
  }

  public void setTransferStations(List<Station> transferStations) {
    this.transferStations = transferStations;
  }

  @Override
  public String toString() {
    return getId() + ": " + getName() + " (ligne " + getLine() + "), latitude=" + getLatitude()
        + ", longitude=" + getLongitude();
  }
}
