package education.knowing.controller;

import education.knowing.dto.email.request.CertificationRequestDto;
import education.knowing.dto.email.request.FindPasswordRequestDto;
import education.knowing.dto.email.response.CertificationResponseDto;
import education.knowing.service.EmailService;
import education.knowing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {
    private final EmailService emailService;
    private final UserService userService;
    @PostMapping("/email/send/join")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@RequestBody @Valid CertificationRequestDto certificationDto) {
        certificationDto.setPurpose("회원가입 인증메일");
        emailService.sendCertificationEmail(certificationDto);
    }
    @PostMapping("/email/send/find/id")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailForId(@RequestBody @Valid CertificationRequestDto certificationDto){
        certificationDto.setPurpose("아이디 찾기 인증메일");
        emailService.sendCertificationEmail(certificationDto);
    }
    @PostMapping("/email/send/find/password")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailForPassword(@RequestBody @Valid FindPasswordRequestDto findPasswordRequestDto){
        findPasswordRequestDto.setPurpose("비밀번호 찾기 인증메일");
        emailService.sendCertificationEmailForPassword(findPasswordRequestDto);
    }

    @PostMapping("/email/send/modify")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailForEmail(@RequestBody @Valid FindPasswordRequestDto findPasswordRequestDto){
        findPasswordRequestDto.setPurpose("이메일 수정 인증메일");
        emailService.sendCertificationEmailForPassword(findPasswordRequestDto);
    }

    @PostMapping("/email/certification")
    @ResponseStatus(HttpStatus.OK)
    public void certificationEmail(@RequestBody @Valid CertificationRequestDto certificationDto){
        emailService.certificationEmail(certificationDto);
    }

    @PostMapping("/email/certification/id")
    @ResponseStatus(HttpStatus.OK)
    public CertificationResponseDto certificationEmailForId(@RequestBody @Valid CertificationRequestDto certificationDto){
        return emailService.certificationEmailForId(certificationDto);
    }

    @PatchMapping("/email/certification/email")
    @ResponseStatus(HttpStatus.OK)
    public void certificationEmailForEmail(@RequestBody @Valid CertificationRequestDto certificationDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        emailService.certificationEmail(certificationDto);
        userService.changeEmail(username, certificationDto.getEmail());
    }
}
