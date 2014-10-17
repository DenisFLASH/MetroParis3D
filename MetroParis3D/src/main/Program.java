package main;

import java.awt.Frame;

import model.Itinerary;
import model.MetroMap;
import processing.core.PApplet;
import remixlab.proscene.Camera;
import remixlab.proscene.Scene;
import controlP5.ControlP5;
import dao.DAOException;

public class Program extends PApplet {

  Camera cam;
  ControlP5 cp5;
  Scene scene;
  PanneauControl panneauControl;

  MetroMap metroMap;
  Itinerary itinerary;

  // int def;

  public static void main(String[] args) {
    PApplet.main(new String[] {"main.Program"});

  }

  public void setup() {
    size(1000, 700, P3D);
    noStroke();

    // Initialisation de la carte de metro
    try {

      long startTime = System.currentTimeMillis();
      System.out.println("initializing MetroMap...");
      metroMap = new MetroMap(this);
      long endInitTime = System.currentTimeMillis();
      System.out
          .println("\nMetroMap initialization time: " + (endInitTime - startTime) + " ms\n\n");

      panneauControl = createGUIWindow("PanneauControl", 500, 700, metroMap);
      scene = new Scene(this);
      scene.setRadius(800);
      scene.setAxisIsDrawn(false);
      cam = new Camera(scene);
      // cam.setPosition(new PVector(55,5,25));
      // cam.setOrientation(null);
      cam.setOrientation(150, 600);
      scene.setCamera(cam);
      itinerary = new Itinerary();



    } catch (DAOException e) {
      e.printStackTrace();
    }

  }

  public void draw() {
    frame.setLocation(510, 1);
    background(0);
    lights();

    // metroMap.drawAllMetroLines(scene);
    metroMap.drawAllMap(scene, metroMap.getStations());
  }

  public PanneauControl createGUIWindow(String theName, int theWidth, int theHeight,
      MetroMap metroMap) {

    System.out.println("metroMap=" + metroMap);

    Frame theFrame = new Frame(theName);
    PanneauControl panneauControl = new PanneauControl(this, theWidth, theHeight, metroMap);
    theFrame.add(panneauControl);
    panneauControl.init();

    System.out.println("metroMap=" + metroMap);
    // theFrame.setUndecorated(true);

    theFrame.setTitle(theName);
    theFrame.setSize(panneauControl.w, panneauControl.h);
    theFrame.setLocation(0, 0);
    theFrame.setResizable(false);

    theFrame.setVisible(true);

    return panneauControl;
  }

}
