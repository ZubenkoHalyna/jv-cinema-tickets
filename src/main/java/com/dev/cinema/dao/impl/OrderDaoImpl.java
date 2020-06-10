package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.User;
import java.util.List;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order>
                          implements OrderDao {
    private SessionFactory sessionFactory;

    public OrderDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order add(Order order) {
        return addItem(order);
    }

    @Override
    public List<Order> getByUser(User user) {
        return getListWithParams(Order.class,
                (root, builder) -> builder.equal(root.get("user"), user));
    }

    @Override
    protected void fetchFields(Root<Order> root) {
        root.fetch("user", JoinType.INNER);
        Fetch ticketsFetch = root.fetch("tickets", JoinType.LEFT);
        Fetch movieSessionFetch = ticketsFetch.fetch("movieSession", JoinType.LEFT);
        movieSessionFetch.fetch("movie", JoinType.LEFT);
        movieSessionFetch.fetch("cinemaHall", JoinType.LEFT);
    }
}
