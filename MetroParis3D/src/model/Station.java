package model;

import java.util.ArrayList;
import java.util.List;

public class Station implements Comparable<Station> {

  private int id;
  private String name;
  private int line;
  private String currentDrawColor;
  private double latitude;
  private double longitude;
  private List<Edge> neighborEdges = new ArrayList<Edge>();
  private List<Edge> transferEdges = new ArrayList<Edge>();

  private double minDistance = Double.POSITIVE_INFINITY;
  private Station previous;

  public Station() {}

  // Pour être utilisé dans PriorityQueue (en appliquant Dijkstra)
  @Override
  public int compareTo(Station other) {
    return Double.compare(minDistance, other.getMinDistance());
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

  public List<Edge> getNeighborEdges() {
    return neighborEdges;
  }

  public void setNeighborsEdges(List<Edge> neighborEdges) {
    this.neighborEdges = neighborEdges;
  }

  public List<Edge> getTransferEdges() {
    return transferEdges;
  }

  public void setTransferEdges(List<Edge> transferEdges) {
    this.transferEdges = transferEdges;
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


  public String shortInfo() {
    return getId() + ": " + getName() + "(" + getLine() + ")";
  }

  @Override
  public String toString() {
    return "Station [id=" + id + ", name=" + name + ", neighbors=[" + neighborEdges
        + "], transfers = [" + transferEdges + "], minDistance=" + minDistance + ", previous="
        + previous + "]";
  }
}
