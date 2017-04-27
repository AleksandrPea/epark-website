//package com.web.apea.parkWebsite.dao.mysql;
//
//import AreaDao;
//import Area;
//import org.junit.*;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class MySqlAreaDaoTest {
//
//    private static final String testAreaName = "area";
//    private static final Integer testAreaId = 1;
//    private static final String testAreaDescription = "aaa";
//    private static Connection connection;
//    private static AreaDao areaDao;
//    private static Area area;
//
//    @BeforeClass
//    public static void openConnection() throws SQLException {
//        connection = MySqlDaoFactory.getInstance().getConnection();
//        areaDao = MySqlDaoFactory.getInstance().getAreaDao(connection);
//        area = new Area(testAreaId, testAreaName);
//        area.setDescription("aaa");
//    }
//
//    @Test
//    public void getByIdTest() throws SQLException {
//        Area area2 = areaDao.getById(testAreaId);
//        Assert.assertEquals(area.getName(), area2.getName());
//        Assert.assertEquals(area.getDescription(), area2.getDescription());
//    }
//
//    @Test
//    public void getByNameTest() throws SQLException {
//        Area area2 = areaDao.getByName(testAreaName);
//        Assert.assertEquals(area.getId(), area2.getId());
//        Assert.assertEquals(area.getDescription(), area2.getDescription());
//    }
//
//    @Test
//    public void updateTest() throws SQLException {
//        Area newArea = new Area(area.getId(), "anotherName");
//        connection.setAutoCommit(false);
//        newArea.setDescription("anotherDescrtiption");
//        areaDao.update(newArea);
//        Area area2 = areaDao.getById(newArea.getId());
//        Assert.assertEquals(newArea.getName(), area2.getName());
//        Assert.assertEquals(newArea.getDescription(), area2.getDescription());
//        connection.rollback();
//        connection.setAutoCommit(true);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void getByIdWithNullTest() throws SQLException {
//        areaDao.getById(null);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void getByNameWithNullTest() throws SQLException {
//        areaDao.getByName(null);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void updateWithNullTest() throws SQLException {
//        areaDao.update(null);
//    }
//
//}