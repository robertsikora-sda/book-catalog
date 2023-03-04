package com.example.bookcatalog.shared;

public interface DomainEventSubscriber<T extends DomainEvent> {

    void subscribe(T domainEvent);
}
