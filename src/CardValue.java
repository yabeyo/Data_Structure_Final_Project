import java.util.Locale;

public enum CardValue {
    ACE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    SMALL_JOKER,
    BIG_JOKER,
    ;
    public static int getIntValue(){
        switch
    }

    @Override
    public String toString() {
        return ordinal() <= 9 ? String.valueOf(ordinal() + 1) : name().toLowerCase(Locale.ROOT);
    }
}

