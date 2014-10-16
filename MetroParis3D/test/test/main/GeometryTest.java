package test.main;

import static org.junit.Assert.assertEquals;
import main.Geometry;
import model.Station;

import org.junit.Test;

import dao.DAOException;
import dao.DAOFactory;
import dao.DaoStation;

public class GeometryTest {

  @Test
  public void testGetDistance() {
    assertEquals(50, Geometry.getDistance(30, 40), 0.0);
  }

  @Test
  public void testGetAngle() {
    assertEquals(Math.PI / 4, Geometry.getAngle(50, 50), 0.01f);
  }

  @Test
  public void testGPSToDeltaXY() {
    // 1 | La Défense | 1 | 48.891826754836515 | 2.237992043215619
    // 2 | Esplanade de la Défense | 1 | 48.88835806016618 | 2.249937212862802
    double latitudeStart = 48.891826754836515;
    double longitudeStart = 2.237992043215619;
    double latitude = 48.88835806016618;
    double longitude = 2.249937212862802;

    double[] deltaXY = Geometry.GPSToKm(latitude, longitude, latitudeStart, longitudeStart);
    System.out.println(deltaXY[0] + " km , " + deltaXY[1] + " km");
  }

  @Test
  public void testGetDistanceBetweenTwoStations() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoStation daoStation = factory.getDaoStation();
    Station station1 = daoStation.getStationById(1); // La Défense (1)
    Station station2 = daoStation.getStationById(2); // Esplanade de la Défense (1)

    System.out.println(Geometry.getDistanceBetweenTwoStations(station1, station2) + " km");
    System.out.println(Geometry.getDistanceBetweenTwoStations(station2, station1) + " km");
  }
}
