package bg.jug.guestbook.users;

import bg.jug.guestbook.entities.User;
import javax.cache.configuration.Factory;
import javax.cache.integration.CacheLoader;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Ivan St. Ivanov
 */
@ApplicationScoped
public class UserCacheLoaderFactory implements Factory<CacheLoader<String, User>> {

    private static final long serialVersionUID = 1L;

    @Inject
    private UserCacheLoader userCacheLoader;

    @Override
    public CacheLoader<String, User> create() {
        return userCacheLoader;
    }
}
