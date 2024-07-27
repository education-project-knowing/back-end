package education.knowing.repository;

import education.knowing.entity.QuestionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionInfoRepository extends JpaRepository<QuestionInfo, Long> {
    @Query("select qi from QuestionInfo qi where qi.user.username=:username and qi.question.qNo = :qNo")
    Optional<QuestionInfo> findByUserUsernameAndQuestionQNo(@Param("username") String username,@Param("qNo") Long qNo);
}
