package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.Credential;

import java.util.List;

public interface CredentialDao {

    void create(Credential credential);

    Credential getByUserId(Integer userId);

    /**
     * @return null if credentials with this login
     * don't exist.
     */
    Credential getByLogin(String login);

    List<Credential> getAll();

    void update(Credential credential);
}