package com.dev.cinema.service.impl;

import com.dev.cinema.dao.UserDao;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.User;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Injector INJECTOR = Injector.getInstance("com.dev.cinema");
    private UserDao userDao = (UserDao) INJECTOR.getInstance(UserDao.class);
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    public User add(User user) {
        User userFromDb = userDao.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return userFromDb;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
