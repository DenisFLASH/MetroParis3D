package test.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import model.Station;

import org.junit.Test;

import dao.DAOException;
import dao.DAOFactory;
import dao.DaoMetroGraph;

public class DaoMetroGraphImplTest {

  @Test
  public void testGetNeighbors_NormalStation() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> neighbors = daoMetroGraph.getNeighbors(231); // Marcel Sembat (9)
    System.out.println(neighbors);
    assertEquals(2, neighbors.size());
  }

  @Test
  public void testGetNeighbors_Terminus() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> neighbors = daoMetroGraph.getNeighbors(371); // Olympiades (14)
    System.out.println(neighbors);
    assertEquals(1, neighbors.size());
  }

  @Test
  public void testGetNeighbors_Fork() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> neighbors = daoMetroGraph.getNeighbors(181); // Maison Blanche (7)
    System.out.println(neighbors);
    assertEquals(3, neighbors.size());
  }

  @Test
  public void testGetNeighbors_Fork2() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> neighbors = daoMetroGraph.getNeighbors(345); // La Fourche (13)
    System.out.println(neighbors);
    assertEquals(3, neighbors.size());
  }

  @Test
  public void testGetNeighbors_OneSidedFork() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> neighbors = daoMetroGraph.getNeighbors(274); // Javel - Andre Citroen(10)
    System.out.println(neighbors);
    assertEquals(2, neighbors.size());
  }

  @Test
  public void testGetNeighbors_InLoop() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> neighbors = daoMetroGraph.getNeighbors(273); // Porte d'Auteuil (10)
    System.out.println(neighbors);
    assertEquals(1, neighbors.size());
  }

  @Test
  public void testGetNeighbors_7bisFork() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> neighbors = daoMetroGraph.getNeighbors(380); // Botzaris (7bis)
    System.out.println(neighbors);
    assertEquals(2, neighbors.size());
  }

  @Test
  public void testGetNeighbors_7bisLoop() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> neighbors = daoMetroGraph.getNeighbors(383); // Pré-Saint-Gervais (7bis)
    System.out.println(neighbors);
    assertEquals(1, neighbors.size());
  }

  @Test
  public void testGetTransferStations_Zero() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> transferStations = daoMetroGraph.getTransferStations(215); // Montgallet (8)
    System.out.println(transferStations);
    assertEquals(0, transferStations.size());
  }

  @Test
  public void testGetTransferStations_One() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> transferStations = daoMetroGraph.getTransferStations(81); // Gare du Nord (4)
    System.out.println(transferStations);
    assertEquals(1, transferStations.size());
  }

  @Test
  public void testGetTransferStations_Two() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> transferStations = daoMetroGraph.getTransferStations(7); // Charles de
                                                                           // Gaulle - Etoile (1)
    System.out.println(transferStations);
    assertEquals(2, transferStations.size());
  }

  @Test
  public void testGetTransferStations_Three() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> transferStations = daoMetroGraph.getTransferStations(136); // Montparnasse-Bienvenue
                                                                             // (6)
    System.out.println(transferStations);
    assertEquals(3, transferStations.size());
  }

  @Test
  public void testGetTransferStations_Four() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> transferStations = daoMetroGraph.getTransferStations(366); // Chatelet (14)
    System.out.println(transferStations);
    assertEquals(4, transferStations.size());
  }

  @Test
  public void testGetTransferStations_SaintAugustin() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> transferStations = daoMetroGraph.getTransferStations(246); // Saint-Augustin (9)
    System.out.println(transferStations);
    assertEquals(1, transferStations.size());
  }

  @Test
  public void testGetTransferStations_SaintLazare14() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> transferStations = daoMetroGraph.getTransferStations(363); // Saint-Lazare (14)
    System.out.println(transferStations);
    assertEquals(4, transferStations.size());
  }

  @Test
  public void testGetTransferStations_SaintLazare3() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoMetroGraph daoMetroGraph = factory.getDaoMetroGraph();
    List<Station> transferStations = daoMetroGraph.getTransferStations(60); // Saint-Lazare (3)
    System.out.println(transferStations);
    assertEquals(3, transferStations.size());
  }
}
