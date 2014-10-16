package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Station;

public class DaoMetroGraphImpl implements DaoMetroGraph {

  private static final String SQL_SELECT_ALL_NEIGHBORS =
      "SELECT * FROM neighbors WHERE id_station = ?";
  private static final String SQL_SELECT_ALL_TRANSFERS =
      "SELECT * FROM transfer WHERE id_station = ?";

  private DAOFactory daoFactory;

  DaoMetroGraphImpl(DAOFactory daoFactory) {
    this.daoFactory = daoFactory;
  }


  @Override
  public List<Station> getNeighbors(int id) throws DAOException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    List<Station> neighbors = new ArrayList<Station>();
    Station neighbor = null;
    DaoStation daoStation = daoFactory.getDaoStation();

    try {
      connection = daoFactory.getConnection();
      preparedStatement =
          DAOUtil.initialisationRequetePreparee(connection, SQL_SELECT_ALL_NEIGHBORS, false, id);
      resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        int idNeighbor = resultSet.getInt("id_station_neighbor");
        neighbor = daoStation.getStationById(idNeighbor);
        neighbors.add(neighbor);
      }
    } catch (SQLException e) {
      throw new DAOException("Erreur dans la méthode 'getStationById()'. " + e.getMessage());
    } finally {
      DAOUtil.fermetureSilencieuse(resultSet, preparedStatement, connection);
    }

    return neighbors;
  }

  @Override
  public List<Station> getTransferStations(int id) throws DAOException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    List<Station> transfers = new ArrayList<Station>();
    Station transfer = null;
    DaoStation daoStation = daoFactory.getDaoStation();

    try {
      connection = daoFactory.getConnection();
      preparedStatement =
          DAOUtil.initialisationRequetePreparee(connection, SQL_SELECT_ALL_TRANSFERS, false, id);
      resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        int idTransferStation = resultSet.getInt("id_station_transfer");
        transfer = daoStation.getStationById(idTransferStation);
        transfers.add(transfer);
      }
    } catch (SQLException e) {
      throw new DAOException("Erreur dans la méthode 'getStationById()'. " + e.getMessage());
    } finally {
      DAOUtil.fermetureSilencieuse(resultSet, preparedStatement, connection);
    }

    return transfers;
  }

  /*
   * Mapping entre une ligne
   */
  public static Station map(ResultSet resultSet) throws SQLException {
    Station station = new Station();

    station.setId(resultSet.getInt("id"));
    station.setName(resultSet.getString("label"));
    station.setLine(resultSet.getInt("id_line"));
    station.setLatitude(resultSet.getDouble("latitude"));
    station.setLongitude(resultSet.getDouble("longitude"));

    return station;
  }

}
