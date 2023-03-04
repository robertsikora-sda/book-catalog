package com.example.bookcatalog.domain.reservation;

import com.example.bookcatalog.domain.BookCatalogException;
import com.example.bookcatalog.domain.InvariantsAPI;
import com.example.bookcatalog.domain.book.BookId;
import com.example.bookcatalog.domain.reservation.events.BookLendEvent;
import com.example.bookcatalog.domain.reservation.events.BookReservedEvent;
import com.example.bookcatalog.shared.Aggregate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Reservation extends Aggregate {

    enum Status {OPEN, CLOSE}

    private final ReservationId reservationId;
    private final String client;
    private final Set<ReservedBook> reservedBooks = new HashSet<>();
    private Status status;

    public ReservationId reservationId() {
        return reservationId;
    }

    private Reservation(final ReservationId reservationId, final Status status, final String client) {
        this.reservationId = reservationId;
        this.status = status;
        this.client = client;
    }

    public static Reservation newReservationFor(final String client) {
        return new Reservation(new ReservationId(UUID.randomUUID()), Status.OPEN, client);
    }

    public void addBookToReservation(final BookId bookId) {
        InvariantsAPI.requireIs(
                this.status,
                Status.OPEN,
                () -> new BookCatalogException("Cannot add book to closed reservation!"));

        reservedBooks.add(new ReservedBook(bookId));
        publishEvent(new BookReservedEvent(bookId));
    }

    public void completeReservation() {
        InvariantsAPI.requireIs(
                this.status,
                Status.OPEN,
                () -> new BookCatalogException("Cannot complete already closed reservation!"));

        this.status = Status.CLOSE;
        reservedBooks.forEach(reservedBook -> publishEvent(new BookLendEvent(reservedBook.bookId())));
    }

}
