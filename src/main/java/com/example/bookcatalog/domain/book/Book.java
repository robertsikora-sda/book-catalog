package com.example.bookcatalog.domain.book;

import com.example.bookcatalog.domain.BookCatalogException;
import com.example.bookcatalog.domain.InvariantsAPI;
import com.example.bookcatalog.shared.Aggregate;

public class Book extends Aggregate {

    private final BookId bookId;
    private final String title;
    private final String author;
    private final ISBN isbn;
    private Status status;

    private Book(Builder builder) {
        bookId = builder.bookId;
        title = builder.title;
        author = builder.author;
        isbn = builder.isbn;
        status = builder.status;
    }

    public BookId bookId() {
        return bookId;
    }

    public enum Status {
        AVAILABLE, RESERVED, LEND
    }

    public Book reserveBook() {
        InvariantsAPI.requireIs(
                this.status,
                Status.AVAILABLE,
                () -> new BookCatalogException("Cannot reserve a book which is not available!"));

        this.status = Status.RESERVED;
        return this;
    }

    public Book borrowBook() {
        InvariantsAPI.requireIs(
                this.status,
                Status.RESERVED,
                () -> new BookCatalogException("Cannot borrow a book which is not reserved!"));

        this.status = Status.LEND;
        return this;
    }

    public void returnBook() {
        InvariantsAPI.requireIs(
                this.status,
                Status.LEND,
                () -> new BookCatalogException("Cannot return a book which is not lend!"));

        this.status = Status.AVAILABLE;
    }

    public boolean isAvailable() {
        return this.status == Status.AVAILABLE;
    }

    public static final class Builder {
        private BookId bookId;
        private String title;
        private String author;
        private ISBN isbn;
        private Status status;

        public Builder() {
        }

        public Builder bookId(BookId bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder isbn(ISBN isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
