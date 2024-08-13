package education.knowing.service;

import education.knowing.dto.email.request.CertificationRequestDto;
import education.knowing.dto.email.request.FindPasswordRequestDto;
import education.knowing.dto.email.response.CertificationResponseDto;
import education.knowing.entity.EmailCertification;
import education.knowing.entity.User;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import education.knowing.repository.EmailCertificationRepository;
import education.knowing.repository.UserRepository;
import education.knowing.util.MailSendUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {
    private final UserRepository userRepository;
    private final EmailCertificationRepository certificationRepository;
    private final MailSendUtil mailSendUtil;
    public void sendCertificationEmail(CertificationRequestDto certificationDto){
        //이메일이 존재하지 않으면
        if(!userRepository.existsByEmail(certificationDto.getEmail())){
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
    }

    public void sendCertificationEmailForPassword(FindPasswordRequestDto findPasswordRequestDto) {
        if(userRepository.existsByUsernameAndEmail(findPasswordRequestDto.getId(), findPasswordRequestDto.getEmail())){
            throw new BusinessLogicException(BusinessError.WRONG_ID_OR_EMAIL);
        }
        sendCertificationEmail(findPasswordRequestDto);
    }

    public void certificationEmail(CertificationRequestDto certificationDto){
        if(!certificationRepository.existsByEmailAndCertificationNumber(
                certificationDto.getEmail(),
                certificationDto.getCertificationNumber())){
            throw new BusinessLogicException(BusinessError.CERTIFICATION_FAIL);
        }

        certificationRepository.deleteById(certificationDto.getEmail());
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
