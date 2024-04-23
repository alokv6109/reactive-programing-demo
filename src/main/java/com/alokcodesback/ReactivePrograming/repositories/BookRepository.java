package com.alokcodesback.ReactivePrograming.repositories;

import com.alokcodesback.ReactivePrograming.entities.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Integer> {
//    Customr finder methods
    Mono<Book> findByName(String name);
    Flux<Book> findByAuthor(String name);
    Flux<Book> findByPublisher(String name);

    Flux<Book> findByNameAndAuthor(String name, String author);

//    for the query methods
    @Query("select * from book where author = :author")
    Flux<Book> getAllBooksByAuthor(String author); //her ethe name in the query and the name above:name shoul match

//    se;ect * from book where name =:name and author=:author

    //for searching
    @Query("select * from book where name like :title")
    Flux<Book> searchBookByName(String title);
}
