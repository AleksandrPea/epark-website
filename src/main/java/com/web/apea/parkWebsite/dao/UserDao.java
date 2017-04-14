package com.web.apea.parkWebsite.dao;

import com.web.apea.parkWebsite.domain.User;

public interface UserDao {

    /** @return null if there is no user with this login */
    User getByLogin(String login);
    void update(User user);
}
