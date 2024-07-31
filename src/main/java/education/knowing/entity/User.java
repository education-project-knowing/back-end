package education.knowing.entity;

import education.knowing.constant.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_tbl", indexes = {
        @Index(name = "idx_username", columnList = "username")
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }
    public void changeEmail(String email){this.email = email;}
    public void changePassword(String password){
        this.password = password;
    }
}
