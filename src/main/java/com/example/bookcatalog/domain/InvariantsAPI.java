package com.example.bookcatalog.domain;

import java.util.Objects;
import java.util.function.Supplier;

public final class InvariantsAPI {

    private InvariantsAPI() {
    }

    public static <T> void requireIs(final T value,
                                     final T expectedValue,
                                     final Supplier<RuntimeException> onError) {
        if (!Objects.equals(value, expectedValue)) {
            throw onError.get();
        }
    }

}
