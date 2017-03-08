package bg.jug.guestbook.comment;

import bg.jug.guestbook.entities.Comment;

import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;

import java.util.logging.Level;
import lombok.extern.java.Log;

@Log
public class EntryCreatedLogListener implements
        CacheEntryCreatedListener<Long, Comment> {

    int hits = 0;

    @Override
    public void onCreated(
            Iterable<CacheEntryEvent<? extends Long, ? extends Comment>> events)
            throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Long, ? extends Comment> event : events) {
            hits++;
            log.log(Level.INFO, "New entry value added in comments cache. Current additions: {0}", hits);
        }
    }
}
