package com.dev.cinema;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

import com.dev.cinema.exceptions.AuthenticationException;
import com.dev.cinema.exceptions.HibernateQueryException;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.time.LocalDateTime;

public class Main {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(movie);
        Movie movie2 = new Movie();
        movie2.setTitle("Fast and Furious 2");
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
        System.out.println("Available sessions:");
        movieSessionService.findAvailableSessions(movie.getId(), dateTime.toLocalDate())
                .forEach(System.out::println);

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        String email = "root@ex.com";
        String password = "root";
        authenticationService.register(email, password);
        try {
            System.out.println(authenticationService.login(email, password));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        try {
            authenticationService.login(email, "123");
            System.err.println("Incorrect password allows to login");
        } catch (AuthenticationException e) {
            System.out.println("Incorrect password doesn't allows to login");
        }
        try {
            authenticationService.register(email, "123");
            System.err.println("Login isn't unique");
        } catch (HibernateQueryException e) {
            System.out.println("Can't add not unique login");
        }

        UserService userService =
                (UserService) injector.getInstance(UserService.class);
        User user = userService.findByEmail(email).orElseThrow();
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        shoppingCartService.addSession(movieSession, user);
        System.out.println(shoppingCartService.getByUser(user));
        shoppingCartService.addSession(movieSession, user);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        System.out.println(shoppingCart);

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(shoppingCart.getTickets(), user);
        System.out.println(shoppingCartService.getByUser(user));
        orderService.getOrderHistory(user).forEach(System.out::println);

        shoppingCartService.addSession(movieSession, user);
        shoppingCartService.addSession(movieSession, user);
        shoppingCartService.addSession(movieSession, user);
        shoppingCartService.addSession(movieSession, user);
        shoppingCartService.addSession(movieSession, user);
        shoppingCartService.addSession(movieSession, user);
        shoppingCartService.addSession(movieSession, user);
        System.out.println(shoppingCartService.getByUser(user));
        shoppingCartService.clear(shoppingCartService.getByUser(user));
        System.out.println(shoppingCartService.getByUser(user));
    }
}
