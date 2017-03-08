package bg.jug.guestbook.comment;

import bg.jug.guestbook.entities.Comment;
import bg.jug.guestbook.entities.User;
import bg.jug.guestbook.cache.JCache;
import bg.jug.guestbook.users.LoggedIn;

import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.annotation.Controller;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;

/**
 * @author Ivan St. Ivanov
 */
@Controller
@Path("/comment")
@RequestScoped
public class CommentsController {

    @Inject
    @JCache
    private CommentsManager commentsManager;

    @Inject
    private MessagesBean messagesBean;

    @Inject
    @LoggedIn
    private User currentUser;

    @Inject
    private Models models;

    @GET
    public String showAllComments() throws ClassNotFoundException, IOException {
        return prepareModelAndView(commentsManager.getAllComments());
    }

    @GET
    @Path("/delete")
    public Response deleteComment(@QueryParam("commentId") Long commentId) {
        if (currentUser.isAdminUser()) {
            commentsManager.deleteCommentWithId(commentId);
        } else {
            messagesBean.setMessage("Only admin users are allowed to delete comments");
        }
        return Response.seeOther(URI.create("comment")).build();
    }

    @POST
    @Path("/search")
    public String filterComments(@FormParam("searchTerm") String searchTerm) throws ClassNotFoundException, IOException {
        return prepareModelAndView(commentsManager.getAllComments()
                .stream()
                .filter(comment -> commentSatisfiesTerm(comment, searchTerm))
                .collect(Collectors.toList()));
    }

    private String prepareModelAndView(List<Comment> comments) {
        models.put("comments", comments);
        models.put("user", currentUser);
        models.put("statistics", commentsManager.getStatistics());
        
        return "comments.jsp";
    }

    private boolean commentSatisfiesTerm(Comment comment, String searchTerm) {
        return comment.getTitle().contains(searchTerm) ||
                comment.getContent().contains(searchTerm);
    }
}
