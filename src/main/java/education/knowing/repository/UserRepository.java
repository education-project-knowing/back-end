package education.knowing.repository;

import education.knowing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByUsernameAndEmail(String username, String email);

    Optional<User> findByUsername(String username);

    User findByEmail(String email);
}
