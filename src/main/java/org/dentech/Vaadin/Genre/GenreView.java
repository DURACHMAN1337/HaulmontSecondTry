package org.dentech.Vaadin.Genre;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entity.Author;
import org.dentech.Entity.Genre;
import org.dentech.Services.BookService;
import org.dentech.Services.GenreService;
import org.dentech.Vaadin.Author.AuthorWindow;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "Genres")
public class GenreView extends VerticalLayout implements View {

    @Autowired
    GenreService genreService;

    @Autowired
    BookService bookService;

    private Button addButton = new Button("Добавить жанр");
    private Button deleteButton = new Button("Удалить жанр");
    private Button editButton = new Button("Изменить");
    private Button statButton = new Button("Статистика жанра");

    public static Grid<Genre> genreGrid = new Grid<>(Genre.class);

    @PostConstruct
    void init() {
        Page.getCurrent().setTitle("Genres");
        HorizontalLayout headerLayout = new HorizontalLayout();
        Label header = new Label("Список Жанров");
        header.setStyleName(ValoTheme.LABEL_H1);
        headerLayout.addComponent(header);
        headerLayout.setWidth("100%");
        headerLayout.setComponentAlignment(header, Alignment.MIDDLE_CENTER);
        addComponent(headerLayout);

        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
        statButton.setEnabled(false);

        addButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton, editButton, statButton, deleteButton);
        buttonsLayout.setSizeFull();
        buttonsLayout.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);
        buttonsLayout.setComponentAlignment(editButton, Alignment.TOP_CENTER);
        addComponent(buttonsLayout);

        genreGrid.setSizeFull();
        genreGrid.setColumns("title");
        genreGrid.setItems(genreService.getAll());

        addComponent(genreGrid);

        genreGrid.addSelectionListener(valueChangeEvent -> {
            if (!genreGrid.asSingleSelect().isEmpty()) {
                deleteButton.setEnabled(true);
                editButton.setEnabled(true);
                statButton.setEnabled(true);
            } else {
                deleteButton.setEnabled(false);
                editButton.setEnabled(false);
                statButton.setEnabled(false);
            }
        });

        addButton.addClickListener(e -> {
            Genre genre = new Genre();
            GenreWindow genreWindow = new GenreWindow(genreService, genre);
            getUI().addWindow(genreWindow);
        });

        editButton.addClickListener(e -> {
            Genre genre = genreGrid.asSingleSelect().getValue();
            GenreWindow genreWindow = new GenreWindow(genreService, genre);
            getUI().addWindow(genreWindow);
        });

        deleteButton.addClickListener(e -> {
            Genre genre = genreGrid.asSingleSelect().getValue();

            try {
                genreService.delete(genre);
                updateGenreGrid(genreService);
                Notification notification = new Notification(genre.toString() + " был успешно удален",
                        Notification.Type.WARNING_MESSAGE);
                notification.setDelayMsec(1500);
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.show(getUI().getPage());
            } catch (Exception deleteException) {
                Notification notification = new Notification("Что-то пошло не так :С",
                        Notification.Type.WARNING_MESSAGE);
                notification.show(getUI().getPage());
            }
        });
        statButton.addClickListener(clickEvent -> {
            Genre genre = genreGrid.asSingleSelect().getValue();
            Notification notification = new Notification("Количество книг жанра " + genre.getTitle() + " : " + bookService.getBooksByGenre(genre).size(),
                    Notification.Type.HUMANIZED_MESSAGE);
            notification.show(getUI().getPage());
        });



    }

    static void updateGenreGrid(GenreService genreService) {
        genreGrid.setItems(genreService.getAll());
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
