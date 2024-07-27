package education.knowing.service;

import education.knowing.dto.request.OpinionRequestDto;
import education.knowing.dto.response.OpinionResponseDto;
import education.knowing.dto.response.ResponseDto;
import education.knowing.entity.Opinion;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import education.knowing.repository.OpinionRepository;
import education.knowing.util.LocalDateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OpinionService {
    private final OpinionRepository opinionRepository;

    private final long SUBMIT_LIMITED_TIME = 10L;

    @Transactional(readOnly = true)
    public List<OpinionResponseDto> getOpinionsByWriter(String username){
        List<Opinion> opinionList = opinionRepository.findOpinionsByCreatedBy(username);

        List<OpinionResponseDto> opinionResponseDtoList = new ArrayList<>();

        for (Opinion opinion : opinionList) {
            opinionResponseDtoList.add(OpinionResponseDto.from(opinion));
        }
        return opinionResponseDtoList;
    }

    @Transactional(readOnly = true)
    public List<OpinionResponseDto> getNotReadOpinions(){
        return opinionRepository.findNotReadOpinions();
    }

    public OpinionResponseDto sendOpinion(String username, OpinionRequestDto opinionRequestDto){
        Opinion current = opinionRepository.findFirstByCreatedByOrderByCreatedDate(username);

        if(current != null && LocalDateTimeUtil.checkTimeMoreThanMinutesAgo(current.getCreatedDate(), SUBMIT_LIMITED_TIME)){
            throw new BusinessLogicException(BusinessError.OPINION_SUBMIT_LIMITED_TO_10MINUTES);
        }

        Opinion opinion = Opinion
                .builder()
                .opinion(opinionRequestDto.getOpinion()).build();

        Opinion result = opinionRepository.save(opinion);

        return new OpinionResponseDto(result.getOpinionId(), result.getCreatedBy(), result.getOpinion(), result.getCreatedDate());
    }
    public ResponseDto<?> updateOpinion(Long opinionId, String username, OpinionRequestDto opinionRequestDto){
        Opinion opinion = opinionRepository.findById(opinionId)
                .orElseThrow(()-> new BusinessLogicException(BusinessError.OPINION_NOT_FOUND));

        if(opinion.getCreatedBy().equals(username)){
            throw new BusinessLogicException(BusinessError.NOT_OPINION_CREATOR);
        }

        opinion.updateOpinion(opinionRequestDto.getOpinion());

        return new ResponseDto<>(200, "사용자 의견 수정");
    }

    public ResponseDto<?> deleteOpinion(Long opinionId, String username){
        Opinion opinion = opinionRepository.findById(opinionId).orElseThrow(
                ()-> new BusinessLogicException(BusinessError.OPINION_NOT_FOUND)
        );

        if(!opinion.getCreatedBy().equals(username)){
            throw new BusinessLogicException(BusinessError.NOT_OPINION_CREATOR);
        }

        opinionRepository.deleteById(opinionId);

        return new ResponseDto<>(200, "사용자 의견 수정");
    }
}
