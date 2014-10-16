package main;

import controlP5.ControlP5;
import dao.DAOException;
import java.awt.Frame;
import model.MetroMap;
import processing.core.PApplet;
import remixlab.proscene.Camera;
import remixlab.proscene.Scene;

public class Program extends PApplet {

  Camera cam;
  ControlP5 cp5;
  Scene scene;
  MetroMap metroMap;
  PanneauControl cf;
  int def;

  public static void main(String[] args) {
    PApplet.main(new String[] {"main.Program"});

  }

  public void setup() {
    size(1420, 1200, P3D);


    noStroke();
    scene = new Scene(this);
    scene.setRadius(800);
    scene.setAxisIsDrawn(false);
    cam = new Camera(scene);
   // cam.setPosition(new PVector(55,5,25));
   // cam.setOrientation(null);
    cam.setOrientation(150,600);

    
    scene.setCamera(cam);
    metroMap = new MetroMap(this);
    cf = createGUIWindow("PanneauControl", 500, 1200);

  }

  public void draw() {
    frame.setLocation(510, 1);
    background(def);
    lights();


    try {

      metroMap.drawAllMetroLines(scene);

    } catch (DAOException e) {
      e.printStackTrace();
    }

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
