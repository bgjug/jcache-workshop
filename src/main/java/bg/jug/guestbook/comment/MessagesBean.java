package bg.jug.guestbook.comment;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import lombok.Data;

/**
 * @author Ivan St. Ivanov
 */
@Model
@Data
@Dependent
public class MessagesBean {
    private String message;
}
