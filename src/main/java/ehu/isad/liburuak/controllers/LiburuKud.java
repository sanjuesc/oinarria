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

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        books.addAll(lortuLiburak());
        comboBox.setItems(books);

    }


    public List<Book> lortuLiburak(){

        String query = "select isbn, izena from Liburu";
        DBKudeatzailea dbKudeatzaile = DBKudeatzailea.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        List<Book> emaitza = new ArrayList<>();

        try {
            while (rs.next()) {

                String isbn = rs.getString("isbn");
                String izena = rs.getString("izena");
                emaitza.add(new Book (isbn, izena));
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return emaitza;


    }



}
