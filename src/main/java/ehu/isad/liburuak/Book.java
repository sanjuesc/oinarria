package ehu.isad.liburuak;

import ehu.isad.liburuak.Details;

public class Book {
    String isbn;
    String title;

    String info_url;
    String bib_key;
    String preview_url;
    String thumbnail_url;
    Details details;

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getISBN(){
        return isbn;
    }

    public Details getDetails(){
        return details;
    }

    public String getThumbnail_url(){
        return thumbnail_url;
    }



}