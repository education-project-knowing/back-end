package education.knowing.service;

import education.knowing.dto.ResponseDto;
import education.knowing.dto.folder.request.FolderRequestDto;
import education.knowing.dto.folder.response.FolderResponseDto;
import education.knowing.entity.Folder;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import education.knowing.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;

    @Transactional(readOnly = true)
    public List<FolderResponseDto> getFolderList(String username){
        if(username.equals("anonymousUser")){
            return folderRepository.findAllWithQuestionCount();
        } else{
            return folderRepository.findAllWithQuestionCountAndRecognizeCount(username);
        }
    }

    public FolderResponseDto createFolder(FolderRequestDto folderRequestDto){
        Folder folder = Folder.builder()
                .title(folderRequestDto.getTitle())
                .intro(folderRequestDto.getIntro())
                .isPublic(true)
                .build();

        Folder result = folderRepository.save(folder);

        FolderResponseDto folderResponseDto = new FolderResponseDto();
        folderResponseDto.setFno(result.getFNo());

        return folderResponseDto;
    }

    public void updateFolder(Long fNo, String username, FolderRequestDto folderRequestDto){
        Folder folder = folderRepository.findById(fNo)
                .orElseThrow(()-> new BusinessLogicException(BusinessError.FOLDER_NOT_FOUND));

        if(!folder.getCreatedBy().equals(username)){
            throw new BusinessLogicException(BusinessError.NOT_FOLDER_CREATOR);
        }

        folder.updateInfo(folderRequestDto.getTitle(),
                folderRequestDto.getIntro(), true);

        folderRepository.save(folder);
    }

    public void deleteFolder(Long fNo, String username){
        Folder folder = folderRepository.findById(fNo)
                .orElseThrow(()-> new BusinessLogicException(BusinessError.FOLDER_NOT_FOUND));

        if(!folder.getCreatedBy().equals(username)){
            throw new BusinessLogicException(BusinessError.NOT_FOLDER_CREATOR);
        }

        folderRepository.delete(folder);
    }
    public void deleteFolderByAdmin(Long fNo){
        Folder folder = folderRepository.findById(fNo)
                .orElseThrow(()-> new BusinessLogicException(BusinessError.FOLDER_NOT_FOUND));

        folderRepository.delete(folder);
    }
}
