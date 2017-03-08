package bg.jug.guestbook.admin;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

/**
 * @author Ivan St. Ivanov
 */
@RequestScoped
public class EntityManagerProducer {
    @PersistenceContext
    @Getter(onMethod = @__(@Produces))
    private EntityManager em;
}
