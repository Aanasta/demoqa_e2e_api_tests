package apiService.models.user.user;

import apiService.models.user.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {

    private String userID;
    private String username;
    private List<Book> books;
}
