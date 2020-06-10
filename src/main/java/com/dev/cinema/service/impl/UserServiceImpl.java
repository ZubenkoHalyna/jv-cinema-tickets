package com.dev.cinema.service.impl;

import com.dev.cinema.dao.UserDao;
import com.dev.cinema.model.User;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private ShoppingCartService shoppingCartService;

    public UserServiceImpl(UserDao userDao, ShoppingCartService shoppingCartService) {
        this.userDao = userDao;
        this.shoppingCartService = shoppingCartService;
    }

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
