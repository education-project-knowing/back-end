package education.knowing.service;

import education.knowing.dto.ResponseDto;
import education.knowing.dto.paging.request.QuestionPageRequestDto;
import education.knowing.dto.paging.response.PageResponseDto;
import education.knowing.dto.question.request.QuestionRequestDto;
import education.knowing.dto.question.response.QuestionResponseDto;
import education.knowing.entity.Folder;
import education.knowing.entity.FolderQna;
import education.knowing.entity.Question;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import education.knowing.repository.FolderQnaRepository;
import education.knowing.repository.FolderRepository;
import education.knowing.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final FolderRepository folderRepository;
    private final FolderQnaRepository folderQnaRepository;

    @Transactional(readOnly = true)
    public PageResponseDto<QuestionResponseDto> getQuestionList(String username, QuestionPageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1,
                pageRequestDto.getSize(), Sort.by(pageRequestDto.getOrderBy()).descending());

        Page<QuestionResponseDto> result;
        if (username.equals("anonymousUser")) {
            result = questionRepository.findAll(pageRequestDto.getKeyword(), pageable);
        } else {
            result = questionRepository.findAllWithUser(username, pageRequestDto.getKeyword(), pageRequestDto.getRecognized(),
                    pageRequestDto.getImportance(), pageable);
        }

        return new PageResponseDto<>(result.getContent(), pageRequestDto, result.getTotalElements());
    }
    @Transactional(readOnly = true)
    public PageResponseDto<QuestionResponseDto> getQuestionListByFolder(Long fNo, String username, QuestionPageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1,
                pageRequestDto.getSize(), Sort.by(pageRequestDto.getOrderBy()).descending());

        Page<QuestionResponseDto> result;
        if (username.equals("anonymousUser")) {
            result = questionRepository.findAllByFolder(fNo, pageRequestDto.getKeyword(), pageable);
        } else {
            result = questionRepository.findAllByFolderWithUser(fNo, username, pageRequestDto.getKeyword(), pageRequestDto.getRecognized(),
                    pageRequestDto.getImportance(), pageable);
        }

        return new PageResponseDto<>(result.getContent(), pageRequestDto, result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public QuestionResponseDto getQuestion(Long qNo, String username) {
        if (username.equals("anonymousUser")) {
            Question question = questionRepository.findById(qNo)
                    .orElseThrow(() -> new BusinessLogicException(BusinessError.QUESTION_NOT_FOUND));

            return new QuestionResponseDto(qNo, question.getQuestion(), question.getAnswer());
        } else {
            return questionRepository.findByIdWithUser(qNo,username).orElseThrow(() -> new BusinessLogicException(BusinessError.QUESTION_NOT_FOUND));
        }
    }


    public QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto){
        Question question = Question.builder()
                .question(questionRequestDto.getQuestion())
                .answer(questionRequestDto.getAnswer())
                .build();

        Folder folder = folderRepository.findById(questionRequestDto.getFNo()).orElseThrow(
                ()-> new BusinessLogicException(BusinessError.FOLDER_NOT_FOUND)
        );

        Question result = questionRepository.save(question);

        FolderQna folderQna = FolderQna.builder()
                .folder(folder)
                .question(question)
                .build();

        folderQnaRepository.save(folderQna);

        return new QuestionResponseDto(result.getQNo(), result.getQuestion(), result.getAnswer());
    }
    public QuestionResponseDto createQuestion(Long fNo, QuestionRequestDto questionRequestDto){
        Question question = Question.builder()
                .question(questionRequestDto.getQuestion())
                .answer(questionRequestDto.getAnswer())
                .build();

        Folder folder = folderRepository.findById(fNo).orElseThrow(
                ()-> new BusinessLogicException(BusinessError.FOLDER_NOT_FOUND)
        );

        Question result = questionRepository.save(question);

        FolderQna folderQna = FolderQna.builder()
                .folder(folder)
                .question(question)
                .build();

        folderQnaRepository.save(folderQna);

        return new QuestionResponseDto(result.getQNo(), result.getQuestion(), result.getAnswer());
    }

    public void updateQuestion(Long qNo, String username, QuestionRequestDto questionRequestDto){
        Question question = questionRepository.findById(qNo).orElseThrow(
                ()-> new BusinessLogicException(BusinessError.QUESTION_NOT_FOUND)
        );

        if(!question.getCreatedBy().equals(username)){
            throw new BusinessLogicException(BusinessError.NOT_QUESTION_CREATOR);
        }

        question.updateQuestion(questionRequestDto.getQuestion(), questionRequestDto.getAnswer());

        questionRepository.save(question);

    }
    public ResponseDto<?> deleteQuestion(Long qNo, String username){
        Question question = questionRepository.findById(qNo).orElseThrow(
                ()-> new BusinessLogicException(BusinessError.QUESTION_NOT_FOUND)
        );

        if(!question.getCreatedBy().equals(username)){
            throw new BusinessLogicException(BusinessError.NOT_QUESTION_CREATOR);
        }

        questionRepository.delete(question);

        return new ResponseDto<>(200, "문답 삭제 완료");
    }

    public ResponseDto<?> deleteQuestionByAdmin(Long qNo){
        Question question = questionRepository.findById(qNo).orElseThrow(
                ()-> new BusinessLogicException(BusinessError.QUESTION_NOT_FOUND)
        );

        questionRepository.delete(question);

        return new ResponseDto<>(200, "문답 삭제 완료");
    }

}
