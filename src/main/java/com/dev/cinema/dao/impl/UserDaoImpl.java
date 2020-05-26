package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.UserDao;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.User;
import java.util.Optional;

@Dao
public class UserDaoImpl extends BaseDaoImpl<User>
                         implements UserDao {
    @Override
    public User add(User user) {
        return super.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return getWithParams(User.class,
                (root, builder) -> builder.equal(root.get("email"), email));
    }
}
