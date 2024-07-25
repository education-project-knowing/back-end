package education.knowing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "study_tbl")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "f_no")
    @ManyToOne(fetch = FetchType.LAZY)
    private Folder folder;

    private int studyTimeSeconds;
    private boolean favorite;
}
