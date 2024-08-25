package apiService.models.booksToUser;

import apiService.models.books.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddBooksToUserResponse {

    private List<Book> books;
}
