import java.util.Locale;

public enum CardValue {
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    X,
    J,
    Q,
    K,
    A,
    TWO,
    w,
    W,
    ;



    @Override
    public String toString() {
        if(ordinal() >= 8 && ordinal() <= 11 || (ordinal() >= 13)){
            return name();
        }else if(this == TWO){
            return String.valueOf(ordinal() - 10);
        }else{
            return String.valueOf(ordinal()+3);
        }
    }

    public static CardValue parse(String s){
            return switch(s){
                case "a" -> A;
                case "2" -> TWO;
                case "3" -> THREE;
                case "4" -> FOUR;
                case "5" -> FIVE;
                case "6" -> SIX;
                case "7" -> SEVEN;
                case "8" -> EIGHT;
                case "9" -> NINE;
                case "x" -> X;
                case "j" -> J;
                case "q" -> Q;
                case "k" -> K;
                case "w" -> w;
                case "W" -> W;
                default -> null;
            };
    }

}

