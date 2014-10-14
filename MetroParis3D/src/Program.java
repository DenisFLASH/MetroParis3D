import java.util.ArrayList;
import java.util.List;

import model.Line;
import model.Station;
import processing.core.PApplet;
import controlP5.ControlP5;
import dao.DAOConfigurationException;
import dao.DAOException;
import dao.DAOFactory;
import dao.DaoStation;


public class Program extends PApplet {

  DAOFactory factory;
  DaoStation daoStation;
  ControlP5 cp5;
  // Scene scene;

  float stationRadius = 10;
  int pixelPerKm = 100;
  Line line1;

  public static void main(String[] args) {
    PApplet.main(new String[] {"Program"});
  }

  public void setup() {
    size(700, 700, P3D);
    noStroke();
    cp5 = new ControlP5(this);
    cp5.addSlider("stationRadius").setPosition(100, 50).setRange(0, 100);
    cp5.addSlider("pixelPerKm").setPosition(100, 100).setRange(0, 500);
    // scene = new Scene(this);

    // On remplit la ligne 1 par des stations de la base
    try {
      factory = DAOFactory.getInstance();
      daoStation = factory.getDaoStation();

      List<Station> stations = new ArrayList<Station>();
      for (int i = 1; i <= 25; i++) {
        stations.add(daoStation.getStationById(i));
      }
      line1 = new Line(this, 1, "Ligne 1", "FFFF00", stations);

    } catch (DAOConfigurationException | DAOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void draw() {
    background(0);
    lights();
    // scene.startAnimation();
    // hint(ENABLE_DEPTH_TEST);

    double latStart = line1.getStations().get(0).getLatitude();
    double longStart = line1.getStations().get(0).getLongitude();
    line1.draw(width / 10, height / 10, stationRadius, latStart, longStart, pixelPerKm);
    // hint(DISABLE_DEPTH_TEST);
    // camera();
    // cp5.draw();
  }
}
