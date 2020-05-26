package com.dev.cinema.service.impl;

import com.dev.cinema.dao.TicketDao;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
    private static final Injector INJECTOR = Injector.getInstance("com.dev.cinema");
    private TicketDao ticketDao = (TicketDao) INJECTOR.getInstance(TicketDao.class);

    @Override
    public Ticket add(Ticket ticket) {
        return ticketDao.add(ticket);
    }
}
