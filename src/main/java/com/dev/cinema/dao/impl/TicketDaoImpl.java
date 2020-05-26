package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.TicketDao;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.Ticket;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

@Dao
public class TicketDaoImpl extends BaseDaoImpl<Ticket>
                           implements TicketDao {
    @Override
    public Ticket add(Ticket ticket) {
        return addItem(ticket);
    }

    @Override
    protected void fetchTables(Root<Ticket> root) {
        root.fetch("user", JoinType.LEFT);
        root.fetch("movieSession", JoinType.LEFT);
    }
}
