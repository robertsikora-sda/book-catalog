package com.example.bookcatalog.application.book;

import com.example.bookcatalog.domain.book.Book;
import com.example.bookcatalog.domain.book.BookRepository;
import com.example.bookcatalog.domain.reservation.events.BookLendEvent;
import com.example.bookcatalog.shared.DomainEventSubscriber;

class BookLendEventSubscriber implements DomainEventSubscriber<BookLendEvent> {

    private final BookRepository bookRepository;

    BookLendEventSubscriber(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void subscribe(final BookLendEvent domainEvent) {
        bookRepository.findById(domainEvent.bookId())
                .map(Book::borrowBook)
                .orElseThrow();
    }
}
