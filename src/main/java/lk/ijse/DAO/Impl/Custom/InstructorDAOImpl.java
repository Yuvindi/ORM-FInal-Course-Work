package lk.ijse.DAO.Impl.Custom;

import lk.ijse.DAO.Impl.InstructorDAO;
import lk.ijse.Entity.Instructor;
import lk.ijse.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class InstructorDAOImpl implements InstructorDAO {
    @Override
    public boolean save(Instructor entity) throws Exception {
        System.out.println("DAO............: " + entity);
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        session.save(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Instructor entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        session.update(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String ID) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        Instructor instructor = new Instructor();
        instructor.setInstructorId(Integer.parseInt(ID));
        session.delete(instructor);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public List<Instructor> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Instructor> all = session.createQuery("from Instructor").list();
        transaction.commit();
        session.close();
        return all;
    }

    @Override
    public Instructor searchByEmail(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Instructor instructor = session.createQuery("FROM Instructor WHERE instructorEmail = :instructorEmail", Instructor.class).setParameter("instructorEmail",id)
                .uniqueResult();
        transaction.commit();
        session.close();
        return instructor;
    }
    @Override
    public Instructor searchByID(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public List<String> getIds() {
        return List.of();
    }

    @Override
    public List<String> getEmails() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<String> all = session.createQuery("SELECT i.instructorEmail From Instructor i",String.class).list();
        transaction.commit();
        session.close();
        return all;
    }
}
