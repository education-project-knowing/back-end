package education.knowing.entity;

import education.knowing.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folder_tbl")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Folder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fNo;

    private boolean isPublic;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 100)
    private String intro;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FolderQna> questionList = new ArrayList<>();

    public void updateInfo(String title, String intro, boolean isPublic){
        this.title = title;
        this.intro = intro;
        this.isPublic = isPublic;
    }

    public void clear(){
        this.questionList.clear();
    }
}
