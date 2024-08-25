package apiService.models.booksToUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Isbn {

    private String isbn;

    public Isbn(String isbn) {
        this.isbn = isbn;
    }
}
