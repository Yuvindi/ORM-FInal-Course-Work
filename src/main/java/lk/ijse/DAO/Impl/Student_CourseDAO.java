package lk.ijse.DAO.Impl;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Student_Course;

import java.util.List;

public interface Student_CourseDAO extends CrudDAO<Student_Course> {
    List<String> getAllCourseBatch();
}
