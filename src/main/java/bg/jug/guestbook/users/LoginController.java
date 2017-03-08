package bg.jug.guestbook.users;

import bg.jug.guestbook.entities.User;
import bg.jug.guestbook.cache.JCache;
import javax.enterprise.context.RequestScoped;

import javax.inject.Inject;
import javax.mvc.annotation.Controller;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Ivan St. Ivanov
 */
@Controller
@Path("/login")
@RequestScoped
public class LoginController {

    @Inject
    private UserContext userContext;

    @Inject
    @JCache
    private UserManager userManager;

    @GET
    public String showLoginForm() {
        return "login.jsp";
    }

    @POST
    public String login(@FormParam("userName") String userName,
                        @FormParam("password") String password) {
        User foundUser = userManager.getUser(userName, password);
        if (foundUser == null) {
            return "redirect:/login";
        } else {
            userContext.setCurrentUser(foundUser);
            return "redirect:/comment";
        }
    }
}
