package ehu.isad.liburuak.controllers;

import com.google.gson.Gson;
import ehu.isad.liburuak.Book;
import ehu.isad.liburuak.Details;
import ehu.isad.liburuak.Liburuak;
import ehu.isad.liburuak.utils.Sarea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;


public class XehetasunakKud {

    private Liburuak mainApp;
    private Gson gson;
    private Book book;

    @FXML
    private Text izenburuText;

    @FXML
    private Text argitalText;

    @FXML
    private Text orriKopText;

    @FXML
    private Button butAtzEg;

    @FXML
    private ImageView irudiaField;

    @FXML
    void atzeraEgin(ActionEvent event) {
        mainApp.liburuErakutsi();
    }

    public void setMainApp(Liburuak liburuak) throws Exception {
        mainApp = liburuak;
    }

    public Book getLib(String s) throws Exception {
        Book emaitza = Sarea.URLlortu(s);
        return emaitza;
    }

    public void egin(Book b) throws Exception {
        String isbn = b.getISBN();
        ResultSet xehe = getXehe(isbn);
        if(xehe.next()){
            izenburuText.setText(xehe.getString("title"));
            argitalText.setText(xehe.getString("publisher"));
            orriKopText.setText(String.valueOf(xehe.getInt("pages")));
            Image Image = new Image(new FileInputStream(isbn+".png"));
            irudiaField.setImage(Image);
        }else{
            book = this.getLib(isbn);
            Details details = book.getDetails();
            String title= details.getTitle();
            izenburuText.setText(title);
            String publisher = details.getArgitaretxea();
            argitalText.setText(publisher);
            publisher = details.getArgitaretxea().replace("\'","\'\'"); //publisher moldatu datu baserako
            String pages =String.valueOf(details.getPages());
            orriKopText.setText(pages);
            String query = "insert into Xehetasunak values('"+isbn+"','"+publisher+"','"+pages+"','"+title+"');";
            System.out.println(query);
            DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
            ResultSet rs = dbKudeatzaile.execSQL(query);
            String url = book.getThumbnail_url().replace("S", "L");
            Image i = createImage(url);
            URL Url = new URL(url);

            //Argazkia modu lokalean gorde
            BufferedImage image = ImageIO.read(Url);
            File outputfile = new File(isbn+".png");
            ImageIO.write(image, "png", outputfile);


            irudiaField.setImage(i);
        }

    }

    private Image createImage(String url) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
        try (InputStream stream = conn.getInputStream()) {
            return new Image(stream);
        }
    }

    public ResultSet getXehe(String isbn){
        String query = "select isbn, publisher, pages, title from Xehetasunak where isbn = "+isbn;
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);
        return rs;
    }

}
