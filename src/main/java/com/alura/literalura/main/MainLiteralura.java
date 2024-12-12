package com.alura.literalura.main;

import com.alura.literalura.model.Book;
import com.alura.literalura.model.DataBook;
import com.alura.literalura.model.DataResult;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.service.ConsumeAPI;
import com.alura.literalura.service.ConvertData;

import java.util.Scanner;

public class MainLiteralura {
    private Scanner sc = new Scanner(System.in);
    private final String URL_BASE = "http://gutendex.com/books/?search=";
    private ConvertData converter = new ConvertData();
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private BookRepository repository;

    public MainLiteralura(BookRepository repository) {
        this.repository = repository;
    }

    public void showMenu(){
        var option = -1;
        while (option != 0){
            var menu = """
                    1 - Search for books by title
                    
                    2 - Show registered books
                    3 - Show registered books by language
                    4 - Show registered authors
                    5 - Show authors alive in a given year
                    
                    0 - Exit
                    """;
            System.out.println(menu);
            option = sc.nextInt();
            sc.nextLine();

            switch (option){
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    //showRegisteredBooks();
                    break;
                case 3:
                    //showRegisteredBooksByLanguage();
                    break;
                case 4:
                    //showRegisteredAuthors();
                    break;
                case 5:
                    //showAuthorsAliveInYear();
                    break;
                case 0:
                    System.out.println("Closing application...");
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, try again.");
                    break;
            }
        }
    }

    private void searchBookByTitle() {
        System.out.println("Enter the title of the book you want to search: ");
        var titleBook = sc.nextLine();

        var json = consumeAPI.obtainData(URL_BASE + titleBook.replace(" ", "+"));
        System.out.println(json);

        DataResult dataResult = converter.obtainData(json, DataResult.class);
        var result = dataResult.results().get(0);

        DataBook dataBook = new DataBook(result.title(), result.authors(), result.language(), result.downloadCount());
        System.out.println(dataBook);

        var bookSaved = repository.findByTitleContainsIgnoreCase(dataBook.title());

        if(bookSaved.isPresent()){
            System.out.println("Book already registered.");
        } else {
            Book book = new Book(dataBook);
            repository.save(book);
            System.out.println("Book registered successfully.");
        }
    }
}
