package bg.jug.guestbook.comment;

import bg.jug.guestbook.entities.Comment;
import bg.jug.guestbook.entities.User;
import bg.jug.guestbook.cache.JCache;
import bg.jug.guestbook.users.LoggedIn;

import javax.inject.Inject;
import javax.mvc.binding.BindingResult;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.net.URI;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.mvc.Controller;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * @author Ivan St. Ivanov
 */
@Controller
@Path("/newcomment")
@RequestScoped
public class NewCommentController {

    @Inject
    private BindingResult br;

    @Inject
    private MessagesBean messagesBean;

    @Inject
    @JCache
    private CommentsManager commentsManager;

    @Inject
    @LoggedIn
    private User currentUser;

    @Inject
    private EntityManager em;

    @GET
    public String showNewCommentForm() {
        return "newComment.jsp";
    }

    @POST
    @ValidateOnExecution(type = ExecutableType.NONE)
    @Transactional
    public Response submitComment(@Valid @BeanParam CommentModel comment) throws IOException {
        if (br.isFailed()) {
            String errorMessage = br.getAllMessages().stream()
                    .collect(Collectors.joining("<br>"));
            messagesBean.setMessage(errorMessage);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("newComment.jsp").build();
        }
        User dbUser = em.find(User.class, currentUser.getId());
        if(dbUser == null) {
            dbUser = User.build(currentUser.getUserName(), currentUser.getPassword(), currentUser.getFirstName(), currentUser.getLastName()).id(currentUser.getId()).build();
            em.persist(dbUser);
        }
        Comment newComment = Comment.builder().title(comment.getTitle()).content(comment.getContent()).byUser(dbUser).build();
        commentsManager.submitComment(newComment);
        return Response.seeOther(URI.create("comment")).build();
    }
}
