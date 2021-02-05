package org.dentech.Services;

import org.dentech.Entity.Author;
import org.dentech.Entity.Book;
import org.dentech.Entity.Genre;
import org.dentech.Entity.Publisher;
import org.dentech.Repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {


    final BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public void delete(Book book){
        bookRepo.delete(book);
    }

    public void deleteById(Long id){
        bookRepo.deleteById(id);
    }

    public List<Book> getAll(){
        return bookRepo.findAll();
    }

    public Optional<Book> getBookByName(String name){
        Book book = new Book();
        book.setName(name);
        return bookRepo.findOne(Example.of(book));
    }

    public Optional<Book> getBookByAuthor(Author author){
        Book book = new Book();
        book.setAuthor(author);
        return bookRepo.findOne(Example.of(book));
    }

    public Optional<Book> getBookByPublisher(Publisher publisher){
        Book book = new Book();
        book.setPublisher(publisher);
        return bookRepo.findOne(Example.of(book));
    }

    public List<Book> getBooksByGenre(Genre genre){
        List<Book> list = new ArrayList<>();
        for (Book book : bookRepo.findAll()){
            if(book.getGenre() == genre)
                list.add(book);
        }
        return list;
    }

    public void save(Book book){
        bookRepo.save(book);
    }

}
