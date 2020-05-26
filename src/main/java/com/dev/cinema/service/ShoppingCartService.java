package com.dev.cinema.service;

import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import java.util.Optional;

public interface ShoppingCartService {
    void addSession(MovieSession movieSession, User user);

    Optional<ShoppingCart> getByUser(User user);

    void registerNewShoppingCart(User user);
}
