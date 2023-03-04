package com.example.bookcatalog.application.book;

import com.example.bookcatalog.domain.book.BookCatalog;
import com.example.bookcatalog.domain.reservation.events.BookLendEvent;
import com.example.bookcatalog.domain.reservation.events.BookReservedEvent;
import com.example.bookcatalog.infrastructure.persistence.book.InMemoryBookRepository;
import com.example.bookcatalog.shared.DomainEventSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookApplicationModule {

    @Bean
    BookCatalog bookCatalog() {
        return new DefaultBookCatalog(InMemoryBookRepository.INSTANCE);
    }

    @Bean
    DomainEventSubscriber<BookReservedEvent> bookReservedEventSubscriber() {
        return new BookReservedEventSubscriber(InMemoryBookRepository.INSTANCE);
    }

    @Bean
    DomainEventSubscriber<BookLendEvent> bookLendEventSubscriber() {
        return new BookLendEventSubscriber(InMemoryBookRepository.INSTANCE);
    }
}
