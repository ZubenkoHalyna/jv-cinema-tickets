package com.dev.cinema.dao;

import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import java.util.Optional;

public interface ShoppingCartDao {
    ShoppingCart add(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getByUser(User user);

    void update(ShoppingCart shoppingCart);
}
