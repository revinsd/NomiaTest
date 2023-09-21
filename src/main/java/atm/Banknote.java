package atm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Banknote {
    TEN(0, 10),
    FIFTY(1, 50),
    ONE_HUNDRED(2, 100),
    TWO_HUNDRED(3, 200),
    FIVE_HUNDRED(4, 500);

    private final int code;
    private final int value;

    private static final Banknote[] sortedDesc;

    static {
        sortedDesc = Arrays.stream(values())
                .sorted((b1, b2) -> Integer.compare(b2.getCode(), b1.getCode()))
                .toArray(Banknote[]::new);
    }

    public static Banknote[] valuesSortedDesc(){
        return sortedDesc;
    }
}
