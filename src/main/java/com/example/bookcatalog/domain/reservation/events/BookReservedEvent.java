package com.example.bookcatalog.domain.reservation.events;

import com.example.bookcatalog.domain.book.BookId;
import com.example.bookcatalog.shared.DomainEvent;

public record BookReservedEvent(BookId bookId) implements DomainEvent {
}
