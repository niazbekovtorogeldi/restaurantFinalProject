package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "subcategories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subcategory {
    @Id
    @GeneratedValue(
            generator = "subcategory_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "subcategory_gen",
            sequenceName = "subcategory_seq",
            allocationSize = 1
    )
    private Long id;
    private String name;
    @OneToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH}, mappedBy = "subcategory")
    private List<MenuItem> menuItems;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private Category category;

}
