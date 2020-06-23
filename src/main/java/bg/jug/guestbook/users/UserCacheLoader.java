package bg.jug.guestbook.users;

import bg.jug.guestbook.cache.JPA;
import bg.jug.guestbook.entities.User;

import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.inject.Inject;

/**
 * @author Ivan St. Ivanov
 */
@ApplicationScoped
public class UserCacheLoader implements CacheLoader<String, User> {

    private @Inject @JPA UserManager userManager;

    @Override
    public User load(String key) throws CacheLoaderException {
        return userManager.findUserByName(key);
    }

    @Override
    public Map<String, User> loadAll(Iterable<? extends String> keys) throws CacheLoaderException {
        return StreamSupport.stream(keys.spliterator(), false)
                            .collect(Collectors.toMap(Function.identity(),
                                     this::load));
    }
}
