package com.example.bookcatalog.domain.reservation;

import java.util.Optional;

public interface ReservationRepository {

    Optional<Reservation> findById(ReservationId reservationId);

    void save(Reservation reservation);

}
