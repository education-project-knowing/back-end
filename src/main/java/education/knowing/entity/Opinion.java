package education.knowing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "opinion_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long opinionId;

    @JoinColumn(name = "writer")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String opinion;
}
