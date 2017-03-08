package bg.jug.guestbook.users;

import bg.jug.guestbook.cache.JCache;
import bg.jug.guestbook.entities.User;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * @author Ivan St. Ivanov
 */
@ApplicationScoped
public class UserCacheProducer {

    private static final String USERS_CACHE_NAME = "users";

    @Inject
    private CacheManager cacheManager;

    @Inject
    private UserCacheLoaderFactory loaderFactory;

    @Inject
    private UserCacheWriterFactory writerFactory;

    @Produces
    @RequestScoped
    @JCache
    public Cache<String, User> getUsersCache() {
        Cache<String, User> cache = cacheManager.getCache(USERS_CACHE_NAME, String.class, User.class);
        if(cache == null) {
            MutableConfiguration<String, User> cacheConfig = new MutableConfiguration<>();
            cacheConfig.setTypes(String.class, User.class)
                    .setReadThrough(true)
                    .setCacheLoaderFactory(loaderFactory)
                    .setWriteThrough(true)
                    .setCacheWriterFactory(writerFactory);
            cache = cacheManager.createCache(USERS_CACHE_NAME, cacheConfig);
        }
        return cache;
    }

    @PreDestroy
    void destroy() {
        cacheManager.destroyCache(USERS_CACHE_NAME);
    }
}
