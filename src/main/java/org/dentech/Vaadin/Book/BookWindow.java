package org.dentech.Vaadin.Book;


import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entity.Author;
import org.dentech.Entity.Book;
import org.dentech.Entity.Genre;
import org.dentech.Entity.Publisher;
import org.dentech.Services.AuthorService;
import org.dentech.Services.BookService;
import org.dentech.Services.GenreService;
import org.dentech.Vaadin.Genre.GenreView;
import org.dentech.Vaadin.Genre.GenreWindow;

import java.util.Collections;
import java.util.List;

public class BookWindow extends Window implements View {

    private VerticalLayout windowLayout = new VerticalLayout();
    private TextField nameField = new TextField("Название книги");
    private NativeSelect<Author> authorNativeSelect;
    private NativeSelect<Genre> genreNativeSelect;
    private NativeSelect<Publisher> publisherNativeSelect;
    private TextField yearField = new TextField("Год");
    private TextField cityField = new TextField("Город");

    private Button saveButton = new Button("Сохранить", VaadinIcons.CLOUD);
    private Button cancelButton = new Button("Отмена");

    private BookService bookService;
    private AuthorService authorService;
    private GenreService genreService;

    private Book book;




    public BookWindow(BookService bookService, Book book,AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.book = book;
        this.authorService = authorService;
        this.genreService = genreService;
        setCaption("Добавление/Изменение Данных книги");
        setModal(true);
        center();
        setContent(createContent());
    }

    private Component createContent() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        saveButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        cancelButton.setStyleName(ValoTheme.BUTTON_DANGER);
        buttonsLayout.addComponents(saveButton, cancelButton);


        List<Author> authors = authorService.getAll();
        authorNativeSelect = new NativeSelect<>("Выберите автора ", authors);

        List<Genre> genres = genreService.getAll();
        genreNativeSelect = new NativeSelect<>("Выберите жанр ", genres);

        List<Publisher> publishers = List.of(Publisher.OReilly, Publisher.Москва, Publisher.Питер);
        publisherNativeSelect = new NativeSelect<>("Выберите издательство", publishers);

        HorizontalLayout row1 = new HorizontalLayout();
        HorizontalLayout row2 = new HorizontalLayout();

        row1.addComponents(nameField, authorNativeSelect, genreNativeSelect);
        row2.addComponents(publisherNativeSelect, yearField, cityField);


        windowLayout.addComponents(row1, row2, buttonsLayout);

        try {
            nameField.setValue(book.getName());
            yearField.setValue(book.getYear().toString());
            cityField.setValue(book.getCity());


        } catch (Exception ignored) {
        }

        nameField.setRequiredIndicatorVisible(true);
        authorNativeSelect.setRequiredIndicatorVisible(true);
        genreNativeSelect.setRequiredIndicatorVisible(true);
        publisherNativeSelect.setRequiredIndicatorVisible(true);
        yearField.setRequiredIndicatorVisible(true);
        cityField.setRequiredIndicatorVisible(true);


        cancelButton.addClickListener(event -> getUI().removeWindow(BookWindow.this));
        saveButton.addClickListener(event -> this.save());
        saveButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);


        return windowLayout;

    }

    private void save() {
        if (bookService.getBook(nameField.getValue(),authorNativeSelect.getValue(),genreNativeSelect.getValue(),
                publisherNativeSelect.getValue(),Integer.parseInt(yearField.getValue()),cityField.getValue()).isEmpty()) {
            if (validateСity(cityField.getValue()) && yearField.getValue().length() == 4) {


                book.setName(nameField.getValue());
                book.setAuthor(authorNativeSelect.getValue());
                book.setGenre(genreNativeSelect.getValue());
                book.setPublisher(publisherNativeSelect.getValue());
                book.setYear(Integer.parseInt(yearField.getValue()));
                book.setCity(cityField.getValue());
                bookService.save(book);
                Notification notification = new Notification("Книга успешно добавлена",
                        Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
                getUI().removeWindow(BookWindow.this);
                BookView.updateBookGrid(bookService);
            }
            else {
                new Notification("Ошибка! Город не может содержать такие символы , а год должен состоять из 4 цифр",
                        Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
            }

        } else {
            new Notification("Ошибка! Такая книга уже есть",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }



    }

    public boolean validateСity(String city) {
        char[] titleArray = city.toCharArray();

        for (Character ch : titleArray)
            if (Character.isDigit(ch)) return false;

        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
