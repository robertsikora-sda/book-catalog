package com.example.bookcatalog.application.reservation;

import com.example.bookcatalog.domain.BookCatalogException;
import com.example.bookcatalog.domain.book.BookId;
import com.example.bookcatalog.domain.reservation.Reservation;
import com.example.bookcatalog.domain.reservation.ReservationId;
import com.example.bookcatalog.domain.reservation.ReservationRepository;
import com.example.bookcatalog.domain.book.BookCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.bookcatalog.domain.reservation.Reservation.newReservationFor;

public class ReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final BookCatalog bookStock;

    public ReservationService(final ReservationRepository reservationRepository, final BookCatalog bookStock) {
        this.reservationRepository = reservationRepository;
        this.bookStock = bookStock;
    }

    public ReservationId startReservation(final String client) {
        final var reservation = newReservationFor(client);
        reservationRepository.save(reservation);
        LOGGER.info("New reservation for:'{}' started", client);
        return reservation.reservationId();
    }

    public void reserveBook(final ReservationId reservationId, final BookId bookId) {
        reservationRepository.findById(reservationId)
                .ifPresentOrElse(reservation -> reserveBookIfAvailable(bookId, reservation),
                        () -> raiseErrorOnMissingReservation(reservationId));
    }

    public void completeReservation(final ReservationId reservationId) {
        reservationRepository.findById(reservationId)
                .ifPresentOrElse(Reservation::completeReservation, () -> raiseErrorOnMissingReservation(reservationId));
    }

    private void reserveBookIfAvailable(final BookId bookId, final Reservation reservation) {
        if (bookStock.isBookAvailable(bookId)) {
            reservation.addBookToReservation(bookId);
            reservationRepository.save(reservation);
            LOGGER.info("Book with id:'{}' successfully reserved", bookId.value());
        } else {
            throw new BookCatalogException("Book:'%s' is not available".formatted(bookId.value()));
        }
    }

    private static void raiseErrorOnMissingReservation(final ReservationId reservationId) {
        throw new BookCatalogException("There is no reservation with id:'%s'".formatted(reservationId.value()));
    }
}
