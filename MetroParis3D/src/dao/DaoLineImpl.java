package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Line;
import model.Station;

public class DaoLineImpl implements DaoLine {

  private static final String SQL_SELECT_LINE_PAR_ID = "SELECT * FROM line WHERE id = ?";
  private static final String SQL_SELECT_ALL_LINES = "SELECT * FROM line";

  private DAOFactory daoFactory;

  DaoLineImpl(DAOFactory daoFactory) {
    this.daoFactory = daoFactory;
  }

  /**
   * Instancie l'objet Line à partir d'une ligne dans la table Line (id, label, color). Ensuite,
   * affecte la liste des stations en faisant appel à la méthode getStationsByLineId.
   */
  @Override
  public Line getLineById(int id) throws DAOException {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Line line = null;

    try {
      connection = daoFactory.getConnection();
      preparedStatement =
          DAOUtil.initialisationRequetePreparee(connection, SQL_SELECT_LINE_PAR_ID, false, id);
      resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        line = map(resultSet);
      }

      DaoStation daoStation = daoFactory.getDaoStation();
      List<Station> stations = daoStation.getStationsByLineId(line.getId());
      line.setStations(stations);

    } catch (SQLException e) {
      throw new DAOException("Erreur dans la méthode 'getLineById()'. " + e.getMessage());
    } finally {
      DAOUtil.fermetureSilencieuse(resultSet, preparedStatement, connection);
    }

    return line;
  }

  @Override
  public List<Line> getAllLines() throws DAOException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Line> lines = new ArrayList<Line>();
    Line line = null;
    DaoStation daoStation = daoFactory.getDaoStation();

    try {
      connection = daoFactory.getConnection();
      preparedStatement =
          DAOUtil.initialisationRequetePreparee(connection, SQL_SELECT_ALL_LINES, false);
      resultSet = preparedStatement.executeQuery();

      /* Parcours de la ligne de données retournér dans le ResultSet */
      while (resultSet.next()) {
        line = map(resultSet);
        List<Station> stations = daoStation.getStationsByLineId(line.getId());
        line.setStations(stations);
        lines.add(line);
      }
    } catch (SQLException e) {
      throw new DAOException("Erreur dans la méthode 'getStationById()'. " + e.getMessage());
    } finally {
      DAOUtil.fermetureSilencieuse(resultSet, preparedStatement, connection);
    }

    return lines;
  }

  /*
   * Simple méthode utilitaire permettant de faire la correspondance (le mapping) entre une ligne
   * issue de la table Station (un ResultSet) et un objet Station.
   */
  public static Line map(ResultSet resultSet) throws SQLException {
    Line line = new Line();
    int lineId = resultSet.getInt("id");
    line.setId(lineId);
    line.setName(resultSet.getString("label"));
    line.setColor(resultSet.getString("color"));
    return line;
  }

}
