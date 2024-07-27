package education.knowing.entity;

import education.knowing.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question_tbl", indexes = {
        @Index(name = "idx_question", columnList = "question")
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qNo;

    @Column(length = 50,nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<FolderQna> folderQnaList = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<QuestionInfo> questionInfoList = new ArrayList<>();

    public void updateQuestion(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public void clear(){
        this.folderQnaList = null;
        this.questionInfoList = null;
    }
}
