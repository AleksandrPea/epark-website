package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.Credentials;

public interface CredentialsDao {

    void create(Credentials credentials);

    Credentials getByUserId(Integer userId);

    /**
     * @return null if credentials with this login
     * don't exist.
     */
    Credentials getByLogin(String login);

    void update(Credentials credentials);
}
