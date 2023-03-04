package com.example.bookcatalog.application.book;

import com.example.bookcatalog.domain.book.Book;
import com.example.bookcatalog.domain.book.BookRepository;
import com.example.bookcatalog.domain.reservation.events.BookReservedEvent;
import com.example.bookcatalog.shared.DomainEventSubscriber;

class BookReservedEventSubscriber implements DomainEventSubscriber<BookReservedEvent> {

    private final BookRepository bookRepository;

    BookReservedEventSubscriber(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void subscribe(final BookReservedEvent domainEvent) {
        bookRepository.findById(domainEvent.bookId())
                .map(Book::reserveBook)
                .orElseThrow();
    }
}
