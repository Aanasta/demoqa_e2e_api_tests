package stepDefinitions;

import apiService.ApiConnection;
import apiService.models.GenericResponse;
import apiService.models.books.BooksResponse;
import apiService.models.booksToUser.AddBooksToUserRequest;
import apiService.models.booksToUser.AddBooksToUserResponse;
import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.testng.Assert;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static apiService.RetrofitClient.getRetrofitInstance;
import static helpers.IsbnConverter.convertStringToIsbnList;

@RequiredArgsConstructor
public class BooksStepDef {

    private final TestContext testContext;

    private AddBooksToUserRequest addBooksRequest;
    private ApiConnection connection;

    @When("I execute GET request to get all books in the app")
    public void iExecuteGETRequestToGetAllBooksInTheApp() throws IOException {
        connection = getRetrofitInstance().create(ApiConnection.class);
        Response<BooksResponse> response = connection.getAllBooks().execute();
        testContext.setGenericResponse(new GenericResponse<>(response));
    }

    @And("I assert all books contain books with publisher {string}")
    public void iAssertAllBooksContainBooksWithPublisher(String publisher) {
        BooksResponse response = (BooksResponse) testContext.getGenericResponse().getResponse().body();
        int resultsCount = ( int ) response.getBooks().stream()
                .filter(book -> book.getPublisher().equals(publisher))
                .count();
        Assert.assertTrue(resultsCount > 0, "All books list doesn't contain books published by " + publisher);
    }

    @And("I execute POST request to add to user books with ids")
    public void iExecutePOSTRequestToAddToUserBooksWithIds(DataTable dataTable) throws IOException {
        List<String> booksToAdd = dataTable.asList();
        String userId = testContext.getApiUser().getUid();
        String token = testContext.getAuthToken();

        addBooksRequest = new AddBooksToUserRequest(userId, convertStringToIsbnList(booksToAdd));
        connection = getRetrofitInstance().create(ApiConnection.class);
        Response<AddBooksToUserResponse> response = connection.addBooksToUser("Bearer " + token, addBooksRequest).execute();
        testContext.setGenericResponse(new GenericResponse<>(response));

        testContext.setBooksAddedToUser(booksToAdd);
    }

    @And("I assert all added books are present in the response")
    public void iAssertAllAddedBooksArePresentInTheResponse() {
        List<String> expectedBooks = testContext.getBooksAddedToUser();
        AddBooksToUserResponse response = (AddBooksToUserResponse) testContext.getGenericResponse().getResponse().body();
        List<String> actualBooks = response.getBooks().stream()
                .map(book -> book.getIsbn())
                .collect(Collectors.toList());
        expectedBooks.forEach(expectedBook -> Assert.assertTrue(actualBooks.contains(expectedBook),
                expectedBook + " book is not present in the response"));
    }
}
