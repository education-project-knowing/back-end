package education.knowing.controller;

import education.knowing.dto.CertificationDto;
import education.knowing.dto.request.SignUpRequestDto;
import education.knowing.dto.response.ResponseDto;
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
    public ResponseEntity<ResponseDto<Object>> join(@RequestBody @Valid SignUpRequestDto userSignUpDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.join(userSignUpDto));
    }

    @PostMapping("/id-check")
    public ResponseEntity<ResponseDto<Boolean>> checkId(@RequestBody SignUpRequestDto userSignUpDto){
        return ResponseEntity.ok(new ResponseDto<>(200, "아이디 중복 검사", authService.idCheck(userSignUpDto.getUsername())));
    }

    @PostMapping("/email-check")
    public ResponseEntity<ResponseDto<Boolean>> checkEmail(@RequestBody SignUpRequestDto userSignUpDto){
        return ResponseEntity.ok(new ResponseDto<>(200, "비밀번호 중복 검사",authService.emailCheck(userSignUpDto.getEmail())));
    }

    @PostMapping("/nickname-check")
    public ResponseEntity<ResponseDto<Boolean>> checkNickname(@RequestBody SignUpRequestDto userSignUpDto){
        return ResponseEntity.ok(new ResponseDto<>(200, "닉네임 중복 검사",authService.nicknameCheck(userSignUpDto.getNickname())));
    }

    @PostMapping("/email/send")
    public ResponseEntity<ResponseDto<CertificationDto>> sendEmail(@RequestBody CertificationDto certificationDto){
        return ResponseEntity.ok(authService.sendCertificationEmail(certificationDto));
    }

    @PostMapping("/email/certification")
    public ResponseEntity<ResponseDto<Object>> certificationEmail(@RequestBody CertificationDto certificationDto){
        return ResponseEntity.ok(authService.certificationEmail(certificationDto));
    }
}
