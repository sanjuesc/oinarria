package ehu.isad.liburuak;

import java.util.Arrays;

public class Details {
    String[] publishers;
    Integer number_of_pages;
    String title;

    @Override
    public String toString() {
        return "Details{" +
                "publishers=" + Arrays.toString(publishers) +
                ", number_of_pages=" + number_of_pages +
                ", title='" + title + '\'' +
                '}';
    }

    public String getArgitaretxea() {
        return publishers[0];
    }

    public String getTitle() {
        return title;
    }

    public Integer getPages(){
        return number_of_pages;
    }
}