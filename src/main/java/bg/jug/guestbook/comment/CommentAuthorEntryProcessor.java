package bg.jug.guestbook.comment;

import bg.jug.guestbook.entities.Comment;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommentAuthorEntryProcessor implements
        EntryProcessor<Long, Comment, Comment> {

    @Override
    public Comment process(MutableEntry<Long, Comment> entry,
            Object... arguments) throws EntryProcessorException {

        Comment comment = entry.getValue();
        comment.setContent(comment.getContent() + " ["
                + comment.getByUser().getUserName() + "]");
        entry.setValue(comment);
        return comment;
    }
}
