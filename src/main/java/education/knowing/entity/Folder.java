package education.knowing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folder_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fNo;

    @JoinColumn(name = "create_by")
    @ManyToOne(fetch = FetchType.LAZY)
    private User createBy;

    private boolean isPublic;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 100)
    private String intro;

    @OneToMany(mappedBy = "folder")
    @Builder.Default
    private List<FolderQna> questionList = new ArrayList<>();
}
