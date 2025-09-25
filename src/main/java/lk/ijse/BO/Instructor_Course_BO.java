package lk.ijse.BO;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.DTO.InstructorDTO;
import lk.ijse.DTO.Instructor_Course_DTO;
import lk.ijse.Entity.Instructor_Course;

import java.util.List;

public interface Instructor_Course_BO extends SuperBO {
    public boolean save(Instructor_Course_DTO dto) throws Exception;

    public boolean update(Instructor_Course_DTO dto) throws Exception;

    public boolean delete(String ID)throws Exception;

    public List<Instructor_Course_DTO> getAll() throws Exception;
}
