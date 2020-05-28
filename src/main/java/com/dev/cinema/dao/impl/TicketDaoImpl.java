package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.TicketDao;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import java.util.Map;

@Dao
public class TicketDaoImpl extends BaseDaoImpl<Ticket>
                           implements TicketDao {
    @Override
    public Ticket add(Ticket ticket) {
        return addItem(ticket);
    }

    @Override
    public void deleteFromUserShoppingCart(User user) {
        deleteWithParams(Ticket.class, (root, builder) ->
                builder.and(
                        builder.equal(root.get("user"), user),
                        builder.isNull(root.get("order"))));
    }

    @Override
    public void moveFromUserShoppingCartToOrder(User user, Order order) {
        updateWithParams(Ticket.class, (root, builder) ->
                builder.and(
                        builder.equal(root.get("user"), user),
                        builder.isNull(root.get("order"))),
                Map.of("order", order));
    }
}
