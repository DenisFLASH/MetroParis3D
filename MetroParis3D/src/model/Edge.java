package model;

public class Edge {

  private final Station target;
  private final double weight;

  public Edge(Station target, double weight) {
    this.target = target;
    this.weight = weight;
  }

  public Station getTarget() {
    return target;
  }

  public double getWeight() {
    return weight;
  }

  @Override
  public String toString() {
    return "[target=" + target.getId() + ":" + target.getName() + ", distance=" + weight + ", "
        + target.getNeighborEdges().size() + " target neighbors]";
  }

}
