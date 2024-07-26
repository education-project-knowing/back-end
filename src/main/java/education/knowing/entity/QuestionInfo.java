package education.knowing.entity;

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

    private int importance;

    private boolean isRecognized;

    public void updateQuestionInfo(int importance, boolean isRecognized){
        this.importance = importance;
        this.isRecognized = isRecognized;
    }
}
