package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Station;

public class DaoStationImpl implements DaoStation {
  private static final String SQL_SELECT_PAR_ID =
      "SELECT id, label, id_line, latitude, longitude FROM station WHERE id = ?";

  private DAOFactory daoFactory;

  DaoStationImpl(DAOFactory daoFactory) {
    this.daoFactory = daoFactory;
  }

  @Override
  public Station getStationById(int id) throws DAOException {
    return find(SQL_SELECT_PAR_ID, id);
  }

  /*
   * Méthode générique utilisée pour retrouver une station depuis la base de données, correspondant
   * à la requête SQL donnée prenant en paramètres les objets passées en argument.
   */
  public Station find(String sql, Object... objets) throws DAOException {
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
      preparedStatement = DAOUtil.initialisationRequetePreparee(connection, sql, false, objets);
      resultSet = preparedStatement.executeQuery();
      /* Parcours de la ligne de données retournér dans le ResultSet */
      if (resultSet.next()) {
        station = map(resultSet);
      }
    } catch (SQLException e) {
      throw new DAOException("Erreur dans la méthode 'find()'. " + e.getMessage());
    } finally {
      DAOUtil.fermetureSilencieuse(resultSet, preparedStatement, connection);
    }

    return station;
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
