package com.example.bookcatalog.application.book;

import com.example.bookcatalog.domain.BookCatalogException;
import com.example.bookcatalog.domain.book.Book;
import com.example.bookcatalog.domain.book.BookCatalog;
import com.example.bookcatalog.domain.book.BookId;
import com.example.bookcatalog.domain.book.BookRepository;

class DefaultBookCatalog implements BookCatalog {

    private final BookRepository bookRepository;

    DefaultBookCatalog(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean isBookAvailable(final BookId bookId) {
        return bookRepository.findById(bookId)
                .map(Book::isAvailable)
                .orElseThrow(() -> new BookCatalogException("There is not book with id:'%s'".formatted(bookId.value())));
    }
}
