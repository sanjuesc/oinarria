package ehu.isad.liburuak.controllers.UI;
import ehu.isad.liburuak.Book;
import ehu.isad.liburuak.Liburuak;
import ehu.isad.liburuak.controllers.DB.DBKudeatzaile;
import ehu.isad.liburuak.controllers.DB.ZerbitzuKUD;
import ehu.isad.liburuak.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        books.addAll(ZerbitzuKUD.getInstantzia().lortuLiburak());
        comboBox.setItems(books);

    }



}
