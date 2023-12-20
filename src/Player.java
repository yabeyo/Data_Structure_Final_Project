import java.util.ArrayList;

public class Player {
    String name;
    ArrayList<Cards> deck;

    public Player(String name, ArrayList<Cards> deck) {
        this.name = name;
        this.deck = deck;
    }
}
