/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author samia
 */
public class Itineray {
    
    private int depart;
    private int arrivee;

    public Itineray(int depart, int arrivee) {
        this.depart = depart;
        this.arrivee = arrivee;
    }

    public int getDepart() {
        return depart;
    }

    public void setDepart(int depart) {
        this.depart = depart;
    }

    public int getArrivee() {
        return arrivee;
    }

    public void setArrivee(int arrivee) {
        this.arrivee = arrivee;
    }
    
    
    
    
}
