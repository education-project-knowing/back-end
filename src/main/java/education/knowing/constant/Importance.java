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
}
