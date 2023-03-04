package com.example.bookcatalog.domain.book;

import java.util.Optional;

public interface BookRepository {

    Optional<Book> findById(BookId bookId);

}
