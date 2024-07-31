package education.knowing.controller;

import education.knowing.dto.opinion.request.OpinionRequestDto;
import education.knowing.dto.opinion.response.OpinionResponseDto;
import education.knowing.dto.ResponseDto;
import education.knowing.service.OpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/opinion")
public class OpinionController {
    private final OpinionService opinionService;
    @PostMapping
    public ResponseEntity<OpinionResponseDto> sendOpinion(@RequestBody OpinionRequestDto opinionRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(opinionService.sendOpinion(username, opinionRequestDto));
    }
    @PutMapping("/{opinionId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOpinion(@PathVariable Long opinionId, @RequestBody OpinionRequestDto opinionRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        opinionService.updateOpinion(opinionId, username, opinionRequestDto);
    }

    @DeleteMapping("/{opinionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOpinion(@PathVariable Long opinionId, @RequestBody OpinionRequestDto opinionRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        opinionService.deleteOpinion(opinionId, username);
    }

}
