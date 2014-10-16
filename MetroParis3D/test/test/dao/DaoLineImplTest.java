package test.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import model.Line;

import org.junit.Test;

import dao.DAOException;
import dao.DAOFactory;
import dao.DaoLine;

public class DaoLineImplTest {

  @Test
  public void testGetLineById_LineName() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoLine daoLine = factory.getDaoLine();
    Line line = daoLine.getLineById(1);
    assertEquals("Ligne 1", line.getName());
  }

  @Test
  public void testGetLineById_LineColor() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoLine daoLine = factory.getDaoLine();
    Line line = daoLine.getLineById(2);
    assertEquals("0000FF", line.getColor());
  }

  @Test
  public void testGetLineById_Stations() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoLine daoLine = factory.getDaoLine();
    Line line = daoLine.getLineById(1);
    assertEquals(25, line.getStations().size());
  }

  @Test
  public void testGetAllLines() throws DAOException {
    DAOFactory factory = DAOFactory.getInstance();
    DaoLine daoLine = factory.getDaoLine();
    List<Line> lines = daoLine.getAllLines();
    System.out.println(lines);
    assertEquals(16, lines.size());
  }
}
