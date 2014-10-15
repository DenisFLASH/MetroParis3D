package dao;

import java.util.List;

import model.Station;

public interface DaoStation {

  Station getStationById(int id) throws DAOException;

  List<Station> getStationsByLineId(int lineId) throws DAOException;
  
  List<Station> getAllStations() throws DAOException;

}
