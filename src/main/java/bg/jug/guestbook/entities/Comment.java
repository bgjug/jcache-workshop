package bg.jug.guestbook.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllComments", query = "SELECT c FROM Comment c")
})
@Data @EqualsAndHashCode(exclude = "byUser") @ToString(exclude = "byUser") @AllArgsConstructor @NoArgsConstructor
@Builder
public class Comment implements Serializable {

    private static final long serialVersionUID = -8061042352342915142L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private int version;

    @Size(max = 100)
    private String title;

    @Column(length = 3000)
    @Lob
    private String content;

    @ManyToOne
    private User byUser;
}
