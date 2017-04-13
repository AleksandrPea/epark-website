package com.web.apea.parkWebsite.dao;

import com.web.apea.parkWebsite.domain.User;

public interface UserDao {

    User getByLogin(String login);
    void update(User user);
}
