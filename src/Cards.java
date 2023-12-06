import java.util.ArrayList;
import java.util.HashMap;

public record Cards(Suit suit, CardValue value) {

    @Override
    public String toString() {
        return value.toString();
    }

    public static ArrayList<Cards> generate(){
        ArrayList<Cards> Deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (CardValue value : CardValue.values()) {
                if(value != CardValue.W && value != CardValue.w)
                    Deck.add(new Cards(suit, value));
            }
        }
        Deck.add(new Cards(null, CardValue.W));
        Deck.add(new Cards(null, CardValue.w));
        return Deck;
    }
    public int getCardValue(){
        return value.ordinal() + 1;
    }
}
