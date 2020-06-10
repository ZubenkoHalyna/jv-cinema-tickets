package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl extends BaseDaoImpl<ShoppingCart>
                                 implements ShoppingCartDao {
    private SessionFactory sessionFactory;

    public ShoppingCartDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        return addItem(shoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return getWithParams(ShoppingCart.class,
                (root, builder) -> builder.equal(root.get("user"), user))
                .orElseThrow();
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        updateItem(shoppingCart);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getTickets().clear();
        updateItem(shoppingCart);
    }

    @Override
    protected void fetchFields(Root<ShoppingCart> root) {
        root.fetch("user", JoinType.INNER);
        Fetch ticketsFetch = root.fetch("tickets", JoinType.LEFT);
        Fetch movieSessionFetch = ticketsFetch.fetch("movieSession", JoinType.LEFT);
        movieSessionFetch.fetch("movie", JoinType.LEFT);
        movieSessionFetch.fetch("cinemaHall", JoinType.LEFT);
    }
}
