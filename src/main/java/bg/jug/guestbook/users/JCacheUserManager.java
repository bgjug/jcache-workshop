package bg.jug.guestbook.users;

import bg.jug.guestbook.cache.JCache;
import bg.jug.guestbook.entities.User;

import javax.cache.Cache;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 * @author Ivan St. Ivanov
 */
@JCache
@RequestScoped
public class JCacheUserManager implements UserManager {

    @Inject
    @JCache
    private Cache<String, User> userCache;

    @Override
    public User getUser(String userName, String password) {
        User user = userCache.get(userName);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void addUser(User newUser) {
        userCache.putIfAbsent(newUser.getUserName(), newUser);
    }

    @Override
    public User findUserByName(String userName) {
        return userCache.get(userName);
    }
}
