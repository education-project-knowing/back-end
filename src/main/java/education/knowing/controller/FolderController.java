package education.knowing.controller;

import education.knowing.dto.folder.request.FolderRequestDto;
import education.knowing.dto.folder.request.StudyRequestDto;
import education.knowing.dto.folder.response.FolderResponseDto;
import education.knowing.dto.ResponseDto;
import education.knowing.service.FolderService;
import education.knowing.service.StudyService;
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
    private final StudyService studyService;
    @GetMapping("/list")
    public ResponseEntity<List<FolderResponseDto>> getList(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(folderService.getFolderList(username));
    }
    @PostMapping
    public ResponseEntity<FolderResponseDto> register(@RequestBody @Valid FolderRequestDto folderRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(folderService.createFolder(folderRequestDto));
    }

    @PutMapping("/{fNo}")
    public ResponseEntity<ResponseDto<?>> update(@PathVariable Long fNo,
                                                    @RequestBody @Valid FolderRequestDto folderRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(folderService.updateFolder(fNo, username, folderRequestDto));
    }

    @DeleteMapping("/{fNo}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable Long fNo){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(folderService.deleteFolder(fNo, username));
    }


    @PutMapping("/study/{fNo}")
    public ResponseEntity<ResponseDto<?>> study(@PathVariable Long fNo, @RequestBody StudyRequestDto studyRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(studyService.study(fNo, username, studyRequestDto));
    }
}
