package education.knowing.controller;

import education.knowing.dto.request.FolderRequestDto;
import education.knowing.dto.response.FolderResponseDto;
import education.knowing.dto.response.ResponseDto;
import education.knowing.service.FolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folder")
public class FolderController {
    private final FolderService folderService;
    @GetMapping("/list")
    public ResponseEntity<List<FolderResponseDto>> getList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if(authentication != null){
            username = authentication.getName();
        }
        return ResponseEntity.ok(folderService.getFolderList(username));
    }
    @PostMapping
    public ResponseEntity<FolderResponseDto> register(@RequestBody @Valid FolderRequestDto folderRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        folderRequestDto.setCreateBy(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(folderService.createFolder(folderRequestDto));
    }

    @PutMapping("/{fNo}")
    public ResponseEntity<ResponseDto<?>> update(@PathVariable Long fNo,
                                                    @RequestBody @Valid FolderRequestDto folderRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        folderRequestDto.setCreateBy(username);

        return ResponseEntity.ok(folderService.updateFolder(fNo, folderRequestDto));
    }

    @DeleteMapping("/{fNo}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable Long fNo){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(folderService.deleteFolder(fNo, username));
    }


}
