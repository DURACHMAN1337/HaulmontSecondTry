package org.dentech.Entity;


import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "genres")
public class Genre extends AbstractEntityClass implements Comparable<Genre>{

    @NotNull
    @Column(name = "title")
    private String title;



    public Genre() {
    }



    @Override
    public String toString() {
        return
                "Жанр :" + this.title;

    }

    @Override
    public int compareTo(Genre genre) {
        return genre.getTitle().toLowerCase().compareTo(this.getTitle().toLowerCase());
    }
}
