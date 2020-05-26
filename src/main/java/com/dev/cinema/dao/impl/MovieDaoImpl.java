package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieDao;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.Movie;
import java.util.List;

@Dao
public class MovieDaoImpl extends BaseDaoImpl<Movie>
                          implements MovieDao {
    @Override
    public Movie add(Movie movie) {
        return super.add(movie);
    }

    @Override
    public List<Movie> getAll() {
        return getAll(Movie.class);
    }
}
