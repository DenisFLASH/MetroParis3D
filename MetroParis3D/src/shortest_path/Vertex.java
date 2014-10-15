package shortest_path;

public class Vertex implements Comparable<Vertex> {

  private final String id;
  private Edge[] edges;
  private double minDistance = Double.POSITIVE_INFINITY;
  private Vertex previous;

  public Vertex(String id) {
    this.id = id;
  }

  @Override
  public int compareTo(Vertex other) {
    return Double.compare(minDistance, other.getMinDistance());
  }

  public Edge[] getEdges() {
    return edges;
  }

  public void setEdges(Edge[] edges) {
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

  public String getId() {
    return id;
  }
}
