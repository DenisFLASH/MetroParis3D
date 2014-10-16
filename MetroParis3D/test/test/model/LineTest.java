package test.model;

import static org.junit.Assert.assertArrayEquals;
import model.Line;

import org.junit.Test;

public class LineTest {

  @Test
  public void testGetRGBColor() {
    int[] expectedColorArray = {255, 255, 0};
    assertArrayEquals(expectedColorArray, Line.getRGBColor("FFFF00"));
  }

}
