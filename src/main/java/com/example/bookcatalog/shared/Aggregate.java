package com.example.bookcatalog.shared;

import java.util.LinkedList;
import java.util.List;

public class Aggregate {

    private final List<DomainEvent> domainEvents = new LinkedList<>();

    public void publishEvent(final DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    public List<DomainEvent> domainEvents() {
        return List.copyOf(domainEvents);
    }

    public void clearEvents() {
        domainEvents.clear();
    }

}
