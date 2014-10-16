package test.shortest_path;

import java.util.List;

import org.junit.Test;

import shortest_path.MetroGraph;
import shortest_path.Vertex;
import dao.DAOException;

public class MetroGraphTest {

  @Test
  public void testCalculate() throws DAOException {
    long startTime = System.currentTimeMillis();
    System.out.println("initializing graph...");
    MetroGraph graph = new MetroGraph();

    List<Vertex> vertices = graph.getVertices();
    long endInitTime = System.currentTimeMillis();

    System.out.println(vertices.size() + " vertices");
    for (Vertex vertex : vertices) {
      System.out.println(vertex.initialInfo());
    }
    System.out.println("\ngraph initialization time: " + (endInitTime - startTime) + " ms\n\n");

    int idStartingStation = 111;
    int idFinalStation = 1;
    int indexStartingVertex = idStartingStation - 1;
    int indexFinalVertex = idFinalStation - 1;
    Vertex start = vertices.get(indexStartingVertex);
    System.out.println("Calculating shortest paths from " + start.getId() + ":" + start.getName()
        + " to ALL other stations");
    graph.calculateShortestPathsToAll(indexStartingVertex);

    long endCalcTime = System.currentTimeMillis();
    System.out.println("\n calculation time: " + (endCalcTime - endInitTime) + " ms\n\n");

    Vertex finish = vertices.get(indexFinalVertex);
    graph.printShortestPathsToTarget(finish);
  }
}
