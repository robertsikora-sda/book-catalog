package com.example.bookcatalog.domain.book;

import com.example.bookcatalog.domain.BookCatalogException;
import org.apache.commons.validator.routines.ISBNValidator;

public record ISBN(String value) {

    public ISBN {
        if (ISBNValidator.getInstance().validate(value) == null) {
            throw new BookCatalogException("Invalid ISBN number!");
        }
    }

}
