package bg.jug.guestbook.users;

import bg.jug.guestbook.entities.User;
import javax.cache.configuration.Factory;
import javax.cache.integration.CacheWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * @author Ivan St. Ivanov
 */
@Dependent
public class UserCacheWriterFactory implements Factory<CacheWriter<String, User>> {

    private static final long serialVersionUID = 1L;

    @Inject
    private UserCacheWriter userCacheWriter;

    @Override
    public CacheWriter<String, User> create() {
        return userCacheWriter;
    }
}
