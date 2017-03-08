package bg.jug.guestbook.users;

import bg.jug.guestbook.cache.JPA;
import bg.jug.guestbook.entities.User;
import javax.enterprise.context.RequestScoped;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 * @author Ivan St. Ivanov`
 */
@JPA
@RequestScoped
public class JPAUserManager implements UserManager {

    @Inject
    private EntityManager em;

    @Override
    public User getUser(String userName, String password) {
        TypedQuery<User> query = em.createNamedQuery("findUserByNameAndPassword", User.class);
        query.setParameter("userName", userName);
        query.setParameter("password", password);
        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Transactional
    @Override
    public void addUser(User newUser) {
        em.persist(newUser);
    }

    @Override
    public User findUserByName(String userName) {
        TypedQuery<User> query = em.createNamedQuery("findUserByName", User.class);
        query.setParameter("userName", userName);
        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
