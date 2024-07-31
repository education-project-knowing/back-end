package education.knowing.repository;

import education.knowing.dto.question.response.QuestionResponseDto;
import education.knowing.entity.QuestionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionInfoRepository extends JpaRepository<QuestionInfo, Long> {
    @Query("select qi from QuestionInfo qi where qi.user.username=:username and qi.question.qNo = :qNo")
    Optional<QuestionInfo> findByUserUsernameAndQuestionQNo(@Param("username") String username,@Param("qNo") Long qNo);

    @Query("select new education.knowing.dto.question.response.QuestionResponseDto(q.qNo, q.question, q.answer) " +
            "from QuestionInfo qi left join qi.question q " +
            "where qi.user.username = :username and qi.isRecognized = false")
    List<QuestionResponseDto> findAllQuizByUser(@Param("username") String username);

}
