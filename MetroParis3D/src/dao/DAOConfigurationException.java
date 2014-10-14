package dao;

public class DAOConfigurationException extends Exception {

  public DAOConfigurationException(String message) {
    super(message);
  }

  public DAOConfigurationException(Throwable cause) {
    super(cause);
  }

  public DAOConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }
}
