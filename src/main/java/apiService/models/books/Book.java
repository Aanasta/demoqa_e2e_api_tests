package apiService.models.books;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

    private String isbn;
    private String title;
    private String subTitle;
    private String author;
    private String publish_date;
    private String publisher;
    private int pages;
    private String description;
    private String website;
}
