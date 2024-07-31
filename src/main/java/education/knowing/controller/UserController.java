package education.knowing.controller;

import education.knowing.dto.user.request.ChangePasswordDto;
import education.knowing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PatchMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto){
        userService.changePassword(changePasswordDto);
    }
}
