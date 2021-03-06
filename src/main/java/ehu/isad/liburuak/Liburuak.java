package ehu.isad.liburuak;


import ehu.isad.liburuak.controllers.UI.LiburuKud;
import ehu.isad.liburuak.controllers.UI.XehetasunakKud;
import ehu.isad.liburuak.utils.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Liburuak extends Application {


    private Parent liburuUI;
    private Parent xeheUI;

    private Stage stage;

    private LiburuKud liburuak;
    private XehetasunakKud xehetasunak;

    private Book lib;

    private Scene libKudScene ; // Liburua aukeratzeko Scene
    private Scene xeheScene ; // Xehetasunak ikusteko Scene


    @Override
    public void start(Stage primaryStage) throws Exception {
        karpetaSortu();
        stage = primaryStage;
        pantailakKargatu();

        libKudScene = new Scene(liburuUI,600,400);
        xeheScene = new Scene(xeheUI,800,500);

        stage.setTitle("Liburuak ariketa");
        stage.setScene(libKudScene);
        stage.show();
    }

    private void pantailakKargatu() throws Exception {

        FXMLLoader loaderliburu = new FXMLLoader(getClass().getResource("/Liburuak.fxml"));
        liburuUI = (Parent) loaderliburu.load();
        liburuak = loaderliburu.getController();
        liburuak.setMainApp(this);

        FXMLLoader loaderxehe = new FXMLLoader(getClass().getResource("/Xehetasunak.fxml"));
        xeheUI = (Parent) loaderxehe.load();
        xehetasunak = loaderxehe.getController();
        xehetasunak.setMainApp(this);
    }


    public static void main(String[] args) {  //Jon Ander Asua, Ena Verhorst eta Ander San Juan
        launch(args);
    }

    public void liburuErakutsi() {
        stage.setScene(libKudScene);
        stage.show();
    }

    public void xeheErakutsi(Book b ) throws Exception {
        xehetasunak.egin(b);
        stage.setScene(xeheScene);
        stage.show();
    }
    public void karpetaSortu() throws IOException {
        Properties properties = Utils.lortuEzarpenak();
        Path path = Paths.get(properties.getProperty("imagePath"));
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
    }

}


