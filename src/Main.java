import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        ArrayList<Cards> Deck = Cards.generate();
        System.out.println(Deck.size());
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(Deck);
        }
        System.out.println(Deck);

        ArrayList<Cards> Deck1 = new ArrayList<>();
        ArrayList<Cards> Deck2 = new ArrayList<>();
        ArrayList<Cards> Deck3 = new ArrayList<>();

        Player NPC1 = new Player("NPC1", Deck1);
        Player NPC2 = new Player("NPC2", Deck2);
        Player Player = new Player("Player1", Deck3);

        Deck1.addAll(Deck.subList(0, 17));
        Deck1.sort(Comparator.comparing());

        Deck2.addAll(Deck.subList(17, 34));
        Deck3.addAll(Deck.subList(34, 54));

        System.out.println(NPC1.deck);
        System.out.println(NPC2.deck);
        System.out.println(Player.deck);
    }
}
