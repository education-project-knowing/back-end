package education.knowing.controller;

import education.knowing.dto.CertificationDto;
import education.knowing.dto.request.SignUpRequestDto;
import education.knowing.dto.response.ResponseDto;
import education.knowing.dto.response.SignUpResponseDto;
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
    public ResponseEntity<Boolean> checkId(@RequestBody SignUpRequestDto userSignUpDto){
        return ResponseEntity.ok(authService.idCheck(userSignUpDto.getUsername()));
    }

    @PostMapping("/email-check")
    public ResponseEntity<Boolean> checkEmail(@RequestBody SignUpRequestDto userSignUpDto){
        return ResponseEntity.ok(authService.emailCheck(userSignUpDto.getEmail()));
    }

    @PostMapping("/nickname-check")
    public ResponseEntity<Boolean> checkNickname(@RequestBody SignUpRequestDto userSignUpDto){
        return ResponseEntity.ok(authService.nicknameCheck(userSignUpDto.getNickname()));
    }

    @PostMapping("/email/send")
    public ResponseEntity<CertificationDto> sendEmail(@RequestBody @Valid CertificationDto certificationDto){
        return ResponseEntity.ok(authService.sendCertificationEmail(certificationDto));
    }

    @PostMapping("/email/certification")
    public ResponseEntity<ResponseDto<?>> certificationEmail(@RequestBody @Valid CertificationDto certificationDto){
        return ResponseEntity.ok(authService.certificationEmail(certificationDto));
    }
}
