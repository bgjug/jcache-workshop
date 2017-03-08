package bg.jug.guestbook.comment;

import java.util.List;

import javax.cache.Cache;
import javax.cache.configuration.CompleteConfiguration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import bg.jug.guestbook.entities.Comment;
import bg.jug.guestbook.cache.JCache;
import bg.jug.guestbook.cache.JPA;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import javax.cache.Cache.Entry;

/**
 * @author Ivan St. Ivanov
 */
@ApplicationScoped
@JCache
public class JCacheCommentsManager implements CommentsManager {

    @Inject
    @JPA
    private CommentsManager passThroughCommentsManager;

    @Inject
    private CommentAuthorEntryProcessor processor;

    @Inject
    @JCache
    private Cache<Long, Comment> cache;

    @Override
    public List<Comment> getAllComments() {
        List<Comment> foundComments = Lists.newArrayList(Iterators.transform(cache.iterator(), Entry::getValue));
        if(!foundComments.isEmpty()) {
            return foundComments;
        }
        List<Comment> dbComments = passThroughCommentsManager.getAllComments();

        dbComments.forEach(comment -> {
            cache.put(comment.getId(), comment);
            cache.invoke(comment.getId(), processor);
        });

        return dbComments;

    }

    @Override
    public Comment submitComment(Comment newComment) {
        Comment submittedComment = passThroughCommentsManager
                .submitComment(newComment);
        cache.put(submittedComment.getId(), submittedComment);
        cache.invoke(submittedComment.getId(), processor);
        return submittedComment;
    }

    @Override
    public void deleteCommentWithId(Long commentId) {
        passThroughCommentsManager.deleteCommentWithId(commentId);
        cache.remove(commentId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getStatistics() {
        return cache.getConfiguration(CompleteConfiguration.class);
    }
}
