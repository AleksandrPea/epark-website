package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.User;

public interface UserService {

    User getByLogin(String login);

    void changePassword(String login, String newPassword);
}
