package model;

import java.util.List;

public class Station {

  private int id;
  private String name;
  private int line;
  private String currentDrawColor;
  private double latitude;
  private double longitude;
  private List<Station> neighbors;
  private List<Station> transferStations;

  private double minDistance = Double.POSITIVE_INFINITY;
  private Station previous;

  public Station() {}

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

  public String getCurrentDrawColor() {
    return currentDrawColor;
  }

  public void setCurrentDrawColor(String currentDrawColor) {
    this.currentDrawColor = currentDrawColor;
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

  public List<Station> getNeighbors() {
    return neighbors;
  }

  public void setNeighbors(List<Station> neighbors) {
    this.neighbors = neighbors;
  }

  public List<Station> getTransferStations() {
    return transferStations;
  }

  public void setTransferStations(List<Station> transferStations) {
    this.transferStations = transferStations;
  }

  public double getMinDistance() {
    return minDistance;
  }

  public void setMinDistance(double minDistance) {
    this.minDistance = minDistance;
  }

  public Station getPrevious() {
    return previous;
  }

  public void setPrevious(Station previous) {
    this.previous = previous;
  }

  @Override
  public String toString() {
    return getId() + ": " + getName() + "(" + getLine() + ")";
  }
}
