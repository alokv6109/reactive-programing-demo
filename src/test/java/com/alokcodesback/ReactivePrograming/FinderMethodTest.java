package com.alokcodesback.ReactivePrograming;

import com.alokcodesback.ReactivePrograming.entities.Book;
import com.alokcodesback.ReactivePrograming.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class FinderMethodTest {

    @Autowired
    private BookRepository bookRepository;
    @Test
    public  void findMethodTestingAPI(){
        Flux<Book> byAuthor =  bookRepository.findByAuthor("Alok verma");
        StepVerifier.create(byAuthor).expectNextCount(2).verifyComplete();
    }

    @Test
    public void queryMethodTest(){
//        Flux<Book> allBooksByAuthor = bookRepository.getAllBooksByAuthor("Alok verma").log();
//        StepVerifier.create(allBooksByAuthor).expectNextCount(2).verifyComplete();

        Flux<Book> searchbook = bookRepository.searchBookByName("%book%").log();
        StepVerifier.create(searchbook).expectNextCount(1).verifyComplete();


    }
}
