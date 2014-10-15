import model.MetroMap;
import processing.core.PApplet;
import remixlab.proscene.Scene;
import dao.DAOException;

public class Program extends PApplet {
  Scene scene;
  MetroMap metroMap;

  public static void main(String[] args) {
    PApplet.main(new String[] {"Program"});
  }

  public void setup() {
    size(700, 700, P3D);
    noStroke();
    scene = new Scene(this);
    scene.setRadius(100);

    metroMap = new MetroMap(this);
  }

  public void draw() {
    background(0);
    lights();

    try {

      metroMap.drawMetroLines(new int[] {1, 2, 3, 4}, scene);

    } catch (DAOException e) {
      e.printStackTrace();
    }
  }
}
