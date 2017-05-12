package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.mysql.MySqlTransactionHelper;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.ServiceException;
import com.apea.training.parkWebsite.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    UserServiceImpl() {}

    @Override
    public void create(User user, Credential credential) {
        MySqlTransactionHelper.getInstance().beginTransaction();
        try {
            MySqlDaoFactory.getInstance().getUserDao().create(user);
            credential.setUserId(user.getId());
            MySqlDaoFactory.getInstance().getCredentialDao().create(credential);
            MySqlTransactionHelper.getInstance().commitTransaction();
        } catch (DaoException e) {
            MySqlTransactionHelper.getInstance().rollbackTransaction();
            throw new ServiceException("Transaction failed.", e);
        }
    }

    @Override
    public User getById(Integer id) {
        return MySqlDaoFactory.getInstance().getUserDao().getById(id);
    }

    @Override
    public User getByLogin(String login) {
        return MySqlDaoFactory.getInstance().getUserDao().getByLogin(login);
    }

    @Override
    public void update(User user, Credential credential) {
        MySqlTransactionHelper.getInstance().beginTransaction();
        try {
            MySqlDaoFactory.getInstance().getUserDao().update(user);
            MySqlDaoFactory.getInstance().getCredentialDao().update(credential);
            MySqlTransactionHelper.getInstance().commitTransaction();
        } catch (DaoException e) {
            MySqlTransactionHelper.getInstance().rollbackTransaction();
            throw new ServiceException("Transaction failed.", e);
        }
    }

    @Override
    public void delete(User user) {
        MySqlDaoFactory.getInstance().getUserDao().delete(user);
    }

    @Override
    public List<User> getAll() {
        return MySqlDaoFactory.getInstance().getUserDao().getAll();
    }

    @Override
    public List<User> getAllSubordinatesOf(User user) {
        return MySqlDaoFactory.getInstance().getUserDao().getAllSubordinatesOf(user);
    }

    @Override
    public User getOwner() {
        return MySqlDaoFactory.getInstance().getUserDao().getOwner();
    }

    @Override
    public List<Area> getAttachedAreas(User user) {
        return MySqlDaoFactory.getInstance().getAreaDao().getAll()
                .stream()
                .filter(area -> area.getTaskmasterId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}