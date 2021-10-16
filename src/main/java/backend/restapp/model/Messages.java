package backend.restapp.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(of = {"id","message"})
@RequiredArgsConstructor
@Entity
@Table(name = "Messages")
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    public Messages(long id, String message, LocalDateTime creationDate, Person person) {
        this.id = id;
        this.message = message;
        this.creationDate = creationDate;
        this.person = person;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Messages messages = (Messages) o;

        return id == messages.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
