package bg.jug.guestbook.comment;

import bg.jug.guestbook.entities.Comment;

import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
interface CommentsManager {

    List<Comment> getAllComments();

    Comment submitComment(Comment newComment);
    
    void deleteCommentWithId(Long commentId);

	default Object getStatistics() {
		return new Object();
	}
}
