// Helper function to handle the response and parse JSON
async function handleResponse(response) {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error(`HTTP error: ${response.status}`);
    }
  }
  
  // Create a new author and book
  async function createAuthorAndBook() {
    const author = {
      name: "John Doe",
      email: "johndoe@example.com"
    };
  
    const book = {
      title: "Sample Book",
      description: "This is a sample book",
      authorId: null // Will be updated after creating the author
    };
  
    // Create the author
    const createAuthorResponse = await fetch("/authors", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(author)
    });
    const createdAuthor = await handleResponse(createAuthorResponse);
  
    // Update the book's author ID with the created author's ID
    book.authorId = createdAuthor.id;
  
    // Create the book
    const createBookResponse = await fetch(`/books/${book.authorId}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(book)
    });
    const createdBook = await handleResponse(createBookResponse);
  
    console.log("Created author:", createdAuthor);
    console.log("Created book:", createdBook);
  }
  
  // Retrieve a book by ID
  async function retrieveBookById(bookId) {
    const response = await fetch(`/books/${bookId}`);
    const book = await handleResponse(response);
  
    console.log("Retrieved book:", book);
  }
  
  // Update an existing book
  async function updateBook(bookId, updatedData) {
    const response = await fetch(`/books/${bookId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(updatedData)
    });
    const updatedBook = await handleResponse(response);
  
    console.log("Updated book:", updatedBook);
  }
  
  // Delete a book by ID
  async function deleteBookById(bookId) {
    const response = await fetch(`/books/${bookId}`, {
      method: "DELETE"
    });
  
    if (response.ok) {
      console.log("Book deleted successfully.");
    } else {
      throw new Error("Failed to delete book.");
    }
  }
  
  // Retrieve all books
  async function retrieveAllBooks() {
    const response = await fetch("/books");
    const books = await handleResponse(response);
  
    console.log("All books:", books);
  }
  
  // Retrieve all books with their corresponding authors
  async function retrieveAllBooksWithAuthors() {
    const response = await fetch("/books");
    const books = await handleResponse(response);
  
    for (const book of books) {
      const authorResponse = await fetch(`/authors/${book.authorId}`);
      const author = await handleResponse(authorResponse);
  
      console.log("Book:", book);
      console.log("Author:", author);
    }
  }
  
  // Usage example
  createAuthorAndBook()
    .then(() => retrieveBookById(1))
    .then(() => updateBook(1, { title: "Updated Title", description: "Updated Description" }))
    .then(() => deleteBookById(1))
    .then(() => retrieveAllBooks())
    .then(() => retrieveAllBooksWithAuthors())
    .catch(error => console.error("Error:", error));
  