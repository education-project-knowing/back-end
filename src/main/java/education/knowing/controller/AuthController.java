package education.knowing.controller;

import education.knowing.dto.UserDto;
import education.knowing.dto.response.ResponseDto;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import education.knowing.service.AuthService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ResponseDto<Object>> join(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authService.join(userDto));
    }

    @PostMapping("/id-check")
    public ResponseEntity<?> idCheck(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authService.idCheck(userDto.getUsername()));
    }

    @PostMapping("/email-check")
    public ResponseEntity<?> emailCheck(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authService.emailCheck(userDto.getEmail()));
    }

    @PostMapping("/nickname-check")
    public ResponseEntity<?> nicknameCheck(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authService.nicknameCheck(userDto.getNickname()));
    }
}
