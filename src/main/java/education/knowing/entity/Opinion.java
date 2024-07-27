package education.knowing.entity;

import education.knowing.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "opinion_tbl")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Opinion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long opinionId;

    @Column(nullable = false)
    private String opinion;

    private boolean isRead;

    public void updateOpinion(String opinion){
        this.opinion = opinion;
    }
}
