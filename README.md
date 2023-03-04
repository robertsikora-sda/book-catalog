# Book catalog

This is simple application which demonstrates how to use DDD & hexagonal architecture in
java project. It doesn't focus on domain which is imagined and partly done.

The domain is very trivial. There is a book catalog which contains books (now there is one hardcoded book in catalog).
An application supports only one usecase to reserve and borrow book from catalog.

Main assumptions:

1. Bounded contexts like books, reservations are loosely coupled and interacts only by domain events

2. An application is built around native Java and libraries. The frameworks like springboot are
   treated as addition and should not impact domain code
