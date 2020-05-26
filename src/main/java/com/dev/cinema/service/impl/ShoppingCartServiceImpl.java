package com.dev.cinema.service.impl;

import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.ShoppingCartService;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private static final Injector INJECTOR = Injector.getInstance("com.dev.cinema");
    private ShoppingCartDao shoppingCartDao =
            (ShoppingCartDao) INJECTOR.getInstance(ShoppingCartDao.class);

    @Override
    public void addSession(MovieSession movieSession, User user) {
        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user)
                .orElse(new ShoppingCart(user));
        shoppingCart.getTickets().add(new Ticket(movieSession, user));
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> getByUser(User user) {
        return shoppingCartDao.getByUser(user);
    }

    @Override
    public void registerNewShoppingCart(User user) {
        shoppingCartDao.add(new ShoppingCart(user));
    }
}
