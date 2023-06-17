package peaksoft.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select new peaksoft.dto.response." +
            "UserResponse(u.id,u.firstName,u.lastName,u.createdDate," +
            "u.email,u.password,u.phoneNumber,u.role,u.experience)" +
            "from User u join u.restaurant ur where ur.id=:id")
    List<UserResponse> getAllUser(Long id);

    Optional<UserResponse>getUserById(Long id);




}
