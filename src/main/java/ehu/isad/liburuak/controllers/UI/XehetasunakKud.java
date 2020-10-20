package ehu.isad.liburuak.controllers.UI;

import com.google.gson.Gson;
import ehu.isad.liburuak.Book;
import ehu.isad.liburuak.Details;
import ehu.isad.liburuak.Liburuak;
import ehu.isad.liburuak.controllers.DB.DBKudeatzaile;
import ehu.isad.liburuak.controllers.DB.ZerbitzuKUD;
import ehu.isad.liburuak.utils.Sarea;
import ehu.isad.liburuak.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
import java.util.Properties;


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
        Properties properties = Utils.lortuEzarpenak();
        ResultSet xehe = ZerbitzuKUD.getInstantzia().getXehe(isbn);
        if(xehe.next()){
            izenburuText.setText(xehe.getString("title"));
            argitalText.setText(xehe.getString("publisher"));
            orriKopText.setText(String.valueOf(xehe.getInt("pages")));
            Image Image = new Image(new FileInputStream(properties.getProperty("path")+isbn+".png"));
            irudiaField.setImage(Image);
        }else{
            book = this.getLib(isbn);
            Details details = book.getDetails();
            String title= details.getTitle();
            String publisher = details.getArgitaretxea();
            String pages =String.valueOf(details.getPages());
            bistaratuXehetasunak(title, pages, publisher);
            publisher = details.getArgitaretxea().replace("\'","\'\'"); //publisher moldatu datu baserako
            ZerbitzuKUD.getInstantzia().sartuXehe(isbn, publisher, pages, title);
            String url = book.getThumbnail_url().replace("S", "L");
            URL Url = new URL(url);
            gordeArgazkia(Url, isbn);
            Image i = createImage(url);
            irudiaField.setImage(i);
        }

    }

    private void gordeArgazkia(URL Url, String isbn) throws IOException {
        Properties properties = Utils.lortuEzarpenak();
        BufferedImage image = ImageIO.read(Url);
        File outputfile = new File(properties.getProperty("path")+isbn+".png");
        ImageIO.write(image, "png", outputfile);

    }

    private void bistaratuXehetasunak(String title, String pages, String publisher) {
        orriKopText.setText(pages);
        izenburuText.setText(title);
        argitalText.setText(publisher);
    }

    private Image createImage(String url) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
        try (InputStream stream = conn.getInputStream()) {
            return new Image(stream);
        }
    }

}
