/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */

package model;

/**
 *
 * @author samia
 */
public class Itinerary {

  private int _depart;
  private int _arrivee;


  public Itinerary() {}

  public Itinerary(int depart) {
    _depart = depart;
  }

  public Itinerary(int depart, int arrivee) {
    _depart = depart;
    _arrivee = arrivee;
  }

  // public Itinerary(int _depart, int _arrivee, int _ligne) {
  // this._depart = _depart;
  // this._arrivee = _arrivee;
  // }

  public int getDepart() {
    return _depart;
  }

  public void setDepart(int depart) {
    _depart = depart;
  }

  public int getArrivee() {
    return _arrivee;
  }

  public void setArrivee(int arrivee) {
    _arrivee = arrivee;
  }

  public void calculateItinerary(int idDepart, int idArrivee, MetroMap metroMap) {
    System.out.println("idDepart: " + idDepart + ", idArrivee: " + idArrivee);
    long startCalcTime = System.currentTimeMillis();

    Station start = metroMap.getStations().get(idDepart - 1);
    System.out.println("Calculating shortest paths from " + start.getId() + ":" + start.getName()
        + " to ALL other stations");
    metroMap.calculateShortestPathsToAll(idDepart);

    long endCalcTime = System.currentTimeMillis();
    System.out.println("\n calculation time: " + (endCalcTime - startCalcTime) + " ms\n\n");

    metroMap.printShortestPathsToTarget(idArrivee);
  }
}
