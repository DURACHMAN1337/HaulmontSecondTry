package org.dentech.Vaadin.Author;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.dentech.Entity.Author;
import org.dentech.Entity.Publisher;
import org.dentech.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "Authors")
public class AuthorView extends VerticalLayout implements View {

    @Autowired
    AuthorService authorService;

    @PostConstruct
    void init(){
        Grid<Author> authorGrid = new Grid<>(Author.class);
        Button add = new Button("+");
        add.addClickListener(clickEvent -> {
            authorService.save(new Author("Иванов","Иван","Иванович"));
            updateAuthorGrid(authorGrid);
        });
        authorGrid.setItems(authorService.getAll());
        addComponents(add,authorGrid);

    }

    public void updateAuthorGrid(Grid<Author> grid){
        grid.setItems(authorService.getAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
