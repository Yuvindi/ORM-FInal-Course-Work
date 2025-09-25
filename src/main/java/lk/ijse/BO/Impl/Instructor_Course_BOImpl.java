package lk.ijse.BO.Impl;

import lk.ijse.BO.Instructor_Course_BO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.Impl.Instructor_Course_DAO;
import lk.ijse.DTO.Instructor_Course_DTO;
import lk.ijse.Entity.Instructor;
import lk.ijse.Entity.Instructor_Course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Instructor_Course_BOImpl implements Instructor_Course_BO {
    Instructor_Course_DAO instructorCourseDAO = (Instructor_Course_DAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DaoType.Instructor_Course);
    @Override
    public boolean save(Instructor_Course_DTO dto) throws Exception {
        return instructorCourseDAO.save(new Instructor_Course(
                dto.getId(),
               new Instructor(
                          dto.getInstructor().getInstructorId(),
                          dto.getInstructor().getInstructorName(),
                          dto.getInstructor().getInstructorEmail(),
                          dto.getInstructor().getInstructorPhone(),
                          dto.getInstructor().getInstructorAddress(),
                          new ArrayList<>()
               ),
                dto.getCourseName(),
                dto.getCourseDate()
        ));
    }

    @Override
    public boolean update(Instructor_Course_DTO dto) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String ID) throws Exception {
        System.out.println("BO ID: " + ID);
        return instructorCourseDAO.delete(ID);
    }

    @Override
    public List<Instructor_Course_DTO> getAll() throws Exception {
        return List.of();
    }
}
