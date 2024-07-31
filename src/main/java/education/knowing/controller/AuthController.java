package education.knowing.controller;

import education.knowing.dto.auth.request.*;
import education.knowing.dto.auth.response.CertificationResponseDto;
import education.knowing.dto.auth.response.SignUpResponseDto;
import education.knowing.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponseDto join(@RequestBody @Valid SignUpRequestDto userSignUpDto) {
        return authService.join(userSignUpDto);
    }

    @PostMapping("/id-check")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkId(@RequestBody @Valid IdCheckRequestDto idCheckRequestDto){
        return authService.idCheck(idCheckRequestDto.getUsername());
    }

    @PostMapping("/email-check")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkEmail(@RequestBody @Valid EmailCheckRequestDto emailCheckRequestDto){
        return authService.emailCheck(emailCheckRequestDto.getEmail());
    }

    @PostMapping("/nickname-check")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkNickname(@RequestBody @Valid NicknameCheckRequestDto nicknameCheckRequestDto){
        return authService.nicknameCheck(nicknameCheckRequestDto.getNickname());
    }

    @PostMapping("/email/send")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@RequestBody @Valid CertificationRequestDto certificationDto) {
        certificationDto.setPurpose("회원가입 인증메일");
        authService.sendCertificationEmail(certificationDto);
    }
    @PostMapping("/email/send/id")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailForId(@RequestBody @Valid CertificationRequestDto certificationDto){
        certificationDto.setPurpose("아이디 찾기 인증메일");
        authService.sendCertificationEmail(certificationDto);
    }
    @PostMapping("/email/send/password")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailForPassword(@RequestBody @Valid FindPasswordRequestDto findPasswordRequestDto){
        findPasswordRequestDto.setPurpose("비밀번호 찾기 인증메일");
        authService.sendCertificationEmailForPassword(findPasswordRequestDto);
    }

    @PostMapping("/email/certification")
    @ResponseStatus(HttpStatus.OK)
    public void certificationEmail(@RequestBody @Valid CertificationRequestDto certificationDto){
        authService.certificationEmail(certificationDto);
    }

    @PostMapping("/email/certification/id")
    public ResponseEntity<CertificationResponseDto> certificationEmailForId(@RequestBody @Valid CertificationRequestDto certificationDto){
        return ResponseEntity.ok(authService.certificationEmailForId(certificationDto));
    }

}
