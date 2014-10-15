import processing.core.PApplet;
import shapes3d.Tube;

public class PlanetsSketch extends PApplet {

  Tube tube = new Tube(this, 20, 100);
  int sunRadius = 50;
  int earthRadius = 20;
  int moonRadius = 5;
  int earthSunDistance = 250;
  int moonEarthDistance = 40;
  int sunColor = color(200, 200, 0);
  int earthColor = color(0, 0, 200);
  int moonColor = color(150);

  public static void main(String[] args) {
    PApplet.main(new String[] {"com.insta.testprocessing.PlanetsSketch"});
  }

  public void setup() {
    // On définit la taille d"écran; ainsi que le moteur de rendu (P2D ou P3D).
    size(800, 700, P3D);
  }

  public void draw() {
    background(0);
    lights();

    // On stocke l'état de la matrice de projection.
    pushMatrix(); // Etat 0: (0,0,0)

    // Le Soleil
    translate(width / 2, height / 2, 0);
    pushMatrix(); // Etat 1: centre (w/2, h/2, 0)
    rotateX(frameCount / 100f);
    fill(sunColor);
    tube.draw();

    popMatrix(); // Etat 1
    pushMatrix();

    // La Terre
    // Rotate + translate (pour dessiner la Terre sur la nouvelle position)
    rotateZ(frameCount / 100f);
    translate(earthSunDistance, 0, 0);
    fill(earthColor);
    sphere(earthRadius);

    // La Lune
    rotateZ(frameCount / 20f);
    translate(moonEarthDistance, 0, 0);
    noStroke();
    fill(moonColor);
    sphere(moonRadius);

    popMatrix(); // Etat 1
    popMatrix(); // Etat 0
  }

}
