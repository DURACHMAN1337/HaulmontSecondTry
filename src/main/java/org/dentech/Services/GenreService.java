package org.dentech.Services;

import org.dentech.Entity.Genre;
import org.dentech.Repo.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    GenreRepo genreRepo;

    public void delete(Genre genre){
        genreRepo.delete(genre);
    }

    public void deleteById(Long id){
        genreRepo.deleteById(id);
    }

    public List<Genre> getAll(){
        return genreRepo.findAll();
    }

    public List<Genre> getAllSort(){
        List<Genre> list = genreRepo.findAll();
        Collections.sort(list);
        return list;
    }
    public Optional<Genre> getGenre(String title){
        Genre genre = new Genre();
        genre.setTitle(title);
        return genreRepo.findOne(Example.of(genre));
    }

    public void save(Genre genre){
        genreRepo.save(genre);
    }
}
