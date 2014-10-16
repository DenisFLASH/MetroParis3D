package test.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import model.Station;

import org.junit.Test;

import dao.DAOException;
import dao.DAOFactory;
import dao.DaoStation;

public class DaoStationImplTest {

  @Test
  public void testGetStationById() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoStation daoStation = factory.getDaoStation();
    Station station = daoStation.getStationById(1);
    assertEquals("La Défense", station.getName());
  }

  @Test
  public void testGetStationsByLineId() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoStation daoStation = factory.getDaoStation();
    List<Station> stations = daoStation.getStationsByLineId(1);
    assertEquals(25, stations.size());
  }

  // @Test
  // public void testGetTransferStations() throws DAOException {
  // DAOFactory factory = DAOFactory.getInstance();
  // DaoStation daoStation = factory.getDaoStation();
  // List<Station> transferStations = daoStation.getTransferStations(21);
  // System.out.println(transferStations);
  // assertEquals(50, transferStations.get(0).getId());
  // }
  //
  // @Test
  // public void testGetTransferStations_NoTransfers() throws DAOException {
  // DAOFactory factory = DAOFactory.getInstance();
  // DaoStation daoStation = factory.getDaoStation();
  // List<Station> transferStations = daoStation.getTransferStations(3);
  // System.out.println(transferStations);
  // assertEquals(0, transferStations.size());
  // }
}
