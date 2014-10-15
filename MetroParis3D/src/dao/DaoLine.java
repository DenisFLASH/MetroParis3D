package dao;

import java.util.List;

import model.Line;

public interface DaoLine {

  Line getLineById(int id) throws DAOException;

  List<Line> getAllLines() throws DAOException;
}
