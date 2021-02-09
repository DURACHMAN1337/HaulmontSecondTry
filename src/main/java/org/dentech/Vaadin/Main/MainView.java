package org.dentech.Vaadin.Main;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;
import java.io.File;

@SpringView(name = "")
public class MainView extends VerticalLayout implements View {


    @PostConstruct
    void init() {
        FileResource booksFileResource = new FileResource(new File("src/main/resources/images/books.png"));
        FileResource authorsFileResource = new FileResource(new File("src/main/resources/images/socrates.png"));
        FileResource genresFileResource = new FileResource(new File("src/main/resources/images/drama.png"));

        HorizontalLayout headerLayout = new HorizontalLayout();
        HorizontalLayout picturesLayout = new HorizontalLayout();
        VerticalLayout booksLayout = new VerticalLayout();
        VerticalLayout authorLayout = new VerticalLayout();
        VerticalLayout genresLayout = new VerticalLayout();

        Image authorImage = new Image("", authorsFileResource);
        Image booksImage = new Image("", booksFileResource);
        Image genresImage = new Image("", genresFileResource);
        Label headerLabel = new Label("My Library");

        authorImage.setWidth("30%");
        booksImage.setWidth("30%");
        genresImage.setWidth("30%");


        Label authorLabel = new Label("Список Авторов");
        authorLabel.addStyleName(ValoTheme.LABEL_BOLD);
        authorLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);

        Label bookLabel = new Label("Список Книг");
        bookLabel.addStyleName(ValoTheme.LABEL_BOLD);
        bookLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);

        Label genreLabel = new Label("Список Жанров");
        genreLabel.addStyleName(ValoTheme.LABEL_BOLD);
        genreLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);

        headerLayout.addComponent(headerLabel);
        headerLayout.setComponentAlignment(headerLabel, Alignment.MIDDLE_CENTER);
        headerLabel.addStyleName(ValoTheme.LABEL_H1);
        headerLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        headerLabel.setWidth("50%");

        authorLayout.addComponents(authorImage, authorLabel);
        authorLayout.setComponentAlignment(authorLabel, Alignment.TOP_CENTER);
        authorLayout.setComponentAlignment(authorImage, Alignment.TOP_CENTER);
        authorLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("Authors");

        });
        authorLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        genresLayout.addComponents(genresImage, genreLabel);
        genresLayout.setComponentAlignment(genreLabel, Alignment.TOP_CENTER);
        genresLayout.setComponentAlignment(genresImage, Alignment.TOP_CENTER);
        genresLayout.addLayoutClickListener(layoutClickEvent -> {
            getUI().getNavigator().navigateTo("Genres");
        });
        genresLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        booksLayout.addComponents(booksImage, bookLabel);
        booksLayout.setComponentAlignment(bookLabel,Alignment.TOP_CENTER);
        booksLayout.setComponentAlignment(booksImage,Alignment.TOP_CENTER);
        booksLayout.addLayoutClickListener(layoutClickEvent -> {
            getUI().getNavigator().navigateTo("Books");
        });
        booksLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        picturesLayout.setWidth("100%");
        picturesLayout.addComponents(authorLayout,genresLayout,booksLayout);

        addComponents(headerLayout,picturesLayout);


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
