package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.CinemaHallDao;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.CinemaHall;
import java.util.List;

@Dao
public class CinemaHallDaoImpl extends BaseDaoImpl<CinemaHall>
                               implements CinemaHallDao {
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return addItem(cinemaHall);
    }

    @Override
    public List<CinemaHall> getAll() {
        return getAll(CinemaHall.class);
    }
}
