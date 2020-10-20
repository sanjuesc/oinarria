package ehu.isad.liburuak.controllers.DB;

import ehu.isad.liburuak.Book;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ZerbitzuKUD {


    // singleton patroia
    private static ZerbitzuKUD instantzia = new ZerbitzuKUD();

    private ZerbitzuKUD() {
    }

    public static ZerbitzuKUD getInstantzia() {
        return instantzia;
    }


    public ResultSet getXehe(String isbn) {
        String query = "select isbn, publisher, pages, title from Xehetasunak where isbn = " + isbn;
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);
        return rs;
    }


    public List<Book> lortuLiburak() {

        String query = "select isbn,izena from Liburu";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        List<Book> emaitza = new ArrayList<>();

        try {
            while (rs.next()) {

                String isbn = rs.getString("isbn");
                String izena = rs.getString("izena");
                emaitza.add(new Book(isbn, izena));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return emaitza;
    }

    public void sartuXehe(String isbn, String publisher, String pages, String title) {
        String query = "insert into Xehetasunak values('"+isbn+"','"+publisher+"','"+pages+"','"+title+"');";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        dbKudeatzaile.execSQL(query);

    }

    public void DBGarbitu(){ //ez da erabiltzen, probak egiterakoan argazkiak kentzeko erabiltzen nuen
        String query = "delete from Xehetasunak";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        dbKudeatzaile.execSQL(query);
    }

    public void kenduArgazkiak(){ //ez da erabiltzen, probak egiterakoan argazkiak kentzeko erabiltzen nuen
        String isbn = null; //horrez gain, ez ditut argazkiak github-era igo nahi
        List<Book> liburuLista = lortuLiburak();
        Iterator<Book> itr = liburuLista.iterator();
        while(itr.hasNext()){
            File f = new File(itr.next().getISBN()+".png");
            f.delete();
        }

    }
}
