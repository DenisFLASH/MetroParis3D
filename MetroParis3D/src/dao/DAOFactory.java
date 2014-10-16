package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
  private static final String FILE_PROPERTIES = "dao/dao.properties";
  private static final String PROPERTY_URL = "url";
  private static final String PROPERTY_DRIVER = "driver";
  private static final String PROPERTY_USER = "user";
  private static final String PROPERTY_PASS = "password";

  private String url;
  private String user;
  private String password;

  DAOFactory(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  /*
   * Récupère les informations de connexion à la base de données, charge le driver JDBC et retourne
   * une instance de la Factory.
   */
  public static DAOFactory getInstance() throws DAOException {
    Properties properties = new Properties();
    String url;
    String driver;
    String user;
    String password;

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream fileProperties = classLoader.getResourceAsStream(FILE_PROPERTIES);

    if (fileProperties == null) {
      throw new DAOException("Le fichier properties " + FILE_PROPERTIES + " est introuvable.");
    }

    try {
      properties.load(fileProperties);
      url = properties.getProperty(PROPERTY_URL);
      driver = properties.getProperty(PROPERTY_DRIVER);
      user = properties.getProperty(PROPERTY_USER);
      password = properties.getProperty(PROPERTY_PASS);
    } catch (IOException e) {
      throw new DAOException("Impossible de charger le fichier properties " + FILE_PROPERTIES, e);
    }

    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      throw new DAOException("Le driver est introuvable dans le classpath.", e);
    }

    /*
     * Enrégistrement du pool créé dans une variable d'instance via un appel au constructeur de
     * DAOFactory.
     */
    DAOFactory instance = new DAOFactory(url, user, password);
    return instance;
  }

  /* Méthode chargée de fournir une connexion à la base de données. */
  /* package */Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }

  /*
   * Méthodes de récupération de l'implémentation des différents DAO
   */
  public DaoStation getDaoStation() {
    return new DaoStationImpl(this);
  }

  public DaoLine getDaoLine() {
    return new DaoLineImpl(this);
  }

  public DaoMetroGraph getDaoMetroGraph() {
    return new DaoMetroGraphImpl(this);
  }
}
