package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.User;

public interface UserDao {

    /** @return null if there is no user with this login */
    User getByLogin(String login);
    void update(User user);
}
