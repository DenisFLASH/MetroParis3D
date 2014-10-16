package test.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import model.Line;

import org.junit.Test;

public class LineTest {

  @Test
  public void testGetDistance() {
    assertEquals(50, Line.getDistance(30, 40), 0.0);
  }

  @Test
  public void testGetAngle() {
    assertEquals(Math.PI / 4, Line.getAngle(50, 50), 0.01f);
  }

  @Test
  public void testGetRGBColor() {
    int[] expectedColorArray = {255, 255, 0};
    assertArrayEquals(expectedColorArray, Line.getRGBColor("FFFF00"));
  }

  @Test
  public void testConvertGPSToDeltaXY() {
    // 1 | La Défense | 1 | 48.891826754836515 | 2.237992043215619
    // 2 | Esplanade de la Défense | 1 | 48.88835806016618 | 2.249937212862802
    double latitudeStart = 48.891826754836515;
    double longitudeStart = 2.237992043215619;
    double latitude = 48.88835806016618;
    double longitude = 2.249937212862802;

    double[] deltaXY = Line.GPSToXY(latitude, longitude, latitudeStart, longitudeStart, 500);
    System.out.println(deltaXY[0] + " , " + deltaXY[1]);
  }
}
