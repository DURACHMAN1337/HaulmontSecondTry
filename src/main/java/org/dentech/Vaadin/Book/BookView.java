package org.dentech.Vaadin.Book;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entity.Book;
import org.dentech.Entity.Genre;
import org.dentech.Services.AuthorService;
import org.dentech.Services.BookService;
import org.dentech.Services.GenreService;
import org.dentech.Vaadin.Genre.GenreWindow;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "Books")
public class BookView extends VerticalLayout implements View {

    @Autowired
    BookService bookService;

    @Autowired
    AuthorService authorService;

    @Autowired
    GenreService genreService;

    private Button addButton = new Button("Добавить книгу");
    private Button deleteButton = new Button("Удалить книгу");
    private Button editButton = new Button("Изменить данные");

    public static Grid<Book> bookGrid = new Grid<>(Book.class);

    @PostConstruct
    void init() {
        Page.getCurrent().setTitle("Books");
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
        addButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton, editButton, deleteButton);
        buttonsLayout.setSizeFull();
        buttonsLayout.setComponentAlignment(editButton, Alignment.TOP_CENTER);
        buttonsLayout.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);

        bookGrid.setSizeFull();
        bookGrid.setColumns("name", "author", "genre", "publisher", "year", "city");
        bookGrid.setItems(bookService.getAll());

        addComponent(createSearcher());
        addComponent(buttonsLayout);
        addComponent(bookGrid);

        bookGrid.addSelectionListener(valueChangeEvent -> {
            if (!bookGrid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        addButton.addClickListener(e -> {
            Book book = new Book();
            BookWindow bookWindow = new BookWindow(bookService,book,authorService,genreService);

            getUI().addWindow(bookWindow);
        });

        editButton.addClickListener(e -> {
            Book book = bookGrid.asSingleSelect().getValue();
            BookWindow bookWindow = new BookWindow(bookService,book,authorService,genreService);
            getUI().addWindow(bookWindow);
        });



    }

    private HorizontalLayout createSearcher() {
        HorizontalLayout mainLayout = new HorizontalLayout();
        Label label = new Label("Поиск: ");
        label.setSizeFull();
        HorizontalLayout labelLayout = new HorizontalLayout();
        labelLayout.setWidth("100%");
        labelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        labelLayout.addComponents(label);

        TextField nameField = new TextField();
        nameField.setPlaceholder("Название Книги");
        TextField authorField = new TextField();
        authorField.setPlaceholder("Автор");
        TextField publisherFiled = new TextField();
        publisherFiled.setPlaceholder("Издательство");



        nameField.addValueChangeListener(this::nameFilter);
        authorField.addValueChangeListener(this::authorFilter);
        publisherFiled.addValueChangeListener(this::publisherFilter);


        HorizontalLayout filters = new HorizontalLayout(labelLayout, nameField, authorField, publisherFiled);
        mainLayout.addComponents(filters);
        mainLayout.setWidth("100%");
        return mainLayout;
    }

    private void nameFilter(HasValue.ValueChangeEvent<String> stringValueChangeEvent){
        ListDataProvider<Book> dataProvider = (ListDataProvider<Book>) bookGrid.getDataProvider();
        dataProvider.setFilter(Book::getName, name ->
                name.toLowerCase().contains(stringValueChangeEvent.getValue().toLowerCase()));
    }

    private void authorFilter(HasValue.ValueChangeEvent<String> stringValueChangeEvent) {
        ListDataProvider<Book> dataProvider = (ListDataProvider<Book>) bookGrid.getDataProvider();
        dataProvider.setFilter(Book::getAuthor, author ->
                author.toString().toLowerCase().contains(stringValueChangeEvent.getValue().toLowerCase()));
    }

    private void publisherFilter(HasValue.ValueChangeEvent<String> stringValueChangeEvent){
        ListDataProvider<Book> dataProvider = (ListDataProvider<Book>) bookGrid.getDataProvider();
        dataProvider.setFilter(Book::getPublisher,publisher ->
                publisher.toString().toLowerCase().contains(stringValueChangeEvent.getValue().toLowerCase()));
    }

    static void updateBookGrid(BookService bookService) {
        bookGrid.setItems(bookService.getAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
