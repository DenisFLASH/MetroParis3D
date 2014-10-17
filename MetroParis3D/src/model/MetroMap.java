package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import main.Geometry;
import processing.core.PApplet;
import remixlab.proscene.Scene;
import dao.DAOException;
import dao.DAOFactory;
import dao.DaoLine;
import dao.DaoMetroGraph;
import dao.DaoStation;

/**
 * Classe qui sert à AFFICHER toute la carte du métro parisien. Presque idéntique au graphe dans
 * l'algo d'un chemin le plus court, sauf que la carte ne contient pas d'information sur des
 * correspondances et le "poids" des segments. Elle sert à afficher la carte, donc on a besoin de
 * connaitre l'information sur des lignes et stations pour pouvoir dessiner des sphères et des
 * tunneles.
 */
public class MetroMap {

  private static final int X0 = 10;
  private static final int Y0 = 10;
  private static final float STATION_RADIUS = 5;
  private static final int PIXEL_PER_KM = 100;
  // Coordonnées de la station La Défense (L1) serviront les coordonnées de départ.
  private static final float LAT_START = 48.891826754836515f;
  private static final float LONG_START = 2.237992043215619f;

  private PApplet app;
  DAOFactory factory;
  DaoStation daoStation;
  DaoLine daoLine;
  DaoMetroGraph daoMetroGraph;

  private List<Station> stations;

  public List<Station> getStations() {
    return stations;
  }

  public MetroMap(PApplet p) throws DAOException {
    app = p;
    factory = DAOFactory.getInstance();
    daoStation = factory.getDaoStation();
    daoLine = factory.getDaoLine();
    daoMetroGraph = factory.getDaoMetroGraph();

    stations = new ArrayList<Station>();

    // 1. Récupérér depuis la base de données les information sur des stations.
    // Remplir la liste 'stations' avec toutes les stations, SANS INITIALISER des listes d'Edge
    // List<Line> lines = daoLine.getAllLines();
    List<Line> lines = daoLine.getAllLines();
    for (Line line : lines) {
      String lineColor = line.getColor();
      List<Station> stationsOfLine = daoStation.getStationsByLineId(line.getId());
      for (Station stationOfLine : stationsOfLine) {

        // couleur courante peut être changé ensuite, quand on va dessiner le chemin le plus court
        stationOfLine.setCurrentDrawColor(lineColor);
        stations.add(stationOfLine);
      }
    }

    // Pour chaque VERTEX dans le graph initialiser des listes d'Edge
    for (Station currentStation : stations) {
      List<Edge> neighborEdges = currentStation.getNeighborEdges();
      List<Edge> transferEdges = currentStation.getTransferEdges();

      // 2. Parcourir toutes les STATIONS VOISINES. Le poids de Edge = distance entre les stations.
      List<Station> neighbors = daoMetroGraph.getNeighbors(currentStation.getId());
      for (Station neighbor : neighbors) {
        Station targetVertex = stations.get(neighbor.getId() - 1);
        Edge newEdge =
            new Edge(targetVertex, Geometry.getDistanceBetweenTwoStations(currentStation, neighbor));
        neighborEdges.add(newEdge);
      }

      // 3. Parcourir toutes les CORRESPONDANCES. Le poids entre les correspondances = distance
      // entre les stations.
      List<Station> transfers = daoMetroGraph.getTransferStations(currentStation.getId());
      for (Station transfer : transfers) {
        Station targetVertex = stations.get(transfer.getId() - 1);
        Edge newEdge =
            new Edge(targetVertex, Geometry.getDistanceBetweenTwoStations(currentStation, transfer));
        transferEdges.add(newEdge);
      }

      // 4. Sauvegarder les Edges
      currentStation.setNeighborsEdges(neighborEdges);
      currentStation.setTransferEdges(transferEdges);
    }
  }

  public void drawAllMetroLines(Scene scene) throws DAOException {
    List<Line> lines = daoLine.getAllLines();
    for (Line line : lines) {
      line.setApp(app);
      line.draw(X0, Y0, STATION_RADIUS, PIXEL_PER_KM, scene);
    }
  }

