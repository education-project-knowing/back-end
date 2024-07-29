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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CertificationResponseDto> sendEmail(@RequestBody @Valid CertificationRequestDto certificationDto){
        return ResponseEntity.ok(authService.sendCertificationEmail(certificationDto));
    }

    @PostMapping("/email/certification")
    public ResponseEntity<ResponseDto<?>> certificationEmail(@RequestBody @Valid CertificationRequestDto certificationDto){
        return ResponseEntity.ok(authService.certificationEmail(certificationDto));
    }
}
