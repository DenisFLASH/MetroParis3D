package main;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Station;
import processing.core.PApplet;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.ListBox;
import controlP5.ListBoxItem;
import dao.DAOException;
import dao.DAOFactory;
import dao.DaoStation;


/**
 *
 * @author samia
 */
public class PanneauControl extends PApplet {

  DaoStation daoStation;
  Program _engine;
  ControlP5 cp5;
  List<Station> listStation;

  int def;
  ControlP5 depart;
  ControlP5 arrivee;

  ListBox l, r;
  int cnt = 0;
  int w, h;
  int abc = 100;


  public PanneauControl(Program engine, int theWidth, int theHeight) {
    _engine = engine;
    w = theWidth;
    h = theHeight;

  }


  public void setup() {
    size(100, 400);

    ControlP5.printPublicMethodsFor(ListBox.class);


    DAOFactory factory = null;

    try {
      factory = DAOFactory.getInstance();
    } catch (DAOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    daoStation = factory.getDaoStation();



    cp5 = new ControlP5(this);

    cp5.addSlider("def").plugTo(_engine, "def").setRange(0, 255).setPosition(50, 250);


    // cp5.addButton("ff").plugTo(_engine,"def");

    depart = new ControlP5(this);
    l =
        depart.addListBox("StationDepart").setPosition(50, 50).setSize(250, 250).setItemHeight(15)
            .setBarHeight(15).setColorBackground(color(255, 128)).setColorActive(color(0))
            .setColorForeground(color(255, 100, 0));


    arrivee = new ControlP5(this);
    r =
        arrivee.addListBox("StationArrivee").setPosition(50, 350).setSize(250, 250)
            .setItemHeight(15).setBarHeight(15).setColorBackground(color(255, 128))
            .setColorActive(color(0)).setColorForeground(color(255, 100, 0));



    for (int i = 0; i < 25; i++) {

      try {
        listStation = daoStation.getAllStations();

      } catch (DAOException ex) {
        Logger.getLogger(PanneauControl.class.getName()).log(Level.SEVERE, null, ex);
      }
    }



    for (int i = 0; i < listStation.size(); i++) {
      ListBoxItem lbi = l.addItem(listStation.get(i).toString(), i);
      ListBoxItem lbir = r.addItem(listStation.get(i).toString(), i);
      lbi.setColorBackground(0xffff0000);
      lbir.setColorBackground(0xffff0000);
    }

  }



  public void keyPressed() {
    if (key == '0') {
      // will activate the listbox item with value 5
      l.setValue(5);
    }
    if (key == '1') {
      // set the height of a listBox should always be a multiple of itemHeight
      l.setHeight(210);
    } else if (key == '2') {
      // set the height of a listBox should always be a multiple of itemHeight
      l.setHeight(120);
    } else if (key == '3') {
      // set the width of a listBox
      l.setWidth(200);
    } else if (key == 'i') {
      // set the height of a listBoxItem, should always be a fraction of the listBox
      l.setItemHeight(30);
    } else if (key == 'u') {
      // set the height of a listBoxItem, should always be a fraction of the listBox
      l.setItemHeight(10);
      l.setBackgroundColor(color(100, 0, 0));
    } else if (key == 'a') {
      int n = (int) (random(100000));
      l.addItem("item " + n, n);
    } else if (key == 'd') {
      l.removeItem("item " + cnt);
      cnt++;
    } else if (key == 'c') {
      l.clear();
    }
  }


  public void controlEvent(ControlEvent theEvent) {
    // ListBox is if type ControlGroup.
    // 1 controlEvent will be executed, where the event
    // originates from a ControlGroup. therefore
    // you need to check the Event with
    // if (theEvent.isGroup())
    // to avoid an error message from controlP5.

    if (theEvent.isGroup()) {
      // an event from a group e.g. scrollList
      println(theEvent.group().value() + " from " + theEvent.group());

    }

    if (theEvent.isGroup() && theEvent.name().equals("myList")) {
      int test = (int) theEvent.group().value();
      println("test " + test);
    }
  }

  public void draw() {
    background(128);
    // scroll the scroll List according to the mouseX position
    // when holding down SPACE.cp5.addSlider("abc").setRange(0, 255).setPosition(10,10);



    // if (keyPressed && key==' ') {
    // //l.scroll(mouseX/((float)width)); // scroll taks values between 0 and 1
    // }
    // if (keyPressed && key==' ') {
    // l.setWidth(mouseX);
    // }



  }



}
