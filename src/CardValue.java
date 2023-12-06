import java.util.Locale;

public enum CardValue {
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    J,
    Q,
    K,
    A,
    TWO,
    W,
    w,
    ;



    @Override
    public String toString() {
        if(ordinal() >= 8 && ordinal() <= 11 || (ordinal() >= 13)){
            return name().toLowerCase(Locale.ROOT);
        }else if(this == TWO){
            return String.valueOf(ordinal() - 10);
        }else{
            return String.valueOf(ordinal()+3);
        }
    }

    public static CardValue parse(String s){
        try {
            int i = Integer.parseInt(s);
            return switch(i){
                case 1 -> A;
                case 2 -> TWO;
                case 3 -> THREE;
                case 4 -> FOUR;
                case 5 -> FIVE;
                case 6 -> SIX;
                case 7 -> SEVEN;
                case 8 -> EIGHT;
                case 9 -> NINE;
                case 10 -> TEN;
                case 11 -> J;
                case 12 -> Q;
                case 13 -> K;
                default -> throw new RuntimeException("?");
            };

        } catch (NumberFormatException ignored) {

        }

        return switch (s) {
            case "small_joker" -> w;
            case "big_joker" -> W;
            default -> throw new RuntimeException("?");
        };
    }

}

