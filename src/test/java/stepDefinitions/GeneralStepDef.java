package stepDefinitions;

import apiService.ApiConnection;
import apiService.models.GenericResponse;
import apiService.models.books.BooksResponse;
import apiService.models.token.TokenResponse;
import apiService.models.user.user.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import context.TestContext;
import entities.ApiUser;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.testng.Assert;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;

import static apiService.RetrofitClient.getRetrofitInstance;
import static helpers.JsonValidator.validateJsonAgainstSchema;

@RequiredArgsConstructor
public class GeneralStepDef {

    private final TestContext testContext;

    private UserRequest userRequest;
    private ApiConnection connection;

    @Then("I assert response code is {int}")
    public void iAssertResponseCodeIs(int expectedCode) {
        Assert.assertEquals(testContext.getGenericResponse().getResponse().code(), expectedCode, "Response code is not as expected");
    }

    @When("I execute POST request to generate auth token")
    public void iExecutePOSTRequestToGenerateAuthToken() throws IOException {
        ApiUser user = testContext.getApiUser();
        String username = user.getUsername();
        String password = user.getPassword();
        connection = getRetrofitInstance().create(ApiConnection.class);
        userRequest = new UserRequest(username, password);
        Response<TokenResponse> response = connection.generateToken(userRequest).execute();
        testContext.setGenericResponse(new GenericResponse<>(response));
    }

    @And("I save the auth token for my user")
    public void iSaveTheAuthTokenForMyUser() {
        TokenResponse response = (TokenResponse) testContext.getGenericResponse().getResponse().body();
        testContext.setAuthToken(response.getToken());
    }

    @And("I assert the auth token is not empty")
    public void iAssertTheAuthTokenIsNotEmpty() {
        TokenResponse response = (TokenResponse) testContext.getGenericResponse().getResponse().body();
        Assert.assertNotNull(response.getToken(), "Auth token wasn't generated");
    }

    @And("I assert response body is not empty")
    public void iAssertResponseBodyIsNotEmpty() {
        String body = testContext.getGenericResponse().getResponse().body().toString();
        Assert.assertNotNull(body, "Response body is not returned");
        Assert.assertFalse(body.isEmpty(), "Response body is empty");
    }

    @And("I assert response content type is {string}")
    public void iAssertResponseContentTypeIs(String expectedResponseType) {
        String actualType = testContext.getGenericResponse().getResponse().headers().get("Content-Type");
        Assert.assertEquals(actualType, expectedResponseType, "Content type is not as expected");
    }

    @And("I assert the response matches {string} schema")
    public void iAssertTheResponseMatchesSchema(String expectedSchema) throws IOException {
        ProcessingReport report = validateJsonAgainstSchema(testContext.getGenericResponse().getResponse().body(), expectedSchema);
        Assert.assertTrue(report.isSuccess(), "Returned JSON schema is not as expected");
    }
}
