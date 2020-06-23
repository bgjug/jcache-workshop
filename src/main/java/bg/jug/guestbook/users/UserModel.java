package bg.jug.guestbook.users;

import javax.enterprise.inject.Model;
import javax.validation.constraints.AssertTrue;
import javax.ws.rs.FormParam;
import lombok.Data;

@Model
@Data
public class UserModel {

    @FormParam("userName")
    private String userName;

    @FormParam("password")
    private String password;

    @FormParam("reenterPassword")
    private String reenterPassword;

    @FormParam("firstName")
    private String firstName;

    @FormParam("lastName")
    private String lastName;

    @AssertTrue(message = "Passwords don't match")
    private boolean isValid() {
        return this.password.equals(this.reenterPassword);
    }
}
