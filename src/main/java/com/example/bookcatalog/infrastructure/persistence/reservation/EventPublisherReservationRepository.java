package com.example.bookcatalog.infrastructure.persistence.reservation;

import com.example.bookcatalog.domain.reservation.Reservation;
import com.example.bookcatalog.domain.reservation.ReservationId;
import com.example.bookcatalog.domain.reservation.ReservationRepository;
import com.example.bookcatalog.domain.reservation.events.BookLendEvent;
import com.example.bookcatalog.domain.reservation.events.BookReservedEvent;
import com.example.bookcatalog.shared.DomainEvent;
import com.example.bookcatalog.shared.DomainEventSubscriber;

import java.util.Optional;

class EventPublisherReservationRepository implements ReservationRepository {

    private final ReservationRepository delegate;
    private final DomainEventSubscriber<BookReservedEvent> bookReservedEventSubscriber;
    private final DomainEventSubscriber<BookLendEvent> bookLendEventSubscriber;

    EventPublisherReservationRepository(final ReservationRepository delegate,
                                        final DomainEventSubscriber<BookReservedEvent> bookReservedEventSubscriber,
                                        final DomainEventSubscriber<BookLendEvent> bookLendEventSubscriber) {
        this.delegate = delegate;
        this.bookReservedEventSubscriber = bookReservedEventSubscriber;
        this.bookLendEventSubscriber = bookLendEventSubscriber;
    }

    @Override
    public Optional<Reservation> findById(final ReservationId reservationId) {
        return delegate.findById(reservationId);
    }

    @Override
    public void save(final Reservation reservation) {
        delegate.save(reservation);

        reservation.domainEvents().forEach(this::subscribeOn);
        reservation.clearEvents();
    }

    private void subscribeOn(final DomainEvent domainEvent) {
        switch (domainEvent) {
            case BookReservedEvent bookReservedEvent -> bookReservedEventSubscriber.subscribe(bookReservedEvent);
            case BookLendEvent bookLendEvent -> bookLendEventSubscriber.subscribe(bookLendEvent);
            default -> throw new IllegalStateException("This event: '%s' is not handled at this moment!".formatted(domainEvent));
        }
    }
}
