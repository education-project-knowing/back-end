package education.knowing.repository;

import education.knowing.constant.Importance;
import education.knowing.dto.question.response.QuestionResponseDto;
import education.knowing.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    //전체 질문
    @Query("select new education.knowing.dto.question.response.QuestionResponseDto(q.qNo, q.question, q.answer) " +
            "from Question q " +
            "where q.question like concat('%', :keyword, '%')")
    Page<QuestionResponseDto> findAll(@Param("keyword") String keyword, Pageable pageable);
    @Query("select new education.knowing.dto.question.response.QuestionResponseDto(q.qNo, q.question, q.answer, qi.importance, qi.isRecognized) " +
            "from Question q " +
            "left join q.questionInfoList qi " +
            "left join qi.user u " +
            "where q.question like concat('%', :keyword, '%') " +
            "and (u is null or u.username = :username) " +
            "and (:importance is null or qi.importance in :importance)" +
            " and (:recognized is null or qi.isRecognized = :recognized)")
    Page<QuestionResponseDto> findAllWithUser(@Param("username") String username, @Param("keyword") String keyword,
                                              @Param("recognized") Boolean recognized, @Param("importance")List<Importance> importance, Pageable pageable);
    //폴더 내 질문
    @Query("select new education.knowing.dto.question.response.QuestionResponseDto(q.qNo, q.question, q.answer) " +
            "from Question q " +
            "left join q.folderQnaList fq " +
            "where fq.folder.fNo = :fNo and q.question like concat('%', :keyword, '%')")
    Page<QuestionResponseDto> findAllByFolder(@Param("fNo") Long fNo, @Param("keyword") String keyword, Pageable pageable);

    @Query("select new education.knowing.dto.question.response.QuestionResponseDto(q.qNo, q.question, q.answer, qi.importance, qi.isRecognized) " +
            "from Question q " +
            "left join q.folderQnaList fq " +
            "left join q.questionInfoList qi " +
            "left join qi.user u " +
            "where fq.folder.fNo = :fNo and q.question like concat('%', :keyword, '%') " +
            "and (u is null or u.username = :username) " +
            "and (:importance is null or qi.importance in :importance) " +
            "and (:recognized is null or qi.isRecognized = :recognized)")
    Page<QuestionResponseDto> findAllByFolderWithUser(@Param("fNo")Long fNo, @Param("username") String username, @Param("keyword") String keyword,
                                                      @Param("recognized") Boolean recognized, @Param("importance")List<Importance> importance, Pageable pageable);

    @Query("select new education.knowing.dto.question.response.QuestionResponseDto(q.qNo, q.question, q.answer) " +
            "from Question q left join q.questionInfoList qi " +
            "where qi.user.username = :username and qi.isRecognized = false")
    List<QuestionResponseDto> findAllQuizByUser(@Param("username") String username);

    @Query("select new education.knowing.dto.question.response.QuestionResponseDto(q.qNo, q.question, q.answer, qi.importance, qi.isRecognized) " +
            "from Question q " +
            "left join q.questionInfoList qi " +
            "left join qi.user u " +
            "where q.qNo = :qNo and (qi is null or u.username = :username)")
    Optional<QuestionResponseDto> findByIdWithUser(@Param("qNo")Long qNo, @Param("username")String username);
}
