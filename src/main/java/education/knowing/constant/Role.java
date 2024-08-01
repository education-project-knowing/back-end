package education.knowing.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String authority;

    public static Role roleByAuthority(String authority){
        return Role.valueOf(authority.replace("ROLE_", ""));
    }
}
