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
    @ResponseStatus(HttpStatus.OK)
    public PageResponseDto<QuestionResponseDto> getList(@RequestBody QuestionPageRequestDto questionPageRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return questionService.getQuestionList(username, questionPageRequestDto);
    }
    @GetMapping("/list/{fNo}")
    @ResponseStatus(HttpStatus.OK)
    public PageResponseDto<QuestionResponseDto> getListByFolder(@PathVariable Long fNo, @RequestBody QuestionPageRequestDto questionPageRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return questionService.getQuestionListByFolder(fNo,username, questionPageRequestDto);
    }
    @GetMapping("/{qNo}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionResponseDto getQuestion(@PathVariable Long qNo){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return questionService.getQuestion(qNo, username);
    }

    @GetMapping("/quiz")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionResponseDto> getQuiz(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return quizService.getQuizList(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponseDto createQuestion(@RequestBody QuestionRequestDto questionRequestDto){
        return questionService.createQuestion(questionRequestDto);
    }

    @PostMapping("/{fNo}")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponseDto createQuestion(@PathVariable Long fNo, @RequestBody QuestionRequestDto questionRequestDto){
        return questionService.createQuestion(fNo, questionRequestDto);
    }

    @PutMapping("/{qNo}")
    @ResponseStatus(HttpStatus.OK)
    public void updateQuestion(@PathVariable Long qNo, @RequestBody QuestionRequestDto questionRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        questionService.updateQuestion(qNo, username, questionRequestDto);
    }

    @DeleteMapping("/{qNo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable Long qNo){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        questionService.deleteQuestion(qNo, username);
    }

    @PutMapping("/info/{qNo}")
    @ResponseStatus(HttpStatus.OK)
    public void createOrUpdateQuestionInfo(@PathVariable Long qNo, @RequestBody QuestionInfoRequestDto questionInfoRequestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        questionInfoService.createOrUpdateQuestionInfo(username, qNo, questionInfoRequestDto);
    }
}
