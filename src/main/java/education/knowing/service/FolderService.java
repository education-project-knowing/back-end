package education.knowing.service;

import education.knowing.dto.request.FolderRequestDto;
import education.knowing.dto.response.FolderResponseDto;
import education.knowing.dto.response.ResponseDto;
import education.knowing.entity.Folder;
import education.knowing.entity.User;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import education.knowing.repository.FolderRepository;
import education.knowing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FolderService {
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;

    @Transactional(readOnly = true)
    public List<FolderResponseDto> getFolderList(String username){
        if(username == null){
            return folderRepository.findAllWithQuestionCount();
        } else{
            return folderRepository.findAllWithQuestionCountAndRecognizeCount(username);
        }
    }

    public FolderResponseDto createFolder(FolderRequestDto folderRequestDto){
        User createBy = userRepository.findByUsername(folderRequestDto.getCreateBy())
                .orElseThrow(()-> new BusinessLogicException(BusinessError.USER_NOT_FOUND));

        Folder folder = Folder.builder()
                .title(folderRequestDto.getTitle())
                .intro(folderRequestDto.getIntro())
                .isPublic(folderRequestDto.isPublic())
                .createBy(createBy)
                .build();

        Folder result = folderRepository.save(folder);

        FolderResponseDto folderResponseDto = new FolderResponseDto();
        folderResponseDto.setFno(result.getFNo());

        return folderResponseDto;
    }

    public ResponseDto<Object> updateFolder(Long fNo, FolderRequestDto folderRequestDto){

        Folder folder = folderRepository.findByIdWithUser(fNo)
                .orElseThrow(()-> new BusinessLogicException(BusinessError.FOLDER_NOT_FOUND));

        if(!folder.getCreateBy().getUsername().equals(folderRequestDto.getCreateBy())){
            throw new BusinessLogicException(BusinessError.NOT_FOLDER_CREATOR);
        }

        folder.updateInfo(folderRequestDto.getTitle(),
                folderRequestDto.getIntro(), folderRequestDto.isPublic());

        folderRepository.save(folder);

        return new ResponseDto<>(200, "폴더 수정 완료");
    }

    public ResponseDto<?> deleteFolder(Long fNo, String username){
        Folder folder = folderRepository.findByIdWithUser(fNo)
                .orElseThrow(()-> new BusinessLogicException(BusinessError.FOLDER_NOT_FOUND));

        if(!folder.getCreateBy().getUsername().equals(username)){
            throw new BusinessLogicException(BusinessError.NOT_FOLDER_CREATOR);
        }

        folder.clearQuestionList();

        folderRepository.delete(folder);
        return new ResponseDto<>(200, "폴더 삭제 완료");
    }
}
