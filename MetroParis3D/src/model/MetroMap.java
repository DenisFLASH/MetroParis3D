package model;

import java.util.ArrayList;
import java.util.List;

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

    // 1. Récupérér depuis la base de données les information sur des stations
    List<Line> lines = daoLine.getAllLines();
    for (Line line : lines) {
      String lineColor = line.getColor();
      List<Station> stationsOfLine = daoStation.getStationsByLineId(line.getId());

      // 2. Compléter les objets Station en leurs fournissant les références sur des
      // stations voisins (pour dessiner des tunnels), des correspondances, ainsi que la couleur
      // courante (qui peut être
      // changé ensuite, quand on va afficher le chemin le plus court)
      for (Station stationOfLine : stationsOfLine) {
        stationOfLine.setCurrentDrawColor(lineColor);
        List<Station> neighbors = daoMetroGraph.getNeighbors(stationOfLine.getId());
        List<Station> transferStations = daoMetroGraph.getTransferStations(stationOfLine.getId());
        stationOfLine.setNeighbors(neighbors);
        stationOfLine.setTransferStations(transferStations);
        // Ajouter la station avec toutes les information dans la liste des station du MetroMap
        stations.add(stationOfLine);
      }
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
      for (Station neighbor : station.getNeighbors()) {
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
}
