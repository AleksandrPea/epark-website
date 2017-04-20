package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.domain.Report;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.ReportDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlReportDao implements ReportDao {

    private Connection connection;

    MySqlReportDao(Connection connection) {
        this.connection = connection;
    }

    public Report createOn(Integer taskId) {
        String sqlStatement = "INSERT INTO report (comment, imgPath, taskId) VALUES ('', '', ?)";
        Report report;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, taskId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating report failed.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Creating report failed, no ID obtained.");
            }
            Integer id = generatedKeys.getInt("id");
            report = new Report(id, taskId);
            generatedKeys.close();
        } catch (SQLException e) {
            throw new DaoException("Can't create report", e);
        }
        return report;
    }

    public Report getById(Integer id) {
        String sqlStatement = "SELECT * FROM report WHERE id = ?";
        Report report;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DaoException("Report with id " + id + " doesn't exist");
            }
            String comment = resultSet.getString("comment");
            String imgPath = resultSet.getString("imgPath");
            Integer taskId = resultSet.getInt("taskId");
            report = new Report(id, taskId);
            report.setComment(comment);
            report.setImgPath(imgPath);
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get report", e);
        }
        return report;
    }

    public void update(Report report) {
        String sqlStatement = "UPDATE report SET comment = ?, imgPath = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, report.getComment());
            statement.setString(2, report.getImgPath());
            statement.setInt(3, report.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating report failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't update report", e);
        }
    }

    public void delete(Report report) {
        String sqlStatement = "DELETE FROM report WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, report.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting report failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't delete report", e);
        }
    }

    @Override
    public List<Report> getAllOn(Integer taskId) {
        String sqlStatement = "SELECT * FROM report WHERE taskId = ?";
        List<Report> reports = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            ResultSet resultSet = statement.executeQuery();
            statement.setInt(1, taskId);
            while (resultSet.next()) {
                String comment = resultSet.getString("comment");
                String imgPath = resultSet.getString("imgPath");
                Integer id = resultSet.getInt("id");
                Report report = new Report(id, taskId);
                report.setComment(comment);
                report.setImgPath(imgPath);
                reports.add(report);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return reports;
    }

    @Override
    public int deleteAllOn(Integer taskId) {
        String sqlStatement = "DELETE FROM report WHERE taskId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, taskId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
