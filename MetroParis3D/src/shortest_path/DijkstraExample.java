package shortest_path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraExample {

  public static void computePaths(Vertex source) {
    source.setMinDistance(0.);

    // Using PriorityQueue class with minDistance as a priority (see the comparator of Vertex)
    PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
    vertexQueue.add(source);

    while (!vertexQueue.isEmpty()) {
      Vertex u = vertexQueue.poll();

      // Visit each edge exiting u
      for (Edge e : u.getEdges()) {
        Vertex v = e.getTarget();
        double weight = e.getWeight();

        // relax the edge (u, v) :
        // if (u, v) is an edge and u is on the shortest path to v, then d(u) + w(u,v) = d(v).
        double distanceThroughU = u.getMinDistance() + weight;

        if (distanceThroughU < v.getMinDistance()) {

          // The priority queue does not like when the ordering of its elements is changed, so
          // when we change the minimum distance of any vertex, we need to remove it and re-insert
          // it into the set.
          vertexQueue.remove(v);
          v.setMinDistance(distanceThroughU);
          v.setPrevious(u);
          vertexQueue.add(v);
        }
      }
    }
  }

  public static List<Vertex> getShortestPathTo(Vertex target) {
    List<Vertex> path = new ArrayList<Vertex>();
    Vertex vertex = target;
    while (vertex != null) {
      path.add(vertex);
      vertex = vertex.getPrevious();
    }
    Collections.reverse(path);
    return path;
  }

  // Initialize graph
  public static void main(String[] args) {
    Vertex v0 = new Vertex("Harrisburg");
    Vertex v1 = new Vertex("Baltimore");
    Vertex v2 = new Vertex("Washington");
    Vertex v3 = new Vertex("Philadelphia");
    Vertex v4 = new Vertex("Binghamton");
    Vertex v5 = new Vertex("Allentown");
    Vertex v6 = new Vertex("New York");
    v0.setEdges(new Edge[] {new Edge(v1, 79.83), new Edge(v5, 81.15)});
    v1.setEdges(new Edge[] {new Edge(v0, 79.75), new Edge(v2, 39.42), new Edge(v3, 103.00)});
    v2.setEdges(new Edge[] {new Edge(v1, 38.65)});
    v3.setEdges(new Edge[] {new Edge(v1, 102.53), new Edge(v5, 61.44), new Edge(v6, 96.79)});
    v4.setEdges(new Edge[] {new Edge(v5, 133.04)});
    v5.setEdges(new Edge[] {new Edge(v0, 81.77), new Edge(v3, 62.05), new Edge(v4, 134.47),
        new Edge(v6, 91.63)});
    v6.setEdges(new Edge[] {new Edge(v3, 97.24), new Edge(v5, 87.94)});

    Vertex[] vertices = {v0, v1, v2, v3, v4, v5, v6};

    computePaths(v0);

    // print out shortest paths and distances
    for (Vertex v : vertices) {
      System.out.println("\n\nDistance to " + v.getId() + ": " + v.getMinDistance());
      List<Vertex> path = getShortestPathTo(v);
      displayPath(path);
    }
  }

  private static void displayPath(List<Vertex> path) {
    String prefix = "";
    for (Vertex vertex : path) {
      System.out.print(prefix + vertex.getId());
      prefix = " -> ";
    }
  }
}
