package education.knowing.repository;

import education.knowing.dto.folder.response.FolderResponseDto;
import education.knowing.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("select new education.knowing.dto.folder.response.FolderResponseDto(f.fNo, f.title, f.intro, f.createdBy, count(fq), f.isPublic) " +
            "from Folder f " +
            "left join f.questionList fq " +
            "where f.isPublic = true " +
            "group by f.fNo")
    List<FolderResponseDto> findAllWithQuestionCount();

    @Query("select new education.knowing.dto.folder.response.FolderResponseDto(f.fNo, f.title, f.intro, f.createdBy, count(fq), SUM(CASE WHEN qi.user.username = :username AND qi.isRecognized = true THEN 1 ELSE 0 END), f.isPublic) " +
            "from Folder f " +
            "left join f.questionList fq " +
            "left join fq.question q " +
            "left join q.questionInfoList qi " +
            "WHERE f.isPublic = true and qi.isRecognized = true and qi.user.username =:username " +
            "group by f.fNo")
    List<FolderResponseDto> findAllWithQuestionCountAndRecognizeCount(@Param("username") String username);
}
