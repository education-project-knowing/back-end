package education.knowing.repository;

import education.knowing.dto.response.QuestionResponseDto;
import education.knowing.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select new education.knowing.dto.response.QuestionResponseDto(q.qNo, q.question, q.answer) " +
            "from Question q join q.folderQnaList fq " +
            "where fq.folder.fNo = :fNo and q.question like concat('%', :keyword, '%')")
    Page<QuestionResponseDto> findAllByFolder(@Param("fNo") Long fNo, @Param("keyword") String keyword, Pageable pageable);

    @Query("select new education.knowing.dto.response.QuestionResponseDto(q.qNo, q.question, q.answer, qu.importance, qu.isRecognized) " +
            "from Question q join q.folderQnaList fq left join fetch q.quizList qu " +
            "where fq.folder.fNo = :fNo and q.question like concat('%', :keyword, '%') " +
            "and qu.user.username = :username and qu.importance = :importance and qu.isRecognized = :isRecognized")
    Page<QuestionResponseDto> findAllByFolderWithUser(@Param("fNo")Long fNo, @Param("username") String username,@Param("keyword") String keyword,
                                                      @Param("isRecognized") boolean isRecognized, @Param("importance")int importance, Pageable pageable);

    @Query("select new education.knowing.dto.response.QuestionResponseDto(q.qNo, q.question, q.answer, qu.importance, qu.isRecognized) " +
            "from Question q join q.quizList qu " +
            "where q.qNo = :qNo and qu.user.username = :username")
    Optional<QuestionResponseDto> findByIdWithUser(@Param("qNo")Long qNo, @Param("username")String username);
}
