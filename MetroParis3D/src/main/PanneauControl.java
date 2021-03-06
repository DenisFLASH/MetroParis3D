package main;

import java.util.List;

import model.Itinerary;
import model.MetroMap;
import model.Station;
import processing.core.PApplet;
import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.ListBox;
import controlP5.ListBoxItem;
import dao.DaoStation;


/**
 *
 * @author samia
 */
public class PanneauControl extends PApplet {
  /**
   * Variable de la classe panneau control
   */
  private DaoStation daoStation;
  private Program _engine;
  private ControlP5 cp5;
  private List<Station> listStation;

  private int def;
  private ListBox depart;
  private ListBox arrivee;


  private int cnt = 0;
  int w, h, idDepart, idArrivee;
  private int abc = 100;
  private Button play;
  private ControlP5 AreaDepart;
  private ControlP5 AreaArrivee;

  private Itinerary it;
  private MetroMap metroMap;


  public PanneauControl(Program engine, int theWidth, int theHeight, MetroMap metroMap) {
    _engine = engine;
    w = theWidth;
    h = theHeight;
    this.metroMap = metroMap;
  }


  public void setup() {
    size(100, 400);

    // // Appel de la dao via la factory pour reccuperer les stations
    // DAOFactory factory = null;
    // try {
    // factory = DAOFactory.getInstance();
    // } catch (DAOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // daoStation = factory.getDaoStation();


    /**
     * Ensemble des évent Control P5
     */
    cp5 = new ControlP5(this);


    play = cp5.addButton("playItinerary").setPosition(50, 10);

    depart =
        cp5.addListBox("StationDepart").setPosition(50, 50).setSize(250, 250).setItemHeight(15)
            .setBarHeight(15).setColorBackground(color(255, 128)).setColorActive(color(0))
            .setColorForeground(color(255, 100, 0));

    arrivee =
        cp5.addListBox("StationArrivee").setPosition(50, 350).setSize(250, 250).setItemHeight(15)
            .setBarHeight(15).setColorBackground(color(255, 128)).setColorActive(color(0))
            .setColorForeground(color(255, 100, 0));


    System.out.println("metroMap = " + metroMap);
    listStation = metroMap.getStations();

    // Chargement des deux listBox Depart et arrivée
    for (int i = 0; i < listStation.size(); i++) {
      ListBoxItem lbi = depart.addItem(listStation.get(i).shortInfo(), listStation.get(i).getId());
      ListBoxItem lbir =
          arrivee.addItem(listStation.get(i).shortInfo(), listStation.get(i).getId());
      lbi.setColorBackground(0xffff0000);
      lbir.setColorBackground(0xffff0000);
    }

  } // end setup


  public void controlEvent(ControlEvent theEvent) {

    if (theEvent.isGroup()) {
      // an event from a group e.g. scrollList
      // println(theEvent.group().value()+" from "+theEvent.group());


      if (theEvent.isGroup() && theEvent.getName().equals("StationDepart")) {

        AreaDepart = new ControlP5(this);

        AreaDepart.addTextfield("Depart").setPosition(50, 800).setSize(200, 40).setFocus(false)
            .setColor(color(255, 0, 0)).setText("Station " + theEvent.group().value());
        idDepart = (int) theEvent.group().value();
      }

      if (theEvent.isGroup() && theEvent.getName().equals("StationArrivee")) {

        AreaArrivee = new ControlP5(this);

        AreaArrivee.addTextfield("Arrivee").setPosition(50, 900).setSize(200, 40)
            .setColor(color(255, 0, 0)).setText("Station " + theEvent.group().value());


        idArrivee = (int) theEvent.group().value();
      }

    }

  }

  public void playItinerary() {
    if (idDepart == 0 || idArrivee == 0) {
      System.out.println("Pas de station de depart ou d'arrivee");
    } else {

      it = new Itinerary();

      it.setDepart(idDepart);
      it.setArrivee(idArrivee);

      System.out.println(it.getDepart());
      System.out.println(it.getArrivee());

      // Executer la methode de traitement
      it.calculateItinerary(idDepart, idArrivee, metroMap);
    }
  }

  public void draw() {
    background(128);
  }



}
