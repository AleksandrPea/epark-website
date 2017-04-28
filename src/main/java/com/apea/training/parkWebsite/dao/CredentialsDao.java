package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.Credentials;

import java.util.List;

public interface CredentialsDao {

    void create(Credentials credentials);

    Credentials getByUserId(Integer userId);

    /**
     * @return null if credentials with this login
     * don't exist.
     */
    Credentials getByLogin(String login);

    List<Credentials> getAll();

    void update(Credentials credentials);
}
