package bg.jug.guestbook.comment;

import bg.jug.guestbook.cache.JCache;
import bg.jug.guestbook.entities.Comment;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

/**
 * @author Ivan St. Ivanov
 */
@ApplicationScoped
public class CommentsCacheProducer {

    private static final String COMMENTS_CACHE_NAME = "comments";

    @Inject
    private CacheManager cacheManager;

    @Produces
    @RequestScoped
    @JCache
    public Cache<Long, Comment> getCommentsCache() {

        Cache<Long, Comment> cache = cacheManager.getCache(COMMENTS_CACHE_NAME,
                Long.class, Comment.class);
        if (cache == null) {
            MutableConfiguration<Long, Comment> cacheConfig = new MutableConfiguration<>();
            cacheConfig.setTypes(Long.class, Comment.class);
            cacheConfig.setExpiryPolicyFactory(FactoryBuilder
                    .factoryOf(new AccessedExpiryPolicy(new Duration(
                            TimeUnit.MINUTES, 3))));
            cacheConfig
                    .addCacheEntryListenerConfiguration(new MutableCacheEntryListenerConfiguration<>(
                            FactoryBuilder.factoryOf(EntryCreatedLogListener.class),
                            null, true, true));
            cache = cacheManager.createCache(COMMENTS_CACHE_NAME, cacheConfig);
        }
        return cache;
    }
}
