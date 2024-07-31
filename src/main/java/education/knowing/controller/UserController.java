package education.knowing.controller;

import education.knowing.dto.user.request.ChangeNicknameDto;
import education.knowing.dto.user.request.ChangePasswordDto;
import education.knowing.dto.user.response.UserInfoResponseDto;
import education.knowing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserInfoResponseDto getMyInfo(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getInfo(username);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto){
        userService.changePassword(changePasswordDto);
    }

    @PatchMapping("/nickname")
    @ResponseStatus(HttpStatus.OK)
    public void changeNickname(@RequestBody @Valid ChangeNicknameDto changeNicknameDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.changeNickname(username, changeNicknameDto.getNickname());
    }
}
