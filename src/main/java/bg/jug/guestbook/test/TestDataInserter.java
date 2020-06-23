package bg.jug.guestbook.test;

import bg.jug.guestbook.entities.Comment;
import bg.jug.guestbook.entities.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Ivan St. Ivanov
 */
@Singleton
@Startup
public class TestDataInserter {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void insertTestData() {
        if(Boolean.getBoolean("noinitdb")) {
            return;
        }
        em.createQuery("DELETE FROM Comment").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();

        User mitya = User.build("mitya", "mitya", "Dmitriy", "Alexandrov").build();
        User misho = User.build("misho", "misho", "Mihail", "Stoynov").build();
        User nayden = User.build("nayden", "nayden", "Nayden", "Gochev").adminUser(true).build();
        User martin = User.build("martin", "martin", "Martin", "Toshev").build();

        Comment myfear = Comment.builder().title("@myfear")
                .content("Great! Great! GREAT!! @myfear will speak at @jPrimeConf 2016!!!")
                .byUser(mitya).build();
        Comment photo = Comment.builder().title("Photo")
                .content("\"Never a straight face :(\" (my mom when i was a kid) ")
                .byUser(misho).build();
        Comment vars = Comment.builder().title("var in Java")
                .content("I dont think you will be able to write var someList = new ArrayList<>() maybe = new ArrayList<ClassName>() still.. I am guessing Java 11 :)")
                .byUser(nayden).build();
        Comment freePasses = Comment.builder().title("Free passes")
                .content("A JUG lead has the chance to attend jPrime 2016 with a free pass")
                .byUser(martin).build();
        Comment top10 = Comment.builder().title("Top 10 mistakes")
                .content("Java Beginner level but good to check : Top 10 Most Common Mistakes That Java Developers Make")
                .byUser(nayden).build();

        mitya.getComments().add(myfear);
        misho.getComments().add(photo);
        nayden.getComments().add(vars);
        martin.getComments().add(freePasses);
        nayden.getComments().add(top10);

        em.persist(mitya);
        em.persist(misho);
        em.persist(nayden);
        em.persist(martin);
    }
}
