import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Player {
    String name;
    ArrayList<Cards> deck;

    public Player(String name, ArrayList<Cards> deck) {
        this.name = name;
        this.deck = deck;
    }
}
