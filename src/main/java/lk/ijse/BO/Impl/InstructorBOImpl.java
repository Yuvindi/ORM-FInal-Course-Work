package lk.ijse.BO.Impl;

import lk.ijse.BO.InstructorBO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.Impl.InstructorDAO;
import lk.ijse.DTO.InstructorDTO;
import lk.ijse.Entity.Instructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructorBOImpl implements InstructorBO {
    InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DaoType.Instructor);

    @Override
    public boolean save(InstructorDTO dto) throws Exception {
        return instructorDAO.save(new Instructor(dto.getInstructorId(), dto.getInstructorName(), dto.getInstructorEmail(), dto.getInstructorPhone(), dto.getInstructorAddress(),new ArrayList<>()));
    }

    @Override
    public boolean update(InstructorDTO dto) throws Exception {
        return instructorDAO.update(new Instructor(dto.getInstructorId(), dto.getInstructorName(), dto.getInstructorEmail(), dto.getInstructorPhone(), dto.getInstructorAddress(),new ArrayList<>()));
    }

    @Override
    public boolean delete(String ID) throws Exception {
        return instructorDAO.delete(ID);
    }

    @Override
    public List<InstructorDTO> getAll() throws Exception {
        return List.of();
    }
}
