package education.knowing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {
    //400 (잘못된 요청)
    WRONG_ID_OR_EMAIL(400, "아이디 혹은 이메일이 틀렸습니다."),
    CERTIFICATION_FAIL(400, "이메일 인증 실패"),
    OPINION_SUBMIT_LIMITED_TO_10MINUTES(400, "10분 이내 사용자 의견 재작성 불가"),
    IMPORTANCE_SETTING_ERROR(400, "문답 중요도는 HIGH, MEDIUM, LOW 만 입력 가능합니다."),

    //403 (권한 부족)
    NOT_FOLDER_CREATOR(403, "해당 폴더의 작성자가 아님"),
    NOT_QUESTION_CREATOR(403, "해당 문답의 작성자가 아님"),
    NOT_OPINION_CREATOR(403, "해당 사용자 의견의 작성자가 아님"),

    //404 (존재하지 않음)
    USER_NOT_FOUND(404, "존재하지 않는 유저"),
    EMAIL_NOT_FOUND(404, "존재하지 않는 이메일"),
    FOLDER_NOT_FOUND(404, "존재하지 않는 폴더"),
    QUESTION_NOT_FOUND(404, "존재하지 않는 문답"),
    OPINION_NOT_FOUND(404, "존재하지 않는 사용자 의견"),

    //409 (충돌)
    DUPLICATED_ID(409, "이미 존재하는 아이디"),
    DUPLICATED_EMAIL(409, "이미 존재하는 이메일"),
    DUPLICATED_NICKNAME(409, "이미 존재하는 닉네임"),

    //500 (서버 오류)
    MAIL_FAIL(500, "이메일 전송 오류 발생");

    private final int status;

    private final String message;
}
