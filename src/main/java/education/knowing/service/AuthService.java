package education.knowing.service;

import education.knowing.constant.Role;
import education.knowing.dto.CertificationDto;
import education.knowing.dto.request.SignUpRequestDto;
import education.knowing.dto.response.ResponseDto;
import education.knowing.entity.EmailCertification;
import education.knowing.entity.User;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import education.knowing.repository.EmailCertificationRepository;
import education.knowing.repository.UserRepository;
import education.knowing.util.MailSendUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailCertificationRepository certificationRepository;
    private final MailSendUtil mailSendUtil;

    public ResponseDto<Object> join(SignUpRequestDto userDto){
        //중복 체크
        if(idCheck(userDto.getUsername())) {
            throw new BusinessLogicException(BusinessError.DUPLICATED_ID);
        }
        if(emailCheck(userDto.getEmail())){
            throw new BusinessLogicException(BusinessError.DUPLICATED_EMAIL);
        }
        if(nicknameCheck(userDto.getNickname())){
            throw new BusinessLogicException(BusinessError.DUPLICATED_NICKNAME);
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .role(Role.USER)
                .build();

        User result = userRepository.save(user);


        return new ResponseDto<>(201, "회원가입 성공", null);
    }

    @Transactional(readOnly = true)
    public boolean idCheck(String id){
        return userRepository.existsByUsername(id);
    }
    @Transactional(readOnly = true)
    public boolean emailCheck(String email){
        return userRepository.existsByEmail(email);

    }
    @Transactional(readOnly = true)
    public boolean nicknameCheck(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public ResponseDto<CertificationDto> sendCertificationEmail(CertificationDto certificationDto){
        String email = certificationDto.getEmail();
        String certificationNumber = createCertificationNumber();

        EmailCertification emailCertification = EmailCertification.builder()
                .email(email)
                .certificationNumber(certificationNumber)
                .build();

        try {
            mailSendUtil.sendCertificationMail(email, "회원가입 인증번호 도착!", certificationNumber);
        } catch (MessagingException e) {
            throw new BusinessLogicException(BusinessError.MAIL_FAIL);
        }

        certificationRepository.save(emailCertification);

        certificationDto.setCertificationNumber(certificationNumber);
        return new ResponseDto<>(200, "이메일 전송 성공", certificationDto);
    }

    public ResponseDto<Object> certificationEmail(CertificationDto certificationDto){
        if(!certificationRepository.existsByEmailAndCertificationNumber(
                certificationDto.getEmail(),
                certificationDto.getCertificationNumber())){
            throw new BusinessLogicException(BusinessError.CERTIFICATION_FAIL);
        }
        return new ResponseDto<>(200, "이메일 인증 성공", null);
    }


    private String createCertificationNumber(){
        Random random = new Random();
        String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
        StringBuilder certificationNumber = new StringBuilder();
        for(int i = 0; i < 6; i++){
            certificationNumber.append(numbers[random.nextInt(10)]);
        }
        return certificationNumber.toString();
    }

}
