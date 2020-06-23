package bg.jug.guestbook.comment;

import javax.enterprise.inject.Model;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import lombok.Data;

/**
 * @author Ivan St. Ivanov
 */
@Model
@Data
public class CommentModel {

    @Size(max = 100)
    @FormParam("title")
    private String title;

    @FormParam("content")
    private String content;
}
