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


   public Itinerary() {
       
    }
  
  public Itinerary(int depart) {
        _depart = depart;
    }


  public Itinerary(int depart, int arrivee) {
    _depart = depart;
    _arrivee = arrivee;
  }

    public Itinerary(int _depart, int _arrivee, int _ligne) {
        this._depart = _depart;
        this._arrivee = _arrivee;
    }


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



}
