package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import java.util.Optional;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

@Dao
public class ShoppingCartDaoImpl extends BaseDaoImpl<ShoppingCart>
                                 implements ShoppingCartDao {
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        return addItem(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> getByUser(User user) {
        return getWithParams(ShoppingCart.class,
                (root, builder) -> builder.equal(root.get("user").get("id"), user.getId()));
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        updateItem(shoppingCart);
    }

    @Override
    protected void fetchTables(Root<ShoppingCart> root) {
        root.fetch("user", JoinType.LEFT);
        root.fetch("tickets", JoinType.LEFT);
    }
}
