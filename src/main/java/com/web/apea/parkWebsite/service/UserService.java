package com.web.apea.parkWebsite.service;

import com.web.apea.parkWebsite.domain.User;

public interface UserService {

    User getByLogin(String login);

    void changePassword(String login, String newPassword);
}
