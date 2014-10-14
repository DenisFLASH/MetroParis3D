package dao;

import model.Station;

public interface DaoStation {

  Station getStationById(int id) throws DAOException;

}
