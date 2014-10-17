package test.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import model.MetroMap;
import model.Station;

import org.junit.Test;

import processing.core.PApplet;
import dao.DAOException;

public class MetroMapTest {

  @Test
  public void testMetroMap_StationDrawColor() throws DAOException {
    long startTime = System.currentTimeMillis();
    System.out.println("initializing metro map...");

    PApplet app = new PApplet();
    MetroMap metroMap = new MetroMap(app);

    List<Station> vertices = metroMap.getStations();

    System.out.println(vertices.size() + " vertices");
    for (Station vertex : vertices) {
      System.out.println(vertex);
    }

    long endInitTime = System.currentTimeMillis();
    System.out.println("\nMap initialization time: " + (endInitTime - startTime) + " ms\n\n");

    List<Station> allStations = metroMap.getStations();
    System.out.println(allStations.size() + " stations");
    assertEquals(383, allStations.size());

    Station station5 = allStations.get(5 - 1); // Porte Maillot (1)
    System.out.println(station5);
    assertEquals("FFFF00", station5.getCurrentDrawColor());

    Station station345 = allStations.get(345 - 1); // La Fourche (13)
    System.out.println(station345);
    assertEquals(3, station345.getNeighborEdges().size());

    Station station28 = allStations.get(28 - 1); // Charles de Gaulle - Etoile (2)
    System.out.println(station28);
    assertEquals(2, station28.getTransferEdges().size());

    // SHORTEST PATH TEST //////////////////////////////////////////////////////////
    int idStart = 112;
    int idFinish = 1;
    Station start = metroMap.getStations().get(idStart - 1);
    System.out.println("Calculating shortest paths from " + start.getId() + ":" + start.getName()
        + " to ALL other stations");
    metroMap.calculateShortestPathsToAll(idStart);

    long endCalcTime = System.currentTimeMillis();
    System.out.println("\n calculation time: " + (endCalcTime - endInitTime) + " ms\n\n");

    metroMap.printShortestPathsToTarget(idFinish);
  }
}
