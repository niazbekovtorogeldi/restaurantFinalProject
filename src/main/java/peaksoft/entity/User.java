package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksoft.enums.Role;
import peaksoft.validation.EmailValid;
import peaksoft.validation.PasswordValid;
import peaksoft.validation.PhoneNumberValid;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(
            generator = "user_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "user_gen",
            sequenceName = "users_seq",
            allocationSize = 1
    )
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private ZonedDateTime createdDate;
    @EmailValid
    @Column(unique = true)
    private String email;
    @PasswordValid
    private String password;
    @PhoneNumberValid
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private int experience;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private Restaurant restaurant;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.ALL})
    private List<Cheque> cheques;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(Long id, String firstName, String lastName, ZonedDateTime createdDate, String email, String password, String phoneNumber, Role role, int experience, Restaurant restaurant, List<Cheque> cheques) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdDate = createdDate;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.experience = experience;
        this.restaurant = restaurant;
        this.cheques = cheques;
    }
}
