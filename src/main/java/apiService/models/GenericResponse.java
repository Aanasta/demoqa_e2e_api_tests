package apiService.models;

import retrofit2.Response;

public class GenericResponse<T>  {

    private Response<T> response;

    public GenericResponse(Response<T> response) {
        this.response = response;
    }

    public Response<T> getResponse() {
        return response;
    }
}
