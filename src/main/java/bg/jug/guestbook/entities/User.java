package bg.jug.guestbook.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NamedQueries({
    @NamedQuery(name = "findUserByNameAndPassword", query = "SELECT u FROM User u WHERE u.userName = :userName AND u.password = :password"),
    @NamedQuery(name = "findUserByName", query = "SELECT u FROM User u WHERE u.userName = :userName")
})
@Table(name = "Users")
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class User implements Serializable {

    public static UserBuilder build(String userName, String password, String firstName, String lastName) {
        return builder().userName(userName).password(password).firstName(firstName).lastName(lastName)
                .comments(new ArrayList<>());
    }

    private static final long serialVersionUID = -1774213830687034509L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private int version;

    @NotNull
    private String userName;

    @NotNull
    private String password;

    private String firstName;

    private String lastName;

    private boolean adminUser;

    @OneToMany(mappedBy = "byUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
