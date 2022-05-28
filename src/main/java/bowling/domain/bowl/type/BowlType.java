package bowling.domain.bowl.type;

import bowling.domain.bowl.*;

import java.util.Arrays;

public enum BowlType {
    GUTTER(Gutter::checkType),
    MISS(Miss::checkType),
    SPARE(Spare::checkType),
    STRIKE(Strike::checkType),
    FIRST(First::checkType),
    READY(Ready::checkType);

    private final Condition condition;

    BowlType(Condition condition) {
        this.condition = condition;
    }

    public static BowlType getType(BowlTypeDto dto) {
        return Arrays.stream(BowlType.values())
                .filter(type -> type.condition.check(dto))
                .findAny()
                .orElseThrow(()->new IllegalArgumentException(dto+": bowl type을 구할 수 없습니다."));
    }
}