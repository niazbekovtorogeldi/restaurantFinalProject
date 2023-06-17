package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import org.springframework.data.repository.query.Param;
import peaksoft.enums.RestType;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(
            generator = "restaurant_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "restaurant_gen",
            sequenceName = "restaurant_seq",
            allocationSize = 1
    )
    private Long id;
    private String name;
    private String location;
    @Enumerated(EnumType.STRING)
    private RestType restType;
    private int countOfEmployees;
    private int service;

    @OneToMany(mappedBy = "restaurant",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,})
    private List<User> users;

    @OneToMany(mappedBy = "restaurant",
            cascade = CascadeType.ALL)
    private List<MenuItem> menuItems;


}
