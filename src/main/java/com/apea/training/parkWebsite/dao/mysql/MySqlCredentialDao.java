package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.connection.mysql.MySqlTransactionHelper;
import com.apea.training.parkWebsite.dao.CredentialDao;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.domain.Credential;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlCredentialDao implements CredentialDao {

    MySqlCredentialDao() {
    }

    @Override
    public void create(Credential credential) {
        String sqlStatement = "INSERT INTO credential (userId, login, password) VALUES (?,?,?)";
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, credential.getUserId());
            statement.setString(2, credential.getLogin());
            statement.setString(3, credential.getPassword());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating credential failed: no rows affected.");
            }
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't create credential", e);
        }
    }

    @Override
    public Credential getByUserId(Integer userId) {
        String sqlStatement = "SELECT * FROM credential WHERE userId = ?";
        Credential credential;
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DaoException("Credential with user id " + userId + " don't exist");
            }
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            credential = new Credential(userId, login, password);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get credential", e);
        }
        return credential;
    }

    @Override
    public Credential getByLogin(String login) {
        String sqlStatement = "SELECT * FROM credential WHERE login = ?";
        Credential credential = null;
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer userId = resultSet.getInt("userId");
                String password = resultSet.getString("password");
                credential = new Credential(userId, login, password);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get credential", e);
        }
        return credential;
    }

    @Override
    public List<Credential> getAll() {
        String sqlStatement = "SELECT * FROM credential";
        List<Credential> credentialList = new ArrayList<>();
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                Integer userId = resultSet.getInt("userId");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                Credential credential = new Credential(userId, login, password);
                credentialList.add(credential);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get all credentials", e);
        }
        return credentialList;
    }

    @Override
    public void update(Credential credential) {
        String sqlStatement = "UPDATE credential SET login = ?, password = ? WHERE userId = ?";
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, credential.getLogin());
            statement.setString(2, credential.getPassword());
            statement.setInt(3, credential.getUserId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoException("Updating credential failed: no rows affected.");
            }
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't update credential", e);
        }
    }
}