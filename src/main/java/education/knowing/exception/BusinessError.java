package education.knowing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {
    //400 (잘못된 요청)
    CERTIFICATION_FAIL(400, "이메일 인증 실패"),

    //404 (존재하지 않음)
    USER_NOT_FOUND(404, "존재하지 않는 유저"),
    EMAIL_NOT_FOUND(404, "존재하지 않는 이메일"),

    //409 (충돌)
    DUPLICATED_ID(409, "이미 존재하는 아이디"),
    DUPLICATED_EMAIL(409, "이미 존재하는 이메일"),
    DUPLICATED_NICKNAME(409, "이미 존재하는 닉네임"),

    //500 (서버 오류)
    MAIL_FAIL(500, "이메일 전송 오류 발생");

    private final int status;

    private final String message;
}
