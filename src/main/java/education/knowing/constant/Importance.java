package education.knowing.constant;

import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Importance {
    LOW,
    MEDIUM,
    HIGH;

    public static List<Importance> StringToImportance(List<String> stringList){
        List<Importance> importanceList = new ArrayList<>();
        try {
            for (String s : stringList) {
                importanceList.add(Importance.valueOf(s));
            }
        } catch (IllegalArgumentException e){
            throw new BusinessLogicException(BusinessError.IMPORTANCE_SETTING_ERROR);
        }
        return importanceList;
    }
}
