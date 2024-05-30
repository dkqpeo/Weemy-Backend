package wemmy.domain.baby.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BabyType {

    PREGNANCY, PARENTING;  // 임신중, 육아중

    public static BabyType from(String type) {
        return BabyType.valueOf(type);
    }

    public static boolean isBabyType(String type) {
        List<BabyType> babyTypeStream = Arrays.stream(BabyType.values())
                .filter(babyType -> babyType.name().equals(type))
                .collect(Collectors.toList());


        return babyTypeStream.size() != 0;
    }
}
