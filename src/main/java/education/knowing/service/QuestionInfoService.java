package education.knowing.service;

import education.knowing.dto.request.QuestionInfoRequestDto;
import education.knowing.dto.response.ResponseDto;
import education.knowing.entity.Question;
import education.knowing.entity.QuestionInfo;
import education.knowing.entity.User;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import education.knowing.repository.QuestionRepository;
import education.knowing.repository.QuestionInfoRepository;
import education.knowing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionInfoService {
    private final QuestionInfoRepository questionInfoRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public ResponseDto<?> createOrUpdateQuestionInfo(String username, Long qNo, QuestionInfoRequestDto questionInfoRequestDto){
        Optional<QuestionInfo> optionalQuestionInfo = questionInfoRepository.findByUserUsernameAndQuestionQNo(username, qNo);

        int importance = questionInfoRequestDto.getImportance();
        boolean isRecognized = questionInfoRequestDto.isRecognized();

        QuestionInfo questionInfo = null;

        //유저가 등록한 내역이 없으면
        if(optionalQuestionInfo.isEmpty()){
            User user = userRepository.findByUsername(username)
                    .orElseThrow(()->new BusinessLogicException(BusinessError.USER_NOT_FOUND));

            Question question = questionRepository.findById(qNo)
                    .orElseThrow(()-> new BusinessLogicException(BusinessError.QUESTION_NOT_FOUND));

            questionInfo = QuestionInfo.builder()
                    .user(user).question(question)
                    .importance(importance)
                    .isRecognized(isRecognized)
                    .build();

        //유저가 등록한 내역이 있으면
        } else{
            questionInfo = optionalQuestionInfo.get();

            questionInfo.updateQuestionInfo(importance, isRecognized);
        }
        questionInfoRepository.save(questionInfo);

        return new ResponseDto<>(200, "퀴즈 수정");
    }
}
