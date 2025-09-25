package lk.ijse.BO;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.DAO.Impl.InstructorDAO;
import lk.ijse.DTO.InstructorDTO;
import lk.ijse.DTO.PaymentDTO;
import lk.ijse.Entity.Instructor;

import java.util.List;

public interface InstructorBO extends SuperBO {
    public boolean save(InstructorDTO dto) throws Exception;

    public boolean update(InstructorDTO dto) throws Exception;

    public boolean delete(String ID)throws Exception;

    public List<InstructorDTO> getAll() throws Exception;
}
