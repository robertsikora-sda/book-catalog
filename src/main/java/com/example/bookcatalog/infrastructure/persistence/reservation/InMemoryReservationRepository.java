package com.example.bookcatalog.infrastructure.persistence.reservation;

import com.example.bookcatalog.domain.reservation.Reservation;
import com.example.bookcatalog.domain.reservation.ReservationId;
import com.example.bookcatalog.domain.reservation.ReservationRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum InMemoryReservationRepository implements ReservationRepository {
    INSTANCE;

    private final Map<ReservationId, Reservation> storage = new HashMap<>();

    @Override
    public Optional<Reservation> findById(ReservationId reservationId) {
        return Optional.ofNullable(storage.get(reservationId));
    }

    @Override
    public void save(final Reservation reservation) {
        storage.put(reservation.reservationId(), reservation);
    }
}
