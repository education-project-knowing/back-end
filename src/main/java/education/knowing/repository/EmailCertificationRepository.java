package education.knowing.repository;

import education.knowing.entity.EmailCertification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailCertificationRepository extends JpaRepository<EmailCertification, String> {
    boolean existsByEmailAndCertificationNumber(String email, String certificationNumber);
}
