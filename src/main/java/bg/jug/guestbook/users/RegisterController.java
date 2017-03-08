package bg.jug.guestbook.users;

import bg.jug.guestbook.entities.User;
import bg.jug.guestbook.cache.JCache;
import javax.enterprise.context.RequestScoped;

import javax.inject.Inject;
import javax.mvc.annotation.Controller;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Controller
@Path("/register")
@RequestScoped
public class RegisterController {

    @GET
    public String showRegistrationForm() {
        return "register.jsp";
    }

    @Inject
    @JCache
    private UserManager userManager;

    @Inject
    private UserContext userContext;

    @POST
    public String registerUser(@Valid @BeanParam UserModel user) {
        User newUser = User.build(user.getUserName(), user.getPassword(), user.getFirstName(), user.getLastName()).build();
        userManager.addUser(newUser);
        userContext.setCurrentUser(newUser);
        return "redirect:/comment";
    }
}
