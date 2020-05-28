package com.dev.cinema.dao;

import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;

public interface TicketDao {
    Ticket add(Ticket ticket);

    void deleteFromUserShoppingCart(User user);

    void moveFromUserShoppingCartToOrder(User user, Order order);
}
