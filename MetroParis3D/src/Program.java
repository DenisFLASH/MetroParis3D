import model.Line;
import processing.core.PApplet;
import remixlab.proscene.Scene;
import controlP5.ControlP5;
import dao.DAOConfigurationException;
import dao.DAOException;
import dao.DAOFactory;
import dao.DaoLine;
import dao.DaoStation;


public class Program extends PApplet {

  DAOFactory factory;
  DaoStation daoStation;
  DaoLine daoLine;
  ControlP5 cp5;
  Scene scene;
  float stationRadius = 5;
  int pixelPerKm = 100;
  Line line1;
  Line line2;

  public static void main(String[] args) {
    PApplet.main(new String[] {"Program"});
  }

  public void setup() {
    size(700, 700, P3D);
    noStroke();
    cp5 = new ControlP5(this);
    cp5.addSlider("stationRadius").setPosition(100, 50).setRange(0, 100);
    cp5.addSlider("pixelPerKm").setPosition(100, 100).setRange(0, 500);
    scene = new Scene(this);
    scene.setRadius(100);

    // Ligne 1
    try {
      factory = DAOFactory.getInstance();
      daoStation = factory.getDaoStation();
      daoLine = factory.getDaoLine();
      line1 = daoLine.getLineById(1);
      line2 = daoLine.getLineById(2);

      line1.setApp(this);
      line2.setApp(this);

    } catch (DAOConfigurationException | DAOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void draw() {
    background(0);
    lights();

    line1.draw(width / 10, height / 10, stationRadius, pixelPerKm, scene);
    line2.draw(width / 10, height / 10, stationRadius, pixelPerKm, scene);
  }
}
