package bg.jug.guestbook.admin;

import javax.enterprise.context.RequestScoped;
import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Ivan St. Ivanov
 */
@Controller
@RequestScoped
@Path("/")
public class RootController {

    @GET
    public String homePage() {
        return "redirect:/comment";
    }
}
