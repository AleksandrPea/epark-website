package com.web.apea.parkWebsite.dao.mysql;

import com.web.apea.parkWebsite.dao.DaoException;
import com.web.apea.parkWebsite.dao.ReportDao;
import com.web.apea.parkWebsite.domain.Report;

import java.sql.*;

public class MySqlReportDao implements ReportDao {

    private Connection connection;

    MySqlReportDao(Connection connection) {
        this.connection = connection;
    }

    public Report create() {
        Report report;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO report " +
                    "(comment, imgPath) VALUES ('', '')", Statement.RETURN_GENERATED_KEYS);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating report failed.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Creating report failed, no ID obtained.");
            }
            Integer id = generatedKeys.getInt("id");
            generatedKeys.close();
            report = new Report(id);
            report.setComment("");
            report.setImgPath("");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return report;
    }

    public Report getById(Integer id) {
        Report report;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM report " +
                    "WHERE id="+id);
            if (!resultSet.next()) {
                throw new DaoException("Report with id " + id + " doesn't exist");
            }
            String comment = resultSet.getString("comment");
            String imgPath = resultSet.getString("imgPath");
            report = new Report(id);
            report.setComment(comment);
            report.setImgPath(imgPath);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return report;
    }

    public void update(Report report) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE report " +
                    "SET comment = ?, imgPath = ? WHERE id = ?");
            statement.setString(1, report.getComment());
            statement.setString(2, report.getImgPath());
            statement.setInt(3, report.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating report failed.");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void delete(Report report) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM report " +
                    "WHERE id = ?");
            statement.setInt(1, report.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting report failed.");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
