package bg.jug.guestbook.comment;

import bg.jug.guestbook.entities.Comment;
import bg.jug.guestbook.cache.JPA;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
@ApplicationScoped
@JPA
public class JpaCommentsManager implements CommentsManager {

    @Inject
    private EntityManager em;

    @Override
    public List<Comment> getAllComments() {
        return em.createNamedQuery("getAllComments", Comment.class).getResultList();
    }

    @Transactional
    @Override
    public Comment submitComment(Comment newComment) {
        em.persist(newComment);
        return newComment;
    }

    @Transactional
    @Override
    public void deleteCommentWithId(Long commentId) {
        final Comment comment = em.find(Comment.class, commentId);
        if (comment != null) {
            em.remove(comment);
        }
    }
}
