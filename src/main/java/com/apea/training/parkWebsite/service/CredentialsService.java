package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Credentials;

public interface CredentialsService {

    void create(Credentials credentials);

    Credentials getByUserId(Integer userId);

    /**
     * @return null value if credentials with this login
     * don't exist.
     */
    Credentials getByLogin(String login);

    void update(Credentials credentials);
}