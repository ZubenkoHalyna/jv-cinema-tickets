package com.dev.cinema.service;

import com.dev.cinema.model.MovieSession;
import java.util.List;

public interface MovieSessionService {
    MovieSession add(MovieSession movieSession);

    List<MovieSession> getAll();
}
