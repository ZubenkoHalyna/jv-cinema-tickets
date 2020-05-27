package com.dev.cinema.service.impl;

import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.dao.TicketDao;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private static final Injector INJECTOR = Injector.getInstance("com.dev.cinema");
    private ShoppingCartDao shoppingCartDao =
            (ShoppingCartDao) INJECTOR.getInstance(ShoppingCartDao.class);
    private TicketDao ticketDao =
            (TicketDao) INJECTOR.getInstance(TicketDao.class);

    /**
     * Add ticket for movieSession to user's shopping cart, update orderDate of the
     * shopping cart.
     * @param movieSession - movieSession for creating a ticket
     * @param user - user for finding a shopping cart
     */
    @Override
    public void addSession(MovieSession movieSession, User user) {
        Ticket ticket = new Ticket(movieSession, user);
        ticketDao.add(ticket);
        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user);
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user);
    }

    @Override
    public void registerNewShoppingCart(User user) {
        shoppingCartDao.add(new ShoppingCart(user));
    }
}
