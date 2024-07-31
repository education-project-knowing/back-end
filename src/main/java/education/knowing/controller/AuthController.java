package education.knowing.controller;

import education.knowing.dto.auth.request.*;
import education.knowing.dto.ResponseDto;
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
    public ResponseEntity<SignUpResponseDto> join(@RequestBody @Valid SignUpRequestDto userSignUpDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.join(userSignUpDto));
    }

    @PostMapping("/id-check")
    public ResponseEntity<Boolean> checkId(@RequestBody @Valid IdCheckRequestDto idCheckRequestDto){
        return ResponseEntity.ok(authService.idCheck(idCheckRequestDto.getUsername()));
    }

    @PostMapping("/email-check")
    public ResponseEntity<Boolean> checkEmail(@RequestBody @Valid EmailCheckRequestDto emailCheckRequestDto){
        return ResponseEntity.ok(authService.emailCheck(emailCheckRequestDto.getEmail()));
    }

    @PostMapping("/nickname-check")
    public ResponseEntity<Boolean> checkNickname(@RequestBody @Valid NicknameCheckRequestDto nicknameCheckRequestDto){
        return ResponseEntity.ok(authService.nicknameCheck(nicknameCheckRequestDto.getNickname()));
    }

    @PostMapping("/email/send")
    public ResponseEntity<ResponseDto<?>> sendEmail(@RequestBody @Valid CertificationRequestDto certificationDto) {
        certificationDto.setPurpose("회원가입 인증메일");
        return ResponseEntity.ok(authService.sendCertificationEmail(certificationDto));
    }
    @PostMapping("/email/send/id")
    public ResponseEntity<ResponseDto<?>> sendEmailForId(@RequestBody @Valid CertificationRequestDto certificationDto){
        certificationDto.setPurpose("아이디 찾기 인증메일");
        return ResponseEntity.ok(authService.sendCertificationEmail(certificationDto));
    }
    @PostMapping("/email/send/password")
    public ResponseEntity<ResponseDto<?>> sendEmailForPassword(@RequestBody @Valid FindPasswordRequestDto findPasswordRequestDto){
        findPasswordRequestDto.setPurpose("비밀번호 찾기 인증메일");
        return ResponseEntity.ok(authService.sendCertificationEmailForPassword(findPasswordRequestDto));
    }

    @PostMapping("/email/certification")
    public ResponseEntity<ResponseDto<?>> certificationEmail(@RequestBody @Valid CertificationRequestDto certificationDto){
        return ResponseEntity.ok(authService.certificationEmail(certificationDto));
    }

    @PostMapping("/email/certification/id")
    public ResponseEntity<CertificationResponseDto> certificationEmailForId(@RequestBody @Valid CertificationRequestDto certificationDto){
        return ResponseEntity.ok(authService.certificationEmailForId(certificationDto));
    }

}
