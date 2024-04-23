package com.alokcodesback.ReactivePrograming.controller;

import com.alokcodesback.ReactivePrograming.entities.Book;
import com.alokcodesback.ReactivePrograming.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Mono<Book> create(@RequestBody Book book){
        return bookService.create(book);

    }

    @GetMapping("/")
    public Flux<Book> getAllBooks(){
        return bookService.getAll();
    }

    @GetMapping("/{bookId}")
    public Mono<Book> getSingleBook(@PathVariable int bookId){
        return bookService.get(bookId);
    }

    @PutMapping("/{bookId}")
    public Mono<Book> updateBook(@PathVariable int bookId, @RequestBody Book book){
        return bookService.update(book, bookId);
    }

    @DeleteMapping("/{bookId}")
    public Mono<Void> deleteBook(@PathVariable int bookId){
        return bookService.delete(bookId);

    }

    @GetMapping("/search")
    public Flux<Book> searchTheBooks(
            @RequestParam("query") String query
    ){
        System.out.println(query);
        return this.bookService.search(query);
    }
}
