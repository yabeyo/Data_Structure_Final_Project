import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public record Cards(Suit suit, CardValue value) {

    @Override
    public String toString() {
        return value.toString();
    }

    public static ArrayList<Cards> generate(){
        ArrayList<Cards> Deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (CardValue value : CardValue.values()) {
                if(value != CardValue.BIG_JOKER && value != CardValue.SMALL_JOKER)
                    Deck.add(new Cards(suit, value));
            }
        }
        Deck.add(new Cards(null, CardValue.BIG_JOKER));
        Deck.add(new Cards(null, CardValue.SMALL_JOKER));
        return Deck;
    }

    public int getIntCardValue(){
        switch (value){
            case ACE -> 
        }
    }
}
