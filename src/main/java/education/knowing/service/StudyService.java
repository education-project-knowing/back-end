package education.knowing.service;

import education.knowing.dto.folder.request.StudyRequestDto;
import education.knowing.dto.ResponseDto;
import education.knowing.entity.Study;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import education.knowing.repository.FolderRepository;
import education.knowing.repository.StudyRepository;
import education.knowing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;

    public ResponseDto<?> study(Long fNo, String username, StudyRequestDto studyRequestDto){
        Study study = studyRepository.findStudyByUserUsernameAndFolderFNo(username, fNo)
                .orElse(Study.builder()
                        .folder(folderRepository.findById(fNo).orElseThrow(()-> new BusinessLogicException(BusinessError.FOLDER_NOT_FOUND)))
                        .user(userRepository.findByUsername(username).orElseThrow(()-> new BusinessLogicException(BusinessError.USER_NOT_FOUND)))
                        .build());

        study.study(studyRequestDto.getStudyTimeSeconds());

        studyRepository.save(study);
        return new ResponseDto<>(200, "공부 시간 저장 완료");
    }
}
