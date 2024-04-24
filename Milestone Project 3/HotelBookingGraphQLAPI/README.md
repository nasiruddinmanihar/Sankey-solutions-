# Introduction

This project provides a GraphQL API for managing hotel bookings. It allows clients to perform CRUD operations on various entities such as users, hotels, rooms, bookings, and payments.

## GraphQL Endpoint
- The GraphQL API endpoint is `/graphql`.
- Clients can send GraphQL queries and mutations to this endpoint to interact with the API.

## Documentation
- The API is documented using GraphQL's built-in schema introspection feature.
- Clients can explore the available queries, mutations, and types by sending an introspection query to the GraphQL endpoint.
- Additionally, the project includes a GraphQL IDE like GraphiQL i.e http://localhost:8081/hotelbooking/graphiql or GraphQL Playground for easy exploration and testing of queries and mutations.

##Database Configuration
- The application is connected to a MYSQL database to store and retrieve data.
- The database URL is `jdbc:mysql://localhost:3306/hotel_booking_graphql`, where:
  - `jdbc:mysql://` indicates the JDBC connection protocol for MySQL.
  - `localhost:3306` represents the host and port where MySQL is running. Update this if your database is hosted elsewhere or uses a different port.
  - `hotel_booking_graphql` is the name of the database schema used by the application.

In application.properties you need to change 
spring.datasource.url= <Your database url>

spring.datasource.username= <Your username>

spring.datasource.password= <Your password>


#Port Number - 8081

#Contextpath - /hotelbooking

