//package jm.task.core.jdbc.dao;
//
//import jm.task.core.jdbc.model.User;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.List;
//
//public class UserDaoHibernateImpl implements UserDao {
//
//    private SessionFactory sessionFactory;
//
//    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public void createUsersTable() {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
//                                   "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
//                                   "name VARCHAR(50)," +
//                                   "last_name VARCHAR(50)," +
//                                   "age TINYINT" +
//                                   ")").executeUpdate();
//            transaction.commit();
//            System.out.println("Таблица 'users' успешно создана или уже существует.");
//        } catch (Exception e) {
//            throw new RuntimeException("Ошибка при создании таблицы", e);
//        }
//
//    }
//
//    @Override
//    public void dropUsersTable() {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
//            transaction.commit();
//            System.out.println("Таблица 'users' успешно удалена.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void saveUser(String name, String lastName, byte age) {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            User user = new User(name, lastName, age);
//            session.save(user);
//            transaction.commit();
//            System.out.println("Пользователь '" + name + "' успешно добавлен.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void removeUserById(long id) {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            User user = session.get(User.class, id);
//            if (user != null) {
//                session.delete(user);
//                System.out.println("Пользователь с ID " + id + " успешно удален.");
//            } else {
//                System.out.println("Пользователь с ID " + id + " не найден.");
//            }
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        try (Session session = sessionFactory.openSession()) {
//            return session.createQuery("FROM User", User.class).list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public void cleanUsersTable() {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.createSQLQuery("DELETE FROM users").executeUpdate();
//            transaction.commit();
//            System.out.println("Таблица 'users' успешно очищена.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
