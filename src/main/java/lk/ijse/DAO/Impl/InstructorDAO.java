package lk.ijse.DAO.Impl;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Course;
import lk.ijse.Entity.Instructor;

import java.sql.SQLException;
import java.util.List;

public interface InstructorDAO extends CrudDAO<Instructor> {
    Instructor searchByEmail(String id) throws SQLException, ClassNotFoundException;

    List<String> getEmails();
}
