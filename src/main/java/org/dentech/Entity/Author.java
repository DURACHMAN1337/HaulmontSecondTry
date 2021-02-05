package org.dentech.Entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "authors")
@EqualsAndHashCode(callSuper = true)
public class Author extends AbstractEntityClass implements Comparable<Author> {

    @NotNull
    @Column(name = "firstname")
    private String firstname;

    @NotNull
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Column(name = "patronymic")
    private String patronymic;

    public Author() {
    }

    public Author(String firstname, String surname, String patronymic) {
        this.firstname = firstname;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        return
                "Имя : " + this.firstname +
                        " Фамилия : " + this.surname +
                        " Отчество : " + this.patronymic;
    }

    @Override
    public int compareTo(Author author) {
        return author.getSurname().toLowerCase().compareTo(this.getSurname().toLowerCase());
    }
}
