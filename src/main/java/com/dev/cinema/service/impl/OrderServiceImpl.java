package com.dev.cinema.service.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.dao.TicketDao;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.OrderService;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Injector INJECTOR = Injector.getInstance("com.dev.cinema");
    private OrderDao orderDao = (OrderDao) INJECTOR.getInstance(OrderDao.class);
    private TicketDao ticketDao = (TicketDao) INJECTOR.getInstance(TicketDao.class);

    @Override
    public Order completeOrder(List<Ticket> tickets, User user) {
        Order order = new Order(user);
        orderDao.add(order);
        ticketDao.moveFromUserShoppingCartToOrder(user, order);
        return order;
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderDao.getByUser(user);
    }
}
