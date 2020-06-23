package bg.jug.guestbook.users;

import bg.jug.guestbook.entities.User;

import javax.transaction.Transactional;

/**
 * @author Ivan St. Ivanov
 */
public interface UserManager {

    User getUser(String userName, String password);

    @Transactional
    void addUser(User newUser);

    User findUserByName(String userName);
}
