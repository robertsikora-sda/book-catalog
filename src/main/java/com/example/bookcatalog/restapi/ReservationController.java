package com.example.bookcatalog.restapi;

import com.example.bookcatalog.application.reservation.ReservationService;
import com.example.bookcatalog.domain.book.BookId;
import com.example.bookcatalog.domain.reservation.ReservationId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

record StartReservationRequest(String client) {
}

record StartReservationResponse(UUID reservationId) {
}

@RequestMapping("api/v1/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> createNewReservation(@RequestBody final StartReservationRequest startReservationRequest) {
        var reservationId = reservationService.startReservation(startReservationRequest.client());
        return ResponseEntity.ok(new StartReservationResponse(reservationId.value()));
    }

    @PostMapping("{reservationId}/books/{bookId}/reserve")
    public ResponseEntity<?> reserveBook(@PathVariable("reservationId") final UUID reservationId,
                                         @PathVariable("bookId") final UUID bookId) {
        reservationService.reserveBook(new ReservationId(reservationId), new BookId(bookId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("{reservationId}/complete")
    public ResponseEntity<?> completeReservation(@PathVariable("reservationId") final UUID reservationId) {
        reservationService.completeReservation(new ReservationId(reservationId));
        return ResponseEntity.ok().build();
    }
}
