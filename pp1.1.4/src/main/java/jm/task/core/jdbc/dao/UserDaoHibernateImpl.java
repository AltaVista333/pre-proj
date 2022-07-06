package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = factory.openSession();
        session.beginTransaction();
        Query sqlQuery = session.createSQLQuery("""
                CREATE TABLE IF NOT EXISTS `Users`
                (
                    `id`        BIGINT      NOT NULL AUTO_INCREMENT,
                    `name`      VARCHAR(15) NOT NULL,
                    `last_name` VARCHAR(15) NOT NULL,
                    `age`       TINYINT     NOT NULL,
                    PRIMARY KEY (`id`)
                )
                """);
        sqlQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = factory.openSession();
        session.beginTransaction();
        Query sqlQuery = session.createSQLQuery("DROP TABLE IF EXISTS Users");
        sqlQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(
                User.builder().name(name).lastName(lastName).age(age).build());
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = factory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User");
        List<User> list = query.list();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.openSession();
        session.createSQLQuery("truncate Users").executeUpdate();
        session.close();
    }
}
