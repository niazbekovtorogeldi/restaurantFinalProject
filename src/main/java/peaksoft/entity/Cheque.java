package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "cheques")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cheque {
    @Id
    @GeneratedValue(
            generator = "cheque_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "cheque_gen",
            sequenceName = "cheque_seq",
            allocationSize = 1
    )

    private Long id;
    private Double priceAverage;
    private ZonedDateTime zonedDateTime;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private User user;
    @ManyToMany(mappedBy = "cheques",
            cascade = {CascadeType.DETACH,
                       CascadeType.MERGE,
                       CascadeType.REFRESH})
    private List<MenuItem> menuItems;

}
