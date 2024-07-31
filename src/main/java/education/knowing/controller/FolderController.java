package education.knowing.controller;

import education.knowing.dto.folder.request.FolderRequestDto;
import education.knowing.dto.folder.request.StudyRequestDto;
import education.knowing.dto.folder.response.FolderResponseDto;
import education.knowing.service.FolderService;
import education.knowing.service.StudyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<FolderResponseDto> getList(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return folderService.getFolderList(username);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FolderResponseDto register(@RequestBody @Valid FolderRequestDto folderRequestDto){
        return folderService.createFolder(folderRequestDto);
    }

    @PutMapping("/{fNo}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long fNo, @RequestBody @Valid FolderRequestDto folderRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        folderService.updateFolder(fNo, username, folderRequestDto);
    }

    @DeleteMapping("/{fNo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long fNo){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        folderService.deleteFolder(fNo, username);
    }


    @PutMapping("/study/{fNo}")
    @ResponseStatus(HttpStatus.OK)
    public void study(@PathVariable Long fNo, @RequestBody StudyRequestDto studyRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        studyService.study(fNo, username, studyRequestDto);
    }
}
