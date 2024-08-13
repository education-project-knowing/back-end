package education.knowing.controller;

import education.knowing.dto.auth.request.*;
import education.knowing.dto.auth.response.SignUpResponseDto;
import education.knowing.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

}
