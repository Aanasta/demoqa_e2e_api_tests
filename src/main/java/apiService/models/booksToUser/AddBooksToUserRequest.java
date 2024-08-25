package apiService.models.booksToUser;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddBooksToUserRequest {

    private String userId;
    private List<Isbn> collectionOfIsbns;

    public AddBooksToUserRequest(String userId, List<Isbn> isbns) {
        this.userId = userId;
        this.collectionOfIsbns = isbns;
    }
}
