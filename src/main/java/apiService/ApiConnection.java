package apiService;

import apiService.models.books.BooksResponse;
import apiService.models.booksToUser.AddBooksToUserRequest;
import apiService.models.booksToUser.AddBooksToUserResponse;
import apiService.models.token.TokenResponse;
import apiService.models.user.user.UserRequest;
import apiService.models.user.user.UserResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiConnection {

    @Headers("Content-Type: application/json")
    @POST("Account/v1/User")
    Call<UserResponse> createUser(@Body UserRequest userRequest);

    @Headers("Content-Type: application/json")
    @POST("Account/v1/GenerateToken")
    Call<TokenResponse> generateToken(@Body UserRequest userRequest);

    @Headers("Content-Type: application/json")
    @GET("BookStore/v1/Books")
    Call<BooksResponse> getAllBooks();

    @Headers("Content-Type: application/json")
    @GET("Account/v1/User/{uuid}")
    Call<UserResponse> getBooksByUser(@Header("Authorization") String token, @Path("uuid") String uuid);

    @POST("/BookStore/v1/Books")
    Call<AddBooksToUserResponse> addBooksToUser(@Header("Authorization") String token, @Body AddBooksToUserRequest addBooksRequest);
}
