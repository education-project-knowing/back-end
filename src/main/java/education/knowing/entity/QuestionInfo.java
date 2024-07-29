package education.knowing.entity;

import education.knowing.constant.Importance;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "question_info_tbl")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QuestionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    private boolean isRecognized;

    public void updateQuestionInfo(Importance importance, boolean isRecognized){
        this.importance = importance;
        this.isRecognized = isRecognized;
    }
}
