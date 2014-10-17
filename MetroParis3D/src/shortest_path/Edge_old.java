package shortest_path;

public class Edge_old {

  private final Vertex target;
  private final double weight;

  public Edge_old(Vertex target, double weight) {
    this.target = target;
    this.weight = weight;
  }

  public Vertex getTarget() {
    return target;
  }

  public double getWeight() {
    return weight;
  }

  @Override
  public String toString() {
    return "[target=" + target.getId() + ":" + target.getName() + ", weight=" + weight + "]";
  }


}
