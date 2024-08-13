package education.knowing.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomNicknameUtil {
    public static String getRandomNickname(){
        Random random = new Random();
        List<String> adjective = Arrays.asList("행복한", "슬픈", "게으른", "슬기로운", "수줍은",
                "그리운", "더러운", "섹시한", "배고픈", "배부른", "부자", "재벌", "웃고있는", "깨발랄한");
        List<String> animals = Arrays.asList("강아지", "고양이", "토끼", "호랑이", "사자", "돌고래", "사슴", "판다", "코끼리",
                "쥐", "뱀", "다람쥐", "용", "닭", "돼지", "소", "말", "양", "원숭이");
        String number = random.nextInt(99) + 1 + "";

        String adj = adjective.get(random.nextInt(adjective.size()));
        String animal = animals.get(random.nextInt(animals.size()));
        return adj + animal + number;
    }
}
