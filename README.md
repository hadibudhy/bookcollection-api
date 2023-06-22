# Book Collection API

The Book Collection API is a RESTful API that allows you to manage a collection of books and their corresponding authors.

## Prerequisites

Before running the application and the client-side code, make sure you have the following dependencies installed:

- Java Development Kit (JDK) 8 or later
- Node.js

## Server-Side Application

### Setup

1. Clone the repository or download the source code.
2. Open the project in your preferred IDE.
3. Build the project to resolve dependencies.

### Configuration

1. Open the `application.properties` file located in the `src/main/resources` directory.
2. Configure the database connection properties according to your environment. By default, the application uses an in-memory H2 database.

### Run

1. Run the main class `BookCollectionApiApplication` to start the server-side application.
2. The application will start on `http://localhost:8080` by default.

## Client-Side Code

### Setup

1. Open a terminal or command prompt.
2. Navigate to the directory where you have the `client.js` file.
3. Run `npm install` to install the necessary dependencies.

### Run

1. Run `npm start` to start the client-side code.
2. The client-side code will interact with the server-side API endpoints.

## Usage

The server-side application provides the following API endpoints:

- `POST /authors`: Create a new author.
- `PUT /authors/{id}`: Update an existing author.
- `GET /authors/{id}`: Retrieve an author by ID.
- `GET /authors`: Retrieve all authors.
- `DELETE /authors/{id}`: Delete an author by ID.

- `POST /books/{id}`: Create or update a book with the given ID.
- `GET /books/{id}`: Retrieve a book by ID.
- `GET /books`: Retrieve all books.
- `DELETE /books/{id}`: Delete a book by ID.

The client-side code in `client.js` demonstrates how to consume these API endpoints and perform various operations, such as creating an author and book, updating a book, retrieving books by ID, deleting a book, and more.

## Testing and Known Issues

During the development of the server-side application, some errors were encountered in the `BookControllerIT` test class:

- `java.lang.AssertionError: Status expected:[201] but was:[405]`: This error occurs when the expected HTTP status code for a request is different from the actual status code received. It indicates that the test expected a successful creation (HTTP 201) of a resource, but the response indicated that the request method is not allowed (HTTP 405). This could be due to a misconfiguration or a problem with the implementation of the API endpoint.

- `java.util.NoSuchElementException: Author with ID 1 not found`: This error occurs when the test expects to find an author with a specific ID (in this case, ID 1), but the author is not found in the system. This could be due to incorrect test data setup or an issue with the retrieval of authors in the service implementation.

If you encounter these errors during your testing, consider reviewing the corresponding test cases in the `BookControllerIT` class and the related service implementations (`BookService` and `AuthorService`). Ensure that the test data setup is correct and that the API endpoints and their implementations are functioning as expected.
