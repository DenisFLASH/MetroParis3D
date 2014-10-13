
import processing.core.PApplet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 import remixlab.proscene.*;
/**
 *
 * @author samia
 */
public class Program extends PApplet {

   

MyScene scene;

@Override
    public void setup() {
  size(640, 360, P3D);
  // We instantiate our MyScene class defined below
  scene = new MyScene(this);
}

@Override
public void draw() {
  background(0);
}

public void keyPressed() {
  if((key == 'x') || (key == 'X'))
    scene.setAnimationPeriod(scene.animationPeriod()-2);
  if((key == 'y') || (key == 'Y'))
    scene.setAnimationPeriod(scene.animationPeriod()+2);
}

class MyScene extends Scene {
  int nbPart;

  // We need to call super(p) to instantiate the base class
  public MyScene(PApplet p) {
    super(p);
  }

  // Initialization stuff could have also been performed at
  // setup(), once after the Scene object have been instantiated
  public void init() {
    nbPart = 2000;
    setShortcut('m', Scene.KeyboardAction.ANIMATION);    
    setAxisIsDrawn(false);
    setAnimationPeriod(40); // 25Hz
    startAnimation();
    smooth();
  }

  // Define here what is actually going to be drawn.
  public void proscenium() {
    parent.pushStyle();
    strokeWeight(3); // Default
    beginShape(POINTS);
    for (int i = 0; i < nbPart; i++) {
      particle[i].draw();
    }
    endShape();
    parent.popStyle();
  }

  // Define here your animation.
  public void animate() {
    for (int i = 0; i < nbPart; i++)
      if(particle[i] != null)
        particle[i].animate();
  }
}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
