package org.dentech.Vaadin.Author;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entity.Author;
import org.dentech.Entity.Publisher;
import org.dentech.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "Authors")
public class AuthorView extends VerticalLayout implements View {

    @Autowired
    AuthorService authorService;

    private Button addButton = new Button("Добавить автора");
    private Button deleteButton = new Button("Удалить автора");
    private Button editButton = new Button("Изменить данные");

    public static Grid<Author> authorGrid = new Grid<>(Author.class);

    @PostConstruct
    void init() {
        Page.getCurrent().setTitle("Authors");
        HorizontalLayout headerLayout = new HorizontalLayout();
        Label header = new Label("Список Авторов");
        header.setStyleName(ValoTheme.LABEL_H1);
        headerLayout.addComponent(header);
        headerLayout.setWidth("100%");
        headerLayout.setComponentAlignment(header, Alignment.MIDDLE_CENTER);
        addComponent(headerLayout);

        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        addButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton, editButton, deleteButton);
        buttonsLayout.setSizeFull();
        buttonsLayout.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);
        buttonsLayout.setComponentAlignment(editButton, Alignment.TOP_CENTER);
        addComponent(buttonsLayout);

        authorGrid.setSizeFull();
        authorGrid.setColumns("firstname","surname","patronymic");
        authorGrid.setItems(authorService.getAll());

        addComponent(authorGrid);

        authorGrid.addSelectionListener(valueChangeEvent -> {
            if (!authorGrid.asSingleSelect().isEmpty()) {
                deleteButton.setEnabled(true);
                editButton.setEnabled(true);
            } else {
                deleteButton.setEnabled(false);
                editButton.setEnabled(false);
            }
        });

        addButton.addClickListener(e -> {
            Author author = new Author();
            AuthorWindow authorWindow = new AuthorWindow(authorService, author);
            getUI().addWindow(authorWindow);
        });

        editButton.addClickListener(e -> {
            Author author = authorGrid.asSingleSelect().getValue();
            AuthorWindow authorWindow = new AuthorWindow(authorService, author);
            getUI().addWindow(authorWindow);
        });

        deleteButton.addClickListener(e -> {
            Author author = authorGrid.asSingleSelect().getValue();

            try {
                authorService.delete(author);
                updateAuthorGrid(authorService);
                Notification notification = new Notification(author.toString() + " был успешно удален",
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


    }

    static void updateAuthorGrid(AuthorService authorService) {
        authorGrid.setItems(authorService.getAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
