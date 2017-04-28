package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Credential;

import java.util.List;

public interface CredentialService {

    void create(Credential credential);

    Credential getByUserId(Integer userId);

    /**
     * @return null value if credentials with this login
     * don't exist.
     */
    Credential getByLogin(String login);

    List<Credential> getAll();

    void update(Credential credential);
}