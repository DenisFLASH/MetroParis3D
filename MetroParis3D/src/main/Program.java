package main;

import java.awt.Frame;

import model.MetroMap;
import processing.core.PApplet;
import remixlab.proscene.Scene;
import controlP5.ControlP5;
import dao.DAOException;

public class Program extends PApplet {

  ControlP5 cp5;
  Scene scene;
  MetroMap metroMap;
  PanneauControl cf;
  int def;

  public static void main(String[] args) {
    PApplet.main(new String[] {"Program"});

  }

  public void setup() {
    // size(1420, 1200, P3D);
    size(900, 800, P3D);


    noStroke();
    scene = new Scene(this);
    scene.setRadius(800);
    scene.setAxisIsDrawn(false);
    scene.setGridIsDrawn(false);

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
