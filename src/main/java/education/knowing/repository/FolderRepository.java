package education.knowing.repository;

import education.knowing.dto.response.FolderResponseDto;
import education.knowing.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("select new education.knowing.dto.response.FolderResponseDto(f.fNo, f.title, f.intro, f.createBy.nickname, count(fq), f.isPublic) " +
            "from Folder f " +
            "left join f.questionList fq " +
            "where f.isPublic = true " +
            "group by f.fNo " +
            "order by f.createdDate desc")
    List<FolderResponseDto> findAllWithQuestionCount();

    @Query("select new education.knowing.dto.response.FolderResponseDto(f.fNo, f.title, f.intro, f.createBy.nickname, count(fq), SUM(CASE WHEN qu.user.username = :username AND qu.isRecognized = true THEN 1 ELSE 0 END), f.isPublic) " +
            "from Folder f " +
            "left join f.questionList fq " +
            "left join fq.question q " +
            "left join q.quizList qu " +
            "WHERE f.isPublic = true and qu.isRecognized = true " +
            "group by f.fNo " +
            "order by f.createdDate desc")
    List<FolderResponseDto> findAllWithQuestionCountAndRecognizeCount(@Param("username") String username);

    @Query("select f from Folder f join fetch f.createBy where f.fNo = :fNo")
    Optional<Folder> findByIdWithUser(@Param("fNo") Long fNo);
}
