package com.alura.literalura.main;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.service.ConsumeAPI;
import com.alura.literalura.service.ConvertData;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MainLiteralura {
    private final String URL_BASE = "http://gutendex.com/books/?search=";
    private ConvertData converter = new ConvertData();
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private List<Book> booksList;
    private List<Author> authorsList;
    private Scanner sc = new Scanner(System.in);
    private BookRepository repositoryBook;
    private AuthorRepository repositoryAuthor;

    public MainLiteralura(BookRepository repositoryBook, AuthorRepository repositoryAuthor) {
        this.repositoryBook = repositoryBook;
        this.repositoryAuthor = repositoryAuthor;
    }

    public void showMenu() {
        System.out.println("""
                -------------------------------------------------------
                                 Welcome to Literalura!
                The application that helps you find books and authors.
                Hope you enjoy it!
                -------------------------------------------------------
                """);

        var option = -1;
        while (option != 0) {
            var menu = """
                    
                    -------------------------------------------------------
                    Select an option:
                    
                    1 - Search for books by title
                    
                    2 - Show registered books
                    3 - Show registered books by language
                    4 - Show registered books by Author
                    5 - Show registered authors
                    6 - Show registered authors alive in a given year
                    7 - Show top 10 books
                    8 - Show statistics
                    
                    0 - Exit
                    -------------------------------------------------------
                    """;
            System.out.println(menu);

            try{
                option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 1:
                        searchBookByTitle();
                        break;
                    case 2:
                        showRegisteredBooks();
                        break;
                    case 3:
                        showRegisteredBooksByLanguage();
                        break;
                    case 4:
                        showRegisteredBooksByAuthor();
                        break;
                    case 5:
                        showRegisteredAuthors();
                        break;
                    case 6:
                        showAuthorsAliveInYear();
                        break;
                    case 7:
                        showTop10Books();
                        break;
                    case 8:
                        showStatistics();
                        break;
                    case 0:
                        System.out.println("""
                            -------------------------------------------------------
                                               Closing application...
                            
                                                    Goodbye!
                            -------------------------------------------------------
                            """);
                        break;
                    default:
                        System.out.println("""
                            
                            -------------------------------------------------------
                                          Invalid option, try again.
                            -------------------------------------------------------
                            """);
                        break;
                }
            } catch(Error e){
                throw new Error("""
                    -------------------------------------------------------
                                  Invalid option, try again.
                    -------------------------------------------------------
                    """ + e.getMessage());
            }
        }
    }

    private void searchBookByTitle() {
        System.out.println("""
                -------------------------------------------------------
                Enter the title of the book you want to search: 
                -------------------------------------------------------
                """);
        sc = new Scanner(System.in);

        try{
            var titleBook = sc.nextLine();

            if (titleBook == null || titleBook.isBlank()) {
                System.out.println("""
                    -------------------------------------------------------
                                    Title cannot be empty.
                    -------------------------------------------------------
                    """);
                return;
            }

            var json = consumeAPI.obtainData(URL_BASE + titleBook.replace(" ", "+"));
            DataResult dataResult = converter.obtainData(json, DataResult.class);
            var result = dataResult.results().get(0);

            DataBook dataBook = new DataBook(
                    result.title(),
                    result.authors(),
                    result.languages(),
                    result.downloadCount());
            Optional<Book> bookSaved = repositoryBook.findByTitleContainsIgnoreCase(dataBook.title());

            if (bookSaved.isPresent()) {
                System.out.println("""
                    -------------------------------------------------------
                                    Book already registered.
                    -------------------------------------------------------
                    """);
            }

            DataAuthor dataAuthor = new DataAuthor(
                    dataBook.authors().get(0).name(),
                    dataBook.authors().get(0).birthYear(),
                    dataBook.authors().get(0).deathYear());
            Optional<Author> authorSaved = repositoryAuthor.findByNameContainsIgnoreCase(dataAuthor.name());

            Author author;
            Book book = new Book(dataBook);
            if (authorSaved.isPresent()) {
                author = authorSaved.get();
            } else {
                author = book.getAuthor();
                repositoryAuthor.save(author);

                System.out.println("""
                    -------------------------------------------------------
                                   Book registered successfully.
                    -------------------------------------------------------
                    """);
            }

            repositoryBook.save(book);
        } catch(Error e){
            throw new Error("""
                    -------------------------------------------------------
                                  Book not found, try again.
                    -------------------------------------------------------
                    """ + e.getMessage());
        }
    }

    private void showRegisteredBooks() {
        booksList = repositoryBook.findAll();

        if (booksList.isEmpty()) {
            System.out.println("""
                    -------------------------------------------------------
                                      No books registered.
                    -------------------------------------------------------
                    """);
        } else {
            System.out.println("""
                    -------------------------------------------------------
                    Registered books: 
                    """);
            booksList.forEach(b -> System.out.println(b.toString()));
            System.out.println("-------------------------------------------------------");

        }
    }

    private void showRegisteredBooksByLanguage() {
        System.out.println("""
                Enter the language you want to search: 
                - English
                - Spanish
                - Portuguese
                - French
                - Italian
                """);
        sc = new Scanner(System.in);

        try{
            var languageSearched = sc.nextLine();

            Language language = Language.fromEnglish(languageSearched.toLowerCase());
            System.out.println(language);
            booksList = repositoryBook.showBooksByLanguage(language);

            if (booksList.isEmpty()) {
                System.out.println("""
                    -------------------------------------------------------
                              No books registered in this language.
                    -------------------------------------------------------
                    """);
            } else {
                System.out.println("""
                    -------------------------------------------------------
                    Registered books in the language %s: 
                    """.formatted(language));
                booksList.forEach(b -> System.out.println(b.toString()));
                System.out.println("-------------------------------------------------------");
            }
        } catch (Error e){
            throw new Error("""
                    No language found: 
                    -------------------------------------------------------
                                  Invalid language, try again.
                    -------------------------------------------------------
                    """ + e.getMessage());
        }
    }

    private void showRegisteredBooksByAuthor() {
        System.out.println("""
                -------------------------------------------------------
                Enter the name of the author you want to search: 
                -------------------------------------------------------
                """);
        sc = new Scanner(System.in);

        try{
            var authorSearched = sc.nextLine();

            booksList = repositoryBook.showBooksByAuthor(authorSearched);

            if (booksList.isEmpty()) {
                System.out.println("""
                    -------------------------------------------------------
                             No books registered for this author.
                    -------------------------------------------------------
                    """);
            } else {
                System.out.println("""
                    -------------------------------------------------------
                    Registered books for the author %s: 
                    """.formatted(booksList.get(0).getAuthor().getName()));
                booksList.forEach(b -> System.out.println(b.toString()));
                System.out.println("-------------------------------------------------------");
            }
        } catch (Error e){
            throw new Error("""
                    -------------------------------------------------------
                                  Invalid author, try again.
                    -------------------------------------------------------
                    """ + e.getMessage());
        }
    }

    private void showRegisteredAuthors() {
        authorsList = repositoryAuthor.findAll();


        if (authorsList.isEmpty()) {
            System.out.println("""
                        -------------------------------------------------------
                                          No authors registered.
                        -------------------------------------------------------
                        """);
        } else {
            System.out.println("""
                        -------------------------------------------------------
                        Registered authors: 
                        """);
            authorsList.forEach(a -> System.out.println(a.toString()));
            System.out.println("-------------------------------------------------------");
        }
    }

    private void showAuthorsAliveInYear() {
        System.out.println("""
                    -------------------------------------------------------
                    Enter the year you want to search: 
                    -------------------------------------------------------
                    """);
        sc = new Scanner(System.in);
        try {
            var yearSearched = sc.nextInt();

            if (yearSearched > LocalDate.now().getYear() || yearSearched < 0) {
                System.out.println("""
                        -------------------------------------------------------
                                            Invalid year.
                        -------------------------------------------------------
                        """);
            } else {
                authorsList = repositoryAuthor.findAuthorsAliveInYear(yearSearched);

                if (authorsList.isEmpty()) {
                    System.out.println("""
                            -------------------------------------------------------
                                      No authors alive in this year.
                            -------------------------------------------------------
                            """);
                } else {
                    System.out.println("""
                            -------------------------------------------------------
                            Authors alive in the year %d: 
                            """.formatted(yearSearched));
                    authorsList.forEach(a -> System.out.println(a.toString()));
                    System.out.println("-------------------------------------------------------");
                }
            }
        } catch (Error e) {
            throw new Error("""
                    -------------------------------------------------------
                                  Invalid year, try again.
                    -------------------------------------------------------
                    """ + e.getMessage());
        }
    }

    private void showTop10Books(){
        booksList = repositoryBook.findTop10ByOrderByDownloadCountDesc();

        if (booksList.isEmpty()) {
            System.out.println("""
                    -------------------------------------------------------
                                    No books registered.
                    -------------------------------------------------------
                    """);
        } else {
            System.out.println("""
                    -------------------------------------------------------
                    Top 10 books: 
                    """);
            booksList.forEach(b -> System.out.println(b.toString()));
            System.out.println("-------------------------------------------------------");
        }
    }

    private void showStatistics(){
        booksList = repositoryBook.findAll();
        authorsList = repositoryAuthor.findAll();

        if(booksList.isEmpty() && authorsList.isEmpty()){
            System.out.println("""
                    -------------------------------------------------------
                                    No books or authors registered.
                    -------------------------------------------------------
                    """);
        } else {
            LongSummaryStatistics statistics = booksList.stream()
                    //.filter(b -> b.getDownloadCount() > 0)
                    //.collect(Collectors.summarizingLong(Book::getDownloadCount));
                    .mapToLong(Book::getDownloadCount)
                    .summaryStatistics();

            System.out.println("""
                    -------------------------------------------------------
                    Statistics:
                    Total of registered books: %d
                    Total of registered authors: %d
                    
                    Total of downloads: %d
                    Average of downloads: %d
                    Maximum of downloads: %d
                    Minimum of downloads: %d
                    -------------------------------------------------------
                    """.formatted(
                            repositoryBook.count(),
                            repositoryAuthor.count(),
                            statistics.getCount(),
                            (int) statistics.getAverage(),
                            statistics.getMax(),
                            statistics.getMin()));
        }
    }
}
