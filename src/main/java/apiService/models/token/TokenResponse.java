package apiService.models.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {

    private String token;
    private String expires;
    private String status;
    private String result;
}
