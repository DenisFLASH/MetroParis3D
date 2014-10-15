package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Station;

public class DaoStationImpl implements DaoStation {

  private static final String SQL_SELECT_PAR_ID = "SELECT * FROM station WHERE id = ?";
  private static final String SQL_SELECT_PAR_LINE_ID = "SELECT * FROM station WHERE id_line = ?";

  private DAOFactory daoFactory;

  DaoStationImpl(DAOFactory daoFactory) {
    this.daoFactory = daoFactory;
  }

  @Override
  public Station getStationById(int id) throws DAOException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Station station = null;

    try {
      connection = daoFactory.getConnection();
      /*
       * Préparation de la requête avec les objets passés en argument (ici, uniquement un id) et
       * exécution
       */
      preparedStatement =
          DAOUtil.initialisationRequetePreparee(connection, SQL_SELECT_PAR_ID, false, id);
      resultSet = preparedStatement.executeQuery();
      /* Parcours de la ligne de données retournér dans le ResultSet */
      if (resultSet.next()) {
        station = map(resultSet);
      }
    } catch (SQLException e) {
      throw new DAOException("Erreur dans la méthode 'getStationById()'. " + e.getMessage());
    } finally {
      DAOUtil.fermetureSilencieuse(resultSet, preparedStatement, connection);
    }

    return station;
  }

  @Override
  public List<Station> getStationsByLineId(int lineId) throws DAOException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Station> stations = new ArrayList<Station>();
    Station station = null;

    try {
      connection = daoFactory.getConnection();
      preparedStatement =
          DAOUtil.initialisationRequetePreparee(connection, SQL_SELECT_PAR_LINE_ID, false, lineId);
      resultSet = preparedStatement.executeQuery();

      /* Parcours de la ligne de données retournér dans le ResultSet */
      while (resultSet.next()) {
        station = map(resultSet);
        stations.add(station);
      }
    } catch (SQLException e) {
      throw new DAOException("Erreur dans la méthode 'getStationById()'. " + e.getMessage());
    } finally {
      DAOUtil.fermetureSilencieuse(resultSet, preparedStatement, connection);
    }

    return stations;
  }

  /*
   * Simple méthode utilitaire permettant de faire la correspondance (le mapping) entre une ligne
   * issue de la table Station (un ResultSet) et un objet Station.
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
