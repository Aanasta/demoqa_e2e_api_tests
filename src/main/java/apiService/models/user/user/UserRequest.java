package apiService.models.user.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String userName;
    private String password;

    public UserRequest(String name, String password) {
        this.userName = name;
        this.password = password;
    }
}
