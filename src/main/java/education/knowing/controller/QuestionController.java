package education.knowing.controller;

import education.knowing.dto.question.request.QuestionInfoRequestDto;
import education.knowing.dto.question.request.QuestionRequestDto;
import education.knowing.dto.paging.request.QuestionPageRequestDto;
import education.knowing.dto.question.response.QuestionResponseDto;
import education.knowing.dto.paging.response.PageResponseDto;
import education.knowing.service.QuestionInfoService;
import education.knowing.service.QuestionService;
import education.knowing.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;
    private final QuestionInfoService questionInfoService;

    @GetMapping("/list")
    public ResponseEntity<PageResponseDto<QuestionResponseDto>> getList(@RequestBody QuestionPageRequestDto questionPageRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(questionService.getQuestionList(username, questionPageRequestDto));
    }
    @GetMapping("/list/{fNo}")
    public ResponseEntity<PageResponseDto<QuestionResponseDto>> getListByFolder(@PathVariable Long fNo, @RequestBody QuestionPageRequestDto questionPageRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(questionService.getQuestionListByFolder(fNo,username, questionPageRequestDto));
    }
    @GetMapping("/{qNo}")
    public ResponseEntity<QuestionResponseDto> getQuestion(@PathVariable Long qNo){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(questionService.getQuestion(qNo, username));
    }

    @GetMapping("/quiz")
    public ResponseEntity<List<QuestionResponseDto>> getQuiz(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(quizService.getQuizList(username));
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(@RequestBody QuestionRequestDto questionRequestDto){
        System.out.println(questionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createQuestion(questionRequestDto));
    }

    @PostMapping("/{fNo}")
    public ResponseEntity<QuestionResponseDto> createQuestion(@PathVariable Long fNo, @RequestBody QuestionRequestDto questionRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createQuestion(fNo, questionRequestDto));
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

    @PutMapping("/info/{qNo}")
    public ResponseEntity<?> createOrUpdateQuestionInfo(@PathVariable Long qNo, @RequestBody QuestionInfoRequestDto questionInfoRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(questionInfoService.createOrUpdateQuestionInfo(username, qNo, questionInfoRequestDto));
    }
}
