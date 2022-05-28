package bowling.domain.pin;

public class Pins {

    private static final int MAX_PIN_CNT = 10;
    private static final int MIN_PIN_CNT = 0;
    private static final String PIN_CNT_MESSAGE = "핀의 개수는 0~10만 가능합니다.";
    private final int count;

    public Pins(int count) {
        validate(count);
        this.count = count;
    }

    void validate(int count){
        if(count < MIN_PIN_CNT || count > MAX_PIN_CNT){
            throw new IllegalArgumentException(PIN_CNT_MESSAGE);
        }
    }

    public int getCount() {
        return count;
    }

    public boolean isStrike(){
        return count == MAX_PIN_CNT;
    }

    public static boolean isSpare(Pins first, Pins second){
        return first.count + second.count == MAX_PIN_CNT;
    }

    public static boolean isMiss(Pins first, Pins second){
        return first.count + second.count < MAX_PIN_CNT;
    }

    public static boolean isGutter(Pins first, Pins second){
        return first.count == MIN_PIN_CNT && second.count == MIN_PIN_CNT;
    }

    @Override
    public String toString(){
        return count+"개";
    }

    public String getSymbol() {
        if(count == 0){
            return "-";
        }
        return count+"";
    }
}