package com.example.bookcatalog.infrastructure.persistence.reservation;

import com.example.bookcatalog.domain.reservation.ReservationRepository;
import com.example.bookcatalog.domain.reservation.events.BookLendEvent;
import com.example.bookcatalog.domain.reservation.events.BookReservedEvent;
import com.example.bookcatalog.shared.DomainEventSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReservationPersistenceModule {

    @Bean
    ReservationRepository reservationRepository(final DomainEventSubscriber<BookReservedEvent> bookReservedEventSubscriber,
                                                final DomainEventSubscriber<BookLendEvent> bookLendEventSubscriber) {
        return new EventPublisherReservationRepository(InMemoryReservationRepository.INSTANCE,
                bookReservedEventSubscriber,
                bookLendEventSubscriber);
    }

}
