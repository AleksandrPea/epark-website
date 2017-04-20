//package com.web.apea.parkWebsite.dao.mysql;
//
//import UserDao;
//import User;
//import org.junit.*;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class MySqlUserDaoTest {
//
//    private static Connection connection;
//    private static UserDao userDao;
//    private static String login;
//    private User user = null;
//
//    @BeforeClass
//    public static void openConnection() throws SQLException {
//        connection = MySqlDaoFactory.getInstance().getDaoConnection();
//        userDao = MySqlDaoFactory.getInstance().getUserDao(connection);
//        login = "" + System.currentTimeMillis();
//    }
//
//    @Before
//    public void createUser() throws SQLException {
//        if (user == null) {
//            user = new User(login, "bbb");
//            userDao.persistNew(user);
//        }
//    }
//
//    @After
//    public void removeUser() throws SQLException {
//        if (user != null) {
//            userDao.deleteByLogin(login);
//        }
//    }
//
//    @Test
//    public void getByLoginTest() {
//        User user2 = userDao.getByLogin(login);
//        Assert.assertEquals("bbb", user2.getPassword());
//    }
//
//    @Test
//    public void updateTest() throws SQLException {
//        user.setPassword("bbbs");
//        userDao.update(user);
//        User user2 = userDao.getByLogin(login);
//        Assert.assertEquals("bbbs", user2.getPassword());
//    }
//
//    @Test
//    public void deleteByLoginTest() throws SQLException {
//        userDao.deleteByLogin(user.getLogin());
//        user = userDao.getByLogin(user.getLogin());
//        Assert.assertNull(user);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void persistWithNullTest() throws SQLException {
//        userDao.persistNew(null);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void getWithNullTest() {
//        userDao.getByLogin(null);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void updateWithNullTest() throws SQLException {
//        userDao.update(null);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void deleteWithNullTest() throws SQLException {
//        userDao.deleteByLogin(null);
//    }
//
//    @AfterClass
//    public static void closeConnection() {
//        try {
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();  // LOGGER
//        }
//    }
//}