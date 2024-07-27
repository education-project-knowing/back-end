package education.knowing.dto.oauth;

public interface OAuthResponse {
    //제공자
    String getProvider();
    //제공자가 발급해주는 아이디
    String getProviderId();
    //이메일
    String getEmail();
    //닉네임
    String getNickname();
}
