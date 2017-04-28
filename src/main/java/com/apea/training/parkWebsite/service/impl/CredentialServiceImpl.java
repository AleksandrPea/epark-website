package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.service.CredentialService;

import java.util.List;

public class CredentialServiceImpl implements CredentialService {

    CredentialServiceImpl() {}

    @Override
    public void create(Credential credential) {
        MySqlDaoFactory.getInstance().getCredentialDao().create(credential);
    }

    @Override
    public Credential getByUserId(Integer userId) {
        return MySqlDaoFactory.getInstance().getCredentialDao().getByUserId(userId);
    }

    @Override
    public Credential getByLogin(String login) {
        return MySqlDaoFactory.getInstance().getCredentialDao().getByLogin(login);
    }

    @Override
    public List<Credential> getAll() {
        return MySqlDaoFactory.getInstance().getCredentialDao().getAll();
    }

    @Override
    public void update(Credential credential) {
        MySqlDaoFactory.getInstance().getCredentialDao().update(credential);
    }
}
