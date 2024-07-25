package education.knowing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FolderQna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Folder folder;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;
}
