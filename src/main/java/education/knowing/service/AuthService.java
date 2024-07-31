package education.knowing.service;

import education.knowing.constant.Role;
import education.knowing.dto.auth.request.CertificationRequestDto;
import education.knowing.dto.auth.request.FindPasswordRequestDto;
import education.knowing.dto.auth.request.SignUpRequestDto;
import education.knowing.dto.ResponseDto;
import education.knowing.dto.auth.response.CertificationResponseDto;
import education.knowing.dto.auth.response.SignUpResponseDto;
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

    public SignUpResponseDto join(SignUpRequestDto userDto){
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

        return new SignUpResponseDto(result.getId());
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

    public ResponseDto<?> sendCertificationEmail(CertificationRequestDto certificationDto){
        //이메일이 존재하지 않으면
        if(!emailCheck(certificationDto.getEmail())){
            throw new BusinessLogicException(BusinessError.EMAIL_NOT_FOUND);
        }

        String email = certificationDto.getEmail();
        String certificationNumber = createCertificationNumber();

        EmailCertification emailCertification = EmailCertification.builder()
                .email(email)
                .certificationNumber(certificationNumber)
                .build();

        try {
            mailSendUtil.sendCertificationMail(email, certificationDto.getPurpose(), certificationNumber);
        } catch (MessagingException e) {
            throw new BusinessLogicException(BusinessError.MAIL_FAIL);
        }

        certificationRepository.save(emailCertification);

        return new ResponseDto<>(200, "인증 메일 전송");
    }

    public ResponseDto<?> sendCertificationEmailForPassword(FindPasswordRequestDto findPasswordRequestDto) {
        if(userRepository.existsByUsernameAndEmail(findPasswordRequestDto.getId(), findPasswordRequestDto.getEmail())){
            throw new BusinessLogicException(BusinessError.WRONG_ID_OR_EMAIL);
        }
        return sendCertificationEmail(findPasswordRequestDto);
    }

    public ResponseDto<?> certificationEmail(CertificationRequestDto certificationDto){
        if(!certificationRepository.existsByEmailAndCertificationNumber(
                certificationDto.getEmail(),
                certificationDto.getCertificationNumber())){
            throw new BusinessLogicException(BusinessError.CERTIFICATION_FAIL);
        }

        certificationRepository.deleteById(certificationDto.getEmail());

        return new ResponseDto<>(200, "이메일 인증 성공");
    }
    public CertificationResponseDto certificationEmailForId(CertificationRequestDto certificationDto) {
        if(!certificationRepository.existsByEmailAndCertificationNumber(
                certificationDto.getEmail(),
                certificationDto.getCertificationNumber())){
            throw new BusinessLogicException(BusinessError.CERTIFICATION_FAIL);
        }
        User user = userRepository.findByEmail(certificationDto.getEmail());

        certificationRepository.deleteById(certificationDto.getEmail());

        return new CertificationResponseDto(user.getUsername());
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
