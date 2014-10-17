package shortest_path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraExample {

  public static void main(String[] args) {
    List<Vertex> vertices = initializeVertices();
    computePaths(vertices.get(0));

    // print out shortest paths and distances
    for (Vertex v : vertices) {
      System.out.println("\n\nDistance to " + v.getName() + ": " + v.getMinDistance());
      List<Vertex> path = getShortestPathTo(v);
      displayPath(path);
    }
  }

  // Initialize graph
  private static List<Vertex> initializeVertices() {
    List<Vertex> vertices = new ArrayList<Vertex>();

    Vertex v0 = new Vertex(0, "Harrisburg");
    Vertex v1 = new Vertex(1, "Baltimore");
    Vertex v2 = new Vertex(2, "Washington");
    Vertex v3 = new Vertex(3, "Philadelphia");
    Vertex v4 = new Vertex(4, "Binghamton");
    Vertex v5 = new Vertex(5, "Allentown");
    Vertex v6 = new Vertex(6, "New York");

    List<Edge_old> edgesOf0 = new ArrayList<Edge_old>();
    List<Edge_old> edgesOf1 = new ArrayList<Edge_old>();
    List<Edge_old> edgesOf2 = new ArrayList<Edge_old>();
    List<Edge_old> edgesOf3 = new ArrayList<Edge_old>();
    List<Edge_old> edgesOf4 = new ArrayList<Edge_old>();
    List<Edge_old> edgesOf5 = new ArrayList<Edge_old>();
    List<Edge_old> edgesOf6 = new ArrayList<Edge_old>();

    edgesOf0.add(new Edge_old(v1, 79.83));
    edgesOf0.add(new Edge_old(v5, 81.15));
    edgesOf1.add(new Edge_old(v0, 79.75));
    edgesOf1.add(new Edge_old(v2, 39.42));
    edgesOf1.add(new Edge_old(v3, 103.00));
    edgesOf2.add(new Edge_old(v1, 38.65));
    edgesOf3.add(new Edge_old(v1, 102.53));
    edgesOf3.add(new Edge_old(v5, 61.44));
    edgesOf3.add(new Edge_old(v6, 96.79));
    edgesOf4.add(new Edge_old(v5, 133.04));
    edgesOf5.add(new Edge_old(v0, 81.77));
    edgesOf5.add(new Edge_old(v3, 62.05));
    edgesOf5.add(new Edge_old(v4, 134.47));
    edgesOf5.add(new Edge_old(v6, 91.63));
    edgesOf6.add(new Edge_old(v3, 97.24));
    edgesOf6.add(new Edge_old(v5, 87.94));

    v0.setEdges(edgesOf0);
    v1.setEdges(edgesOf1);
    v2.setEdges(edgesOf2);
    v3.setEdges(edgesOf3);
    v4.setEdges(edgesOf4);
    v5.setEdges(edgesOf5);
    v6.setEdges(edgesOf6);

    vertices.add(v0);
    vertices.add(v1);
    vertices.add(v2);
    vertices.add(v3);
    vertices.add(v4);
    vertices.add(v5);
    vertices.add(v6);

    return vertices;
  }

  public static void computePaths(Vertex source) {
    source.setMinDistance(0.);

    // Using PriorityQueue class with minDistance as a priority (see the comparator of Vertex)
    PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
    vertexQueue.add(source);

    while (!vertexQueue.isEmpty()) {
      Vertex u = vertexQueue.poll();

      // Visit each edge exiting u
      for (Edge_old e : u.getEdges()) {
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

  private static void displayPath(List<Vertex> path) {
    String prefix = "";
    for (Vertex vertex : path) {
      System.out.print(prefix + vertex.getName());
      prefix = " -> ";
    }
  }
}
