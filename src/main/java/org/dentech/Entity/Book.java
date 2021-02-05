package org.dentech.Entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "books")
public class Book extends AbstractEntityClass implements Comparable<Book>{

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull
    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @NotNull
    @Column(name = "publisher")
    private Publisher publisher;

    @NotNull
    @Column(name = "year")
    private Integer year;

    @NotNull
    @Column(name = "city")
    private String city;

    public Book() {
    }

    public Book(String name, Author author, Genre genre, Publisher publisher, Integer year, String city) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.year = year;
        this.city = city;
    }

    @Override
    public String toString() {
        return
                "Название : " + this.name +
                " Автор : " + this.author +
                " Жанр :" + this.genre +
                " Издательство :" + this.publisher +
                " Год выпуска :" + this.year +
                " Город : " + this.city;

    }

    @Override
    public int compareTo(Book book) {
        return book.getName().toLowerCase().compareTo(this.getName().toLowerCase());
    }
}
