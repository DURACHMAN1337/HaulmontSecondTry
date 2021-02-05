package org.dentech.Services;

import org.dentech.Entity.Author;
import org.dentech.Repo.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepo authorRepo;

    public AuthorService() {
    }

    public void delete(Author author){
        authorRepo.delete(author);
    }

    public void deleteById(Long id){
        authorRepo.deleteById(id);
    }

    public List<Author> getAll(){
        return authorRepo.findAll();
    }

    public List<Author> getAllSort(){
        List<Author> list = authorRepo.findAll();
        Collections.sort(list);
        return list;
    }

    public void save(Author author){
        authorRepo.save(author);
    }
}
