package org.dentech.Services;

import org.dentech.Entity.Genre;
import org.dentech.Repo.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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

    public void save(Genre genre){
        genreRepo.save(genre);
    }
}
