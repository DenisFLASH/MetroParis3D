package shortest_path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import main.Geometry;
import model.Station;
import dao.DAOException;
import dao.DAOFactory;
import dao.DaoMetroGraph;
import dao.DaoStation;

public class MetroGraph {

  private List<Vertex> vertices;

  public List<Vertex> getVertices() {
    return vertices;
  }

  public void setVertices(List<Vertex> vertices) {
    this.vertices = vertices;
  }

  // Initialize graph
  public MetroGraph() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoStation daoStation = factory.getDaoStation();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    vertices = new ArrayList<Vertex>();


    // 1. Créer des objets Vertex sans Edge
    List<Station> allStations = daoStation.getAllStations();
    for (Station station : allStations) {
      vertices.add(new Vertex(station.getId(), station.getName()));
    }

    // Pour chaque VERTEX dans le graph initialiser des listes des Edge
    for (Vertex currentVertex : vertices) {
      List<Edge> edges = currentVertex.getEdges();
      Station currentStation = allStations.get(currentVertex.getId() - 1);

      // 2. Parcourir toutes les STATIONS VOISINES. Le poids de Edge = distance entre les stations.
      List<Station> neighbors = daoMetroGraph.getNeighbors(currentVertex.getId());
      for (Station neighbor : neighbors) {
        Vertex targetVertex = vertices.get(neighbor.getId() - 1);
        Edge newEdge =
            new Edge(targetVertex, Geometry.getDistanceBetweenTwoStations(currentStation, neighbor));
        edges.add(newEdge);
      }

      // 3. Parcourir toutes les CORRESPONDANCES. Le poids entre les correspondances = distance
      // entre les stations.
      List<Station> transferStations = daoMetroGraph.getTransferStations(currentVertex.getId());
      for (Station transferStation : transferStations) {
        Vertex targetVertex = vertices.get(transferStation.getId() - 1);
        Edge newEdge =
            new Edge(targetVertex, Geometry.getDistanceBetweenTwoStations(currentStation,
                transferStation));
        edges.add(newEdge);
      }

      // 3. Sauvegarder les Edges
      currentVertex.setEdges(edges);
    }
  }

  /**
   * Calcule les chemins les plus courts entre la station #1 de départ et TOUTES LES AUTRES
   * stations. Les valeurs de minDistance de chaque Vertex sont modifiées.
   */
  public void calculateShortestPathsToAll(int idStartVertex) {
    computePaths(vertices.get(idStartVertex));
  }

  public void printShortestPathsToTarget(Vertex finish) {
    System.out.println("\n\nDistance to " + finish.getName() + ": " + finish.getMinDistance());
    List<Vertex> path = getShortestPathTo(finish);
    displayPath(path);
  }

  public static void computePaths(Vertex source) {
    source.setMinDistance(0.);

    System.out.println("source = " + source);
    // Using PriorityQueue class with minDistance as a priority (see the comparator of Vertex)
    PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
    vertexQueue.add(source);

    while (!vertexQueue.isEmpty()) {
      Vertex u = vertexQueue.poll();

      // Visit each edge exiting u
      for (Edge edge : u.getEdges()) {
        Vertex v = edge.getTarget();
        double weight = edge.getWeight();

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

  public static void displayPath(List<Vertex> path) {
    String prefix = "";
    for (Vertex vertex : path) {
      System.out.println(prefix + vertex.getName() + "(" + vertex.getId() + ")");
      prefix = " -> ";
    }
  }
}
