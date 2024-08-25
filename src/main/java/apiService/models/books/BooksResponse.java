package apiService.models.books;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BooksResponse {

    private List<Book> books;
}
