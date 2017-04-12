package com.web.apea.parkWebsite.mysql;

import com.web.apea.parkWebsite.dao.ReportDao;
import com.web.apea.parkWebsite.domain.Report;
import com.web.apea.parkWebsite.domain.User;

import java.sql.*;

public class MySqlReportDao implements ReportDao {

    private Connection connection;

    MySqlReportDao(Connection connection) {
        this.connection = connection;
    }

    public Report create() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO report " +
                "(comment, imgPath) VALUES ('', '')", Statement.RETURN_GENERATED_KEYS);
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("Creating report failed, no ID obtained.");
        }
        Integer id = generatedKeys.getInt("id");
        generatedKeys.close();
        Report report = new Report(id);
        report.setComment("");
        report.setImgPath("");
        return report;
    }

    public Report getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("null id");
        }
        Report report = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM report " +
                    "WHERE id="+id);
            if (resultSet.next()) {
                String comment = resultSet.getString("comment");
                String imgPath = resultSet.getString("imgPath");
                report = new Report(id);
                report.setComment(comment);
                report.setImgPath(imgPath);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // LOGGER
        }
        return report;
    }

    public boolean update(Report report) {
        if (report == null) {
            throw new IllegalArgumentException("null report");
        }
        boolean isUpdated = false;
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE report " +
                    "SET comment = ?, imgPath = ? WHERE id = ?");
            statement.setString(1, report.getComment());
            statement.setString(2, report.getImgPath());
            statement.setInt(3, report.getId());
            isUpdated = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();  // LOGGER
        }
        return isUpdated;
    }

    public boolean delete(Report report) {
        if (report == null) {
            throw new IllegalArgumentException("null report");
        }
        boolean isDeleted = false;
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM report " +
                    "WHERE id = ?");
            statement.setInt(1, report.getId());
            isDeleted = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();  // LOGGER
        }
        return isDeleted;
    }
}
