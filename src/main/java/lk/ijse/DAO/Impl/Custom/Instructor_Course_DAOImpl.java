package lk.ijse.DAO.Impl.Custom;

import lk.ijse.DAO.Impl.Instructor_Course_DAO;
import lk.ijse.Entity.Instructor_Course;
import lk.ijse.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class Instructor_Course_DAOImpl implements Instructor_Course_DAO {
    @Override
    public boolean save(Instructor_Course entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        session.save(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Instructor_Course entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String ID) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        Instructor_Course instructor_course = session.get(Instructor_Course.class, Integer.parseInt(ID));
        if (instructor_course != null) {
            session.delete(instructor_course);
            tx.commit();
            session.close();
            return true;
        } else {
            tx.rollback();
            session.close();
            return false;
        }
    }


    @Override
    public List<Instructor_Course> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Instructor_Course> all = session.createQuery("from Instructor_Course").list();
        transaction.commit();
        session.close();
        return all;
    }

    @Override
    public Instructor_Course searchByID(String id) throws SQLException, ClassNotFoundException {
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
}
