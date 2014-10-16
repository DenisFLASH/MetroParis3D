package shortest_path;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex> {

  private final int id;
  private final String name;
  private List<Edge> edges = new ArrayList<Edge>();

  private double minDistance = Double.POSITIVE_INFINITY;
  private Vertex previous;

  public Vertex(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public int compareTo(Vertex other) {
    return Double.compare(minDistance, other.getMinDistance());
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public void setEdges(List<Edge> edges) {
    this.edges = edges;
  }

  public double getMinDistance() {
    return minDistance;
  }

  public void setMinDistance(double minDistance) {
    this.minDistance = minDistance;
  }

  public Vertex getPrevious() {
    return previous;
  }

  public void setPrevious(Vertex previous) {
    this.previous = previous;
  }

  @Override
  public String toString() {
    return "Vertex [id=" + id + ", name=" + name + ", edges=[" + edges + "], minDistance="
        + minDistance + ", previous=" + previous + "]";
  }

  public String initialInfo() {
    String edgesText = "";
    String prefix = "";
    for (Edge edge : edges) {
      edgesText += prefix + edge.toString();
      prefix = " , ";
    }
    return "Vertex " + id + " \"" + name + "\", edges = " + edgesText;
  }

}
