package education.knowing.controller;

import education.knowing.dto.request.QuestionRequestDto;
import education.knowing.dto.request.paging.QuestionPageRequestDto;
import education.knowing.dto.response.QuestionResponseDto;
import education.knowing.dto.response.paging.PageResponseDto;
import education.knowing.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public ResponseEntity<PageResponseDto<QuestionResponseDto>> getList(@RequestBody QuestionPageRequestDto questionPageRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if(authentication != null){
            username = authentication.getName();
        }
        return ResponseEntity.ok(questionService.getQuestionList(username, questionPageRequestDto));
    }
    @GetMapping("/list/{fNo}")
    public ResponseEntity<PageResponseDto<QuestionResponseDto>> getList(@PathVariable Long fNo, QuestionPageRequestDto questionPageRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if(authentication != null){
            username = authentication.getName();
        }
        return ResponseEntity.ok(questionService.getQuestionListByFolder(fNo,username, questionPageRequestDto));
    }
    @GetMapping("/{qNo}")
    public ResponseEntity<QuestionResponseDto> getQuestion(@PathVariable Long qNo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if(authentication != null){
            username = authentication.getName();
        }
        return ResponseEntity.ok(questionService.getQuestion(qNo, username));
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(@RequestBody QuestionRequestDto questionRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createQuestion(questionRequestDto));
    }

    @PutMapping("/{qNo}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long qNo, @RequestBody QuestionRequestDto questionRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(questionService.updateQuestion(qNo, username, questionRequestDto));
    }

    @DeleteMapping("/{qNo}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long qNo){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(questionService.deleteQuestion(qNo, username));
    }
}
