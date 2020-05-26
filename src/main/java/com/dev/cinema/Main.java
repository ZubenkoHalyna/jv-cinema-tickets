package com.dev.cinema;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDateTime;

public class Main {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(movie);
        Movie movie2 = new Movie();
        movie.setTitle("Fast and Furious 2");
        movieService.add(movie2);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("red");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHall = cinemaHallService.add(cinemaHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(movie);
        LocalDateTime dateTime = LocalDateTime.of(2020, 5, 20, 12, 30);
        movieSession.setShowTime(dateTime);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);

        movieSession.setShowTime(dateTime.plus(5, HOURS));
        movieSessionService.add(movieSession);

        movieSession.setMovie(movie2);
        movieSessionService.add(movieSession);

        movieSession.setMovie(movie);
        movieSession.setShowTime(dateTime.plus(1, DAYS));
        movieSessionService.add(movieSession);

        movieService.getAll().forEach(System.out::println);
        cinemaHallService.getAll().forEach(System.out::println);
        movieSessionService.findAvailableSessions(movie.getId(), dateTime.toLocalDate())
                .forEach(System.out::println);
    }
}