  public void drawAllMap(Scene scene, List<Station> allStations) {

    app.pushMatrix(); // Etat 0: pour préserver ControlP5
    app.translate(X0, Y0, 0);

    // Pour toutes les stations:
    for (Station station : allStations) {
      app.pushMatrix(); // Etat 1: (x0,y0)

      // 1. Dessiner des sphères
      double[] relativeKm =
          Geometry.GPSToKm(station.getLatitude(), station.getLongitude(), LAT_START, LONG_START);
      float relativeX = (float) relativeKm[0] * PIXEL_PER_KM;
      float relativeY = (float) relativeKm[1] * PIXEL_PER_KM;
      int[] rgb = Line.getRGBColor(station.getCurrentDrawColor());
      int currentFillColor = app.color((float) rgb[0], (float) rgb[1], (float) rgb[2]);
      int selectedColor = app.color(255, 0, 0);

      app.translate(relativeX, relativeY); // (x0,y0) -> (xStation, yStation)
      if (station.getId() <= 7) {
        currentFillColor = selectedColor;
      }
      app.fill(currentFillColor);
      app.sphere(STATION_RADIUS);

      // Dessiner le tunnel depuis cette station jusqu'à TOUTES les stations voisines
      for (Edge neighborEdge : station.getNeighborEdges()) {
        Station neighbor = neighborEdge.getTarget();

        app.pushMatrix(); // Etat 2 (xStation, yStation)

        double lat1 = station.getLatitude();
        double long1 = station.getLongitude();
        double lat2 = neighbor.getLatitude();
        double long2 = neighbor.getLongitude();

        double[] deltaXY = Geometry.GPSToKm(lat1, long1, lat2, long2);
        float deltaX = (float) deltaXY[0];
        float deltaY = (float) deltaXY[1];
        float r = STATION_RADIUS / 2;
        float distance = Geometry.getDistance(deltaX, deltaY) * PIXEL_PER_KM;
        float tunnelHorizonAngle = Geometry.getAngle(Math.abs(deltaX), deltaY);
        if (deltaX == 0) {
          app.rotateX((float) -Math.PI / 2 * Math.signum(deltaY));
        } else {
          float signum = Math.signum(deltaX);
          app.rotateY((float) -(signum * Math.PI / 2));
          app.rotateX(tunnelHorizonAngle);
        }

        scene.cylinder(r, distance);

        app.popMatrix(); // Etat 2
      }

      app.popMatrix(); // Etat 1 (x0,y0)
    }

    // Avant l'itération suivante on met à jour les coordonnées
    // xPrevious = xCurrent;
    // yPrevious = yCurrent;

    app.popMatrix(); // Etat 0: pour redessiner le slider au bon endroit

  }

  /**
   * Calcule les chemins les plus courts entre la station #1 de départ et TOUTES LES AUTRES
   * stations. Les valeurs de minDistance de chaque Station sont modifiées.
   */
  public void calculateShortestPathsToAll(int idStart) {
    computePaths(stations.get(idStart - 1));
  }

  public void printShortestPathsToTarget(int idFinish) {
    Station finish = stations.get(idFinish - 1);
    System.out.println("\n\nDistance to " + finish.getId() + ":" + finish.getName() + " = "
        + finish.getMinDistance());
    List<Station> path = getShortestPathTo(finish);
    displayPath(path);
  }

  public static void computePaths(Station source) {
    source.setMinDistance(0.0);

    System.out.println("source = " + source);
    // Using PriorityQueue class with minDistance as a priority (see the comparator of Station)
    PriorityQueue<Station> vertexQueue = new PriorityQueue<Station>();
    vertexQueue.add(source);

    while (!vertexQueue.isEmpty()) {
      Station u = vertexQueue.poll();

      // Visit each NEIGHBOR edge exiting u
      for (Edge edge : u.getNeighborEdges()) {
        Station v = edge.getTarget();
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

      // Visit each TRANSFER edge exiting u
      for (Edge edge : u.getTransferEdges()) {
        Station v = edge.getTarget();
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

      // // Visit each edge exiting u
      // List<Edge> neighborEdges = u.getNeighborEdges();
      // List<Edge> transferEdges = u.getTransferEdges();
      // List<Edge> allEdges = new ArrayList<Edge>();
      // allEdges.addAll(neighborEdges);
      // allEdges.addAll(transferEdges);
      // System.out.println("allEdges: " + allEdges.size());
      // for (Edge edge : allEdges) {
      // Station v = edge.getTarget();
      // double weight = edge.getWeight();
      //
      // // relax the edge (u, v) :
      // // if (u, v) is an edge and u is on the shortest path to v, then d(u) + w(u,v) = d(v).
      // double distanceThroughU = u.getMinDistance() + weight;
      //
      // if (distanceThroughU < v.getMinDistance()) {
      // // The priority queue does not like when the ordering of its elements is changed, so
      // // when we change the minimum distance of any vertex, we need to remove it and re-insert
      // // it into the set.
      // vertexQueue.remove(v);
      // v.setMinDistance(distanceThroughU);
      // v.setPrevious(u);
      // vertexQueue.add(v);
      // }
      // }

    }
  }

  public static List<Station> getShortestPathTo(Station target) {
    List<Station> path = new ArrayList<Station>();
    Station vertex = target;
    while (vertex != null) {
      path.add(vertex);
      vertex = vertex.getPrevious();
    }
    Collections.reverse(path);
    return path;
  }

  public static void displayPath(List<Station> path) {
    String prefix = "";
    for (Station vertex : path) {
      System.out.println(prefix + vertex.getName() + "(" + vertex.getId() + ")");
      prefix = " -> ";
    }
  }

}
