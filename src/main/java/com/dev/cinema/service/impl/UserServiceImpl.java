package com.dev.cinema.service.impl;

import com.dev.cinema.dao.UserDao;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.User;
import com.dev.cinema.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final Injector INJECTOR = Injector.getInstance("com.dev.cinema");
    private UserDao userDao = (UserDao) INJECTOR.getInstance(UserDao.class);

    @Override
    public User add(User user) {
        return userDao.add(user);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
