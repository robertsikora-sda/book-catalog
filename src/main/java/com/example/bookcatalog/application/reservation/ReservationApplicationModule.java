package com.example.bookcatalog.application.reservation;

import com.example.bookcatalog.domain.book.BookCatalog;
import com.example.bookcatalog.domain.reservation.ReservationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReservationApplicationModule {

    @Bean
    ReservationService reservationService(final BookCatalog bookStock, final ReservationRepository reservationRepository) {
        return new ReservationService(reservationRepository, bookStock);
    }
}
