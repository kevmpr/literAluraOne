package com.alura.literalura;

import com.alura.literalura.main.MainLiteralura;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner{

	@Autowired
	private BookRepository repositoryBook;
	@Autowired
    private AuthorRepository repositoryAuthor;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MainLiteralura mainLiteralura = new MainLiteralura(repositoryBook, repositoryAuthor);
		mainLiteralura.showMenu();
	}
}
