package education.knowing.repository;

import education.knowing.dto.response.OpinionResponseDto;
import education.knowing.entity.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    List<Opinion> findOpinionsByCreatedBy(String createdBy);

    Opinion findFirstByCreatedByOrderByCreatedDate(String createBy);

    @Query("select new education.knowing.dto.response.OpinionResponseDto(o.opinionId, o.createdBy, o.opinion, o.createdDate) " +
            "from Opinion o where o.isRead = false")
    List<OpinionResponseDto> findNotReadOpinions();
}
