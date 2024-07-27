package education.knowing.repository;

import education.knowing.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {
    @Query("select s from Study s where s.user.username=:username and s.folder.fNo=:fNo")
    Optional<Study> findStudyByUserUsernameAndFolderFNo(@Param("username") String username,@Param("fNo") Long fNo);
}
