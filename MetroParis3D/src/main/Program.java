package main;

import java.awt.Frame;

import model.MetroMap;
import processing.core.PApplet;
import remixlab.proscene.Camera;
import remixlab.proscene.Scene;
import shortest_path.MetroGraph;
import controlP5.ControlP5;
import dao.DAOException;

public class Program extends PApplet {

  Camera cam;
  ControlP5 cp5;
  Scene scene;
  MetroMap metroMap;
  MetroGraph metroGraph;
  PanneauControl cf;
  int def;

  public static void main(String[] args) {
    PApplet.main(new String[] {"main.Program"});

  }

  public void setup() {
    size(1000, 700, P3D);
    cf = createGUIWindow("PanneauControl", 500, 700);

    noStroke();
    scene = new Scene(this);
    scene.setRadius(800);
    scene.setAxisIsDrawn(false);
    cam = new Camera(scene);
    // cam.setPosition(new PVector(55,5,25));
    // cam.setOrientation(null);
    cam.setOrientation(150, 600);
    scene.setCamera(cam);

    // Initialisation de la carte de metro
    try {

      long startTime = System.currentTimeMillis();
      System.out.println("initializing MetroMap...");
      metroMap = new MetroMap(this);
      long time1 = System.currentTimeMillis();
      System.out.println("\nMetroMap initialization time: " + (time1 - startTime) + " ms\n\n");

      // System.out.println("initializing graph...");
      // metroGraph = new MetroGraph();
      // long time2 = System.currentTimeMillis();
      // System.out.println("\nGraph initialization time: " + (time2 - time1) + " ms\n\n");

    } catch (DAOException e) {
      e.printStackTrace();
    }
  }

  public void draw() {
    frame.setLocation(510, 1);
    background(def);
    lights();

    // metroMap.drawAllMetroLines(scene);
    metroMap.drawAllMap(scene, metroMap.getStations());
  }

  public PanneauControl createGUIWindow(String theName, int theWidth, int theHeight) {

    Frame theFrame = new Frame(theName);
    PanneauControl panneauControl = new PanneauControl(this, theWidth, theHeight);
    theFrame.add(panneauControl);

    panneauControl.init();
    // theFrame.setUndecorated(true);

    theFrame.setTitle(theName);
    theFrame.setSize(panneauControl.w, panneauControl.h);
    theFrame.setLocation(0, 0);
    theFrame.setResizable(false);

    theFrame.setVisible(true);
    return panneauControl;
  }

}
