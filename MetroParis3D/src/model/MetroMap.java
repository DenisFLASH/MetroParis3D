package model;

import processing.core.PApplet;
import remixlab.proscene.Scene;
import dao.DAOException;
import dao.DAOFactory;
import dao.DaoLine;

public class MetroMap {

  private static final int X0 = 10;
  private static final int Y0 = 10;
  private static final float STATION_RADIUS = 5;
  private static final int PIXEL_PER_KM = 100;

  private PApplet app;
  private DaoLine daoLine;

  public MetroMap(PApplet p) {
    app = p;
    DAOFactory factory = null;

    try {
      factory = DAOFactory.getInstance();
    } catch (DAOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    daoLine = factory.getDaoLine();
  }

  public void drawMetroLines(int[] lines, Scene scene) throws DAOException {

    for (int i = 0; i < lines.length; i++) {
      System.out.println("Line " + lines[i]);
      Line line = daoLine.getLineById(lines[i]);
      line.setApp(app);
      line.draw(X0, Y0, STATION_RADIUS, PIXEL_PER_KM, scene);
    }
  }
}
