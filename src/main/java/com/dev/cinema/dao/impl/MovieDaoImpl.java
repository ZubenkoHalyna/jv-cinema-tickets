package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieDao;
import com.dev.cinema.model.Movie;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDaoImpl extends BaseDaoImpl<Movie>
                          implements MovieDao {
    private SessionFactory sessionFactory;

    public MovieDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Movie add(Movie movie) {
        return addItem(movie);
    }

    @Override
    public List<Movie> getAll() {
        return getAll(Movie.class);
    }
}
