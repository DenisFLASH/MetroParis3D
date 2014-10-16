package dao;

import java.util.List;

import model.Station;

public interface DaoMetroGraph {

  List<Station> getNeighbors(int id) throws DAOException;

  List<Station> getTransferStations(int id) throws DAOException;
}
