package education.knowing.service;

import education.knowing.dto.question.response.QuestionResponseDto;
import education.knowing.repository.QuestionInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizService {
    private final QuestionInfoRepository questionInfoRepository;
    @Transactional(readOnly = true)
    public List<QuestionResponseDto> getQuizList(String username){
        return questionInfoRepository.findAllQuizByUser(username);
    }

}
