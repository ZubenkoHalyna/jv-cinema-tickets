package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.model.MovieSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl extends BaseDaoImpl<MovieSession>
                                 implements MovieSessionDao {
    private SessionFactory sessionFactory;

    public MovieSessionDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        return addItem(movieSession);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return getListWithParams(MovieSession.class, (root, builder) ->
                        builder.and(
                                builder.equal(root.get("movie"), movieId),
                                builder.between(root.get("showTime"),
                                    date.atStartOfDay(),
                                    date.atTime(LocalTime.MAX))));
    }

    @Override
    protected void fetchFields(Root<MovieSession> root) {
        root.fetch("movie", JoinType.LEFT);
        root.fetch("cinemaHall", JoinType.LEFT);
    }
}
