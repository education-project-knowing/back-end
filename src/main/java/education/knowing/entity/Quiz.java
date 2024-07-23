package education.knowing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quiz_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    private int importance;

    private boolean isRecognized;
}
