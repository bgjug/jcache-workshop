package bg.jug.guestbook.users;

import bg.jug.guestbook.cache.JPA;
import bg.jug.guestbook.entities.User;
import bg.jug.guestbook.users.UserManager;

import javax.cache.Cache;
import javax.cache.integration.CacheWriter;
import javax.cache.integration.CacheWriterException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Ivan St. Ivanov
 */
@ApplicationScoped
public class UserCacheWriter implements CacheWriter<String, User> {

    @Inject
    @JPA
    private UserManager userManager;


    @Override
    public void write(Cache.Entry<? extends String, ? extends User> entry) throws CacheWriterException {
        userManager.addUser(entry.getValue());
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends String, ? extends User>> entries) throws CacheWriterException {
        entries.forEach(this::write);
    }

    @Override
    public void delete(Object key) throws CacheWriterException {
        // Do nothing. We don't want to remove a user when she is removed from cache.
    }

    @Override
    public void deleteAll(Collection<?> keys) throws CacheWriterException {
        // Do nothing. We don't want to remove a user when she is removed from cache.
    }
}
