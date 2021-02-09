package org.dentech.Vaadin.Author;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.dentech.Entity.Author;
import org.dentech.Services.AuthorService;


public class AuthorWindow extends Window implements View {

    private final TextField firstnameField = new TextField("Имя");
    private final TextField surnameField = new TextField("Фамилия");
    private final TextField patronymicField = new TextField("Отчество");

    private final Button saveButton = new Button("Сохранить", VaadinIcons.CLOUD);
    private final Button cancelButton = new Button("Отмена");

    private final AuthorService authorService;
    private final Author author;

    public AuthorWindow(AuthorService authorService, Author author) {
        this.authorService = authorService;
        this.author = author;
        setCaption("Добавление/Изменение Данных автора");
        setModal(true);
        center();
        setContent(createContent());
    }

    public Component createContent() {
        VerticalLayout main = new VerticalLayout();
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        HorizontalLayout row1 = new HorizontalLayout();

        buttonsLayout.addComponents(saveButton, cancelButton);
        row1.addComponents(surnameField,firstnameField,patronymicField);
        main.addComponents(row1, buttonsLayout);

        try {
            firstnameField.setValue(author.getFirstname());
            surnameField.setValue(author.getSurname());
            patronymicField.setValue(author.getPatronymic());

        } catch (Exception ignored) {
        }

        firstnameField.setRequiredIndicatorVisible(true);
        surnameField.setRequiredIndicatorVisible(true);
        patronymicField.setRequiredIndicatorVisible(true);

        cancelButton.addClickListener(event -> getUI().removeWindow(AuthorWindow.this));
        saveButton.addClickListener(event -> this.save());
        saveButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        return main;
    }

    private void save() {
        if (validateFIO(firstnameField.getValue(), surnameField.getValue(), patronymicField.getValue())) {

            author.setFirstname(firstnameField.getValue().trim());
            author.setSurname(surnameField.getValue().trim());
            author.setPatronymic(patronymicField.getValue().trim());
            authorService.save(author);
            Notification notification = new Notification("Автор успешно добавлен",
                    Notification.Type.HUMANIZED_MESSAGE);
            notification.setDelayMsec(1500);
            notification.show(getUI().getPage());
            getUI().removeWindow(AuthorWindow.this);
            AuthorView.updateAuthorGrid(authorService);


        } else {
            new Notification("Ошибка! Проверьте что в ФИО отстутсвуют цифры и другие символы",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
    }

    public boolean validateFIO(String firstname, String surname, String patronymic) {
        char[] firstnameArray = firstname.toCharArray();
        char[] surnameArray = surname.toCharArray();
        char[] patronymicArray = patronymic.toCharArray();

        for (Character ch : firstnameArray)
            if (Character.isDigit(ch)) return false;

        for (Character ch : surnameArray)
            if (Character.isDigit(ch)) return false;

        for (Character ch : patronymicArray)
            if (Character.isDigit(ch)) return false;

        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
