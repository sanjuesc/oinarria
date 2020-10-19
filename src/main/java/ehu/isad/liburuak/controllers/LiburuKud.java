package ehu.isad.liburuak.controllers;

import ehu.isad.liburuak.Book;
import ehu.isad.liburuak.Liburuak;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class LiburuKud {

    @FXML
    private ComboBox comboBox;

    @FXML
    private Button botoia;

    private Liburuak mainApp;

    public void setMainApp(Liburuak main) {
        this.mainApp = main;
    }


    @FXML
    void sacatu(ActionEvent event) throws Exception {
        mainApp.xeheErakutsi((Book) comboBox.getValue());
    }

    @FXML
    public void initialize() {
        comboBox.getItems().removeAll();
        ObservableList<Book> books = FXCollections.observableArrayList();
        books.addAll(
                new Book("1491910399", "R for Data Science"),
                new Book("1491946008", "Fluent Python"),
                new Book("9781491906187", "Data Algorithms")
        );
        comboBox.setItems(books);

    }


}
