package helpers;

import apiService.models.booksToUser.Isbn;

import java.util.List;
import java.util.stream.Collectors;

public class IsbnConverter {

    public static List<Isbn> convertStringToIsbnList(List<String> strings) {
        return strings.stream()
                .map(Isbn::new)
                .collect(Collectors.toList());
    }
}
