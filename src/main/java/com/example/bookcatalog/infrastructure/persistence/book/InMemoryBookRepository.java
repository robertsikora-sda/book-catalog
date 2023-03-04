package com.example.bookcatalog.infrastructure.persistence.book;

import com.example.bookcatalog.domain.book.Book;
import com.example.bookcatalog.domain.book.BookId;
import com.example.bookcatalog.domain.book.BookRepository;
import com.example.bookcatalog.domain.book.ISBN;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public enum InMemoryBookRepository implements BookRepository {
    INSTANCE;

    private final BookId BOOK_ID = new BookId(UUID.fromString("504bbf4c-a762-4cd9-8375-8ef8465e4117"));

    private final Map<BookId, Book> storage = new HashMap<>();

    {
        storage.put(BOOK_ID, new Book.Builder()
                .bookId(BOOK_ID)
                .title("Pan Tadeusz")
                .author("A.Mickiewicz")
                .isbn(new ISBN("978-3-16-148410-0"))
                .status(Book.Status.AVAILABLE)
                .build()
        );
    }

    @Override
    public Optional<Book> findById(final BookId bookId) {
        return Optional.ofNullable(storage.get(bookId));
    }

}
