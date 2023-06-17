package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Table(name = "menuItems")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItem {
    @Id
    @GeneratedValue(
            generator = "menuItem_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "menuItem_gen",
            sequenceName = "menuItem_seq",
            allocationSize = 1
    )
    private Long id;
    private String name;
    @Lob
    private List<String> image;
    private int price;
    private String description;
    private Boolean isVegetarian;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private Restaurant restaurant;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Cheque> cheques;

    @OneToOne(mappedBy = "menuItem",
            cascade = CascadeType.ALL)
    private StopList stopList;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.DETACH,
            CascadeType.REFRESH})
    private Subcategory subcategory;
}
