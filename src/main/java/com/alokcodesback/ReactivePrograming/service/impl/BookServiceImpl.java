package com.alokcodesback.ReactivePrograming.service.impl;

import com.alokcodesback.ReactivePrograming.entities.Book;
import com.alokcodesback.ReactivePrograming.repositories.BookRepository;
import com.alokcodesback.ReactivePrograming.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Mono<Book> create(Book book) {
        System.out.println("thread 1 " + Thread.currentThread().getName());
        Mono<Book> cretaedBook=  bookRepository.save(book).doOnNext( data-> {
            System.out.println(" Thread 2 " +Thread.currentThread().getName());
        });
        return cretaedBook;
    }

    @Override
    public Flux<Book> getAll() {
        return bookRepository.findAll()
                .delayElements(Duration.ofSeconds(2l)).log().
                map(book->{
                    book.setName(book.getName().toUpperCase());
                    return book;
                });
    }

    @Override
    public Mono<Book> get(int bookId) {
        Mono<Book> item = bookRepository.findById(bookId);
        return item;
    }

    @Override
    public Mono<Book> update(Book book, int bookId) {
        Mono<Book> oldValue = bookRepository.findById(bookId);
        return oldValue.flatMap(book1->{
            book1.setAuthor(book.getAuthor());
            book1.setDescription(book.getDescription());
            book1.setName(book.getName());
            book1.setPublisher(book.getPublisher());
            return bookRepository.save(book1);
        });
//        return ;
    }

    @Override
    public Mono<Void> delete(int bookId) {
        Mono<Void> deletedItem = bookRepository.deleteById(bookId);
        return deletedItem;
    }

//    @Override
//    public Flux<Book> search(String query) {
//        return null;
//    }

    @Override
    public Flux<Book> search(String query) {
//        return null;
        String newTitleKeyword = "%" + query + "%";
        System.out.println(newTitleKeyword);
        return this.bookRepository.searchBookByName(newTitleKeyword);
    }

//    @Override
    public Flux<Book> searchBooks(String titleKeyword) {
        String newTitleKeyword = "%" + titleKeyword + "%";
        System.out.println(newTitleKeyword);
        return this.bookRepository.searchBookByName(newTitleKeyword);
    }
}
