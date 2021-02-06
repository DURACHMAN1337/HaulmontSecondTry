package org.dentech.Vaadin.Genre;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entity.Genre;
import org.dentech.Services.GenreService;
import org.dentech.Vaadin.Author.AuthorView;
import org.dentech.Vaadin.Author.AuthorWindow;


public class GenreWindow extends Window implements View {

    private final TextField titleField = new TextField("Название жанра");

    private final Button saveButton = new Button("Сохранить", VaadinIcons.CLOUD);
    private final Button cancelButton = new Button("Отмена");

    private final GenreService genreService;
    private final Genre genre;

    public GenreWindow(GenreService genreService, Genre genre) {
        this.genreService = genreService;
        this.genre = genre;
        setCaption("Добавление/Изменение Жанра");
        setModal(true);
        center();
        setContent(createContent());

    }

    private Component createContent() {
        VerticalLayout main = new VerticalLayout();
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        HorizontalLayout row1 = new HorizontalLayout();

        buttonsLayout.addComponents(saveButton, cancelButton);
        saveButton.setIcon(VaadinIcons.CLOUD);
        saveButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        cancelButton.setStyleName(ValoTheme.BUTTON_DANGER);
        row1.addComponents(titleField);
        row1.setWidth("100%");
        row1.setComponentAlignment(titleField, Alignment.MIDDLE_CENTER);
        main.addComponents(row1, buttonsLayout);

        try {
            titleField.setValue(genre.getTitle());

        } catch (Exception ignored) {
        }

        titleField.setRequiredIndicatorVisible(true);

        cancelButton.addClickListener(event -> getUI().removeWindow(GenreWindow.this));
        saveButton.addClickListener(event -> this.save());
        saveButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        return main;

    }

    private void save() {
        if (genreService.getGenre(titleField.getValue()).isEmpty()) {
            if (validateTitle(titleField.getValue().trim())) {


                genre.setTitle(titleField.getValue().trim());
                genreService.save(genre);
                Notification notification = new Notification("Жанр успешно добавлен",
                        Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
                getUI().removeWindow(GenreWindow.this);
                GenreView.updateGenreGrid(genreService);
            }
            else {
                new Notification("Ошибка! Жанр содержит запрещенные символы",
                        Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
            }

        } else {
            new Notification("Ошибка! Такой жанр уже есть",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }



    }

    public boolean validateTitle(String title) {
        char[] titleArray = title.toCharArray();

        for (Character ch : titleArray)
            if (Character.isDigit(ch)) return false;

        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {


    }
}
