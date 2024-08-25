package stepDefinitions;

import apiService.ApiConnection;
import apiService.models.GenericResponse;
import apiService.models.books.BooksResponse;
import apiService.models.user.Book;
import apiService.models.user.user.UserRequest;
import apiService.models.user.user.UserResponse;
import context.TestContext;
import entities.ApiUser;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.testng.Assert;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static apiService.RetrofitClient.getRetrofitInstance;
import static helpers.UserDataHelper.generateRandomPassword;
import static helpers.UserDataHelper.generateRandomUsername;

@RequiredArgsConstructor
public class UserStepDef {

    private final TestContext testContext;

    private UserRequest userRequest;
    private ApiConnection connection;

    @Given("I am a new Book Store user with a username and a password")
    public void iAmANewBookStoreUserWithAUsernameAndAPassword() {
        ApiUser newUser = ApiUser.builder()
                .username(generateRandomUsername())
                .password(generateRandomPassword())
                .build();
        testContext.setApiUser(newUser);
    }

    @When("I execute POST request to create a new user")
    public void iExecutePOSTRequestToCreateANewUser() throws IOException {
        ApiUser user = testContext.getApiUser();
        String username = user.getUsername();
        String password = user.getPassword();
        connection = getRetrofitInstance().create(ApiConnection.class);
        userRequest = new UserRequest(username, password);
        Response<UserResponse> response = connection.createUser(userRequest).execute();
        testContext.setGenericResponse(new GenericResponse<>(response));
    }

    @And("I save userID for my user")
    public void iSaveUserIDForMyUser() {
        UserResponse response = (UserResponse) testContext.getGenericResponse().getResponse().body();
        String id = response.getUserID();
        testContext.getApiUser().setUid(id);
    }

    @And("I assert username is as set at creation")
    public void iAssertUsernameIsAsSetAtCreation() {
        String expectedName = testContext.getApiUser().getUsername();
        UserResponse response = (UserResponse) testContext.getGenericResponse().getResponse().body();
        String actualName = response.getUsername();
        Assert.assertEquals(actualName, expectedName, "Username is not as expected");
    }

    @And("books count for the user is {int}")
    public void booksCountForTheUserIs(int expectedCount) {
        UserResponse response = (UserResponse) testContext.getGenericResponse().getResponse().body();
        int actualCount = ( int ) response.getBooks().stream().count();
        Assert.assertEquals(actualCount, expectedCount, "Books count for the user is not as expected");
    }

    @And("I assert books list is not empty")
    public void iAssertBooksListIsNotEmpty() {
        BooksResponse response = (BooksResponse) testContext.getGenericResponse().getResponse().body();
        Assert.assertFalse(response.getBooks().isEmpty(), "All books list is empty in the response");
    }

    @When("I execute GET request to get all books to user")
    public void iExecuteGETRequestToGetAllBooksToUser() throws IOException {
        String userId = testContext.getApiUser().getUid();
        String token = testContext.getAuthToken();
        connection = getRetrofitInstance().create(ApiConnection.class);
        Response<UserResponse> response = connection.getBooksByUser(token, userId).execute();
        testContext.setGenericResponse(new GenericResponse<>(response));
    }

    @Then("I assert the books are present in the user's books")
    public void iAssertTheBooksArePresentInTheUserSBooks() {
        List<String> expectedBooks = testContext.getBooksAddedToUser();
        UserResponse response = (UserResponse) testContext.getGenericResponse().getResponse().body();
        List<String> actualBooks = response.getBooks().stream()
                .map(Book::getIsbn)
                .collect(Collectors.toList());
        expectedBooks.forEach(expectedBook -> Assert.assertTrue(actualBooks.contains(expectedBook)));
    }
}
