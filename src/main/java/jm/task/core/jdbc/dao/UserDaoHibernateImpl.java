package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * в dependency старая версия hibernate поэтому не работал try with resources из коробки
 *         <dependency>
 *             <groupId>org.hibernate</groupId>
 *             <artifactId>hibernate-core</artifactId>
 *             <version>5.4.26.Final</version>
 *         </dependency>
 */

public class UserDaoHibernateImpl implements UserDao {
    private static UserDaoHibernateImpl instance;

    public UserDaoHibernateImpl() {
    }

    public static UserDaoHibernateImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoHibernateImpl();
        }
        return instance;
    }

    @Override
    public void createUsersTable() {

        String createTable = "CREATE TABLE IF NOT EXISTS Users" +
                "(id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR (45) NOT NULL, " +
                "lastName VARCHAR (45) NOT NULL, " +
                "age INT NOT NULL, " +
                "PRIMARY KEY (id));";

        try (Session session = Util.getSession().openSession()) {

            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(createTable).executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Не удалось создать таблицу");
        }

    }

    @Override
    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS Users;";

        try (Session session = Util.getSession().openSession()) {

            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(dropTable).executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Не удалось удалить таблицу");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSession().openSession()) {

            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);

        } catch (Exception e) {
            System.out.printf("Не удалось добавить в базу пользователя с именем %s", name);
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getSession().openSession()) {

            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Не удалось удалить пользователяиз БД");
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();

        try (Session session = Util.getSession().openSession()) {

            Transaction transaction = session.beginTransaction();
            list = session.createQuery("from User",User.class).list();
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Не удалось получить список User's");
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = Util.getSession().openSession()) {

            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Не удалось очистить таблицу User's");
        }
    }
}
