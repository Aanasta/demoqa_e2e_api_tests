package context;

import apiService.models.GenericResponse;
import entities.ApiUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestContext {

    private String authToken;
    private ApiUser apiUser;
    private List<String> booksAddedToUser;
    private GenericResponse<?> genericResponse;
}
