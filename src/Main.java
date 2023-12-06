import javax.swing.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String CANONICAL_XXXY = "([2-9XJQKAWw\\*])\\1{2}[2-9XJQKAWw\\*]";
    private static final String CANONICAL_NP = "[^2-9XJQKAWw\\-\\*]";
    private static final String CANONICAL_X = "[^2-9XJQKAWw\\*]";
    private static final String CANONICAL_XX = "([2-9XJQKAWw\\*])\\1{1}";
    private static final String CANONICAL_XXX = "([2-9XJQKAWw\\*])\\1{2}";
    private static final String CANONICAL_XXXX = "([2-9XJQKAWw\\*])\\1{3}";
    private static final String CANONICAL_ABCDE = "(3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)|9(?=X)|X(?=J)|J(?=Q)|Q(?=K)|K(?=A)){4,}";
    private static final String CANONICAL_ABCDE_N = "3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)|9(?=X)|X(?=J)|J(?=Q)|Q(?=K)|K(?=A)){N,}";






    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Here is the rule of this game: This game uses a 54-card pack including two jokers, red and black. The cards rank from high to low:\n" +
                "Big_joker, Small_joker, 2, A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3.\n" +
                "Suits are irrelevant. ");
        System.out.println("The landlord plays first, and may play a single card or any legal combination. Each subsequent player must either pass (play no card) or beat the previous play by playing a higher combination of the same number of cards and same type. There are just two exceptions to this: a rocket can beat any combination, and a bomb can beat any combination except a higher bomb or rocket - see definitions below. The play continues around the table for as many circuits as necessary until two consecutive players pass. The played cards are then turned face down and put aside, and the person who played the last card(s) begins again, leading any card or legal combination.\n" +
                "\n" +
                "In this game, there are thirteen types of combination that can be played:\n" +
                "Single card - ranking from three (low) up to red joker (high) as explained above\n" +
                "Pair - two cards of the same rank, from three (low) up to two (high)\n" +
                "Triplet - three cards of the same rank\n" +
                "Triplet with an attached card - a triplet with any single card added, for example 6-6-6-8. These rank according to the rank of the triplet - so for example 9-9-9-3 beats 8-8-8-A.\n" +
                "Triplet with an attached pair - a triplet with a pair added, like a full house in poker, the ranking being determined by the rank of the triplet - for example Q-Q-Q-6-6 beats 10-10-10-K-K.\n" +
                "Sequence - at least five cards of consecutive rank, from 3 up to ace - for example 8-9-10-J-Q. Twos and jokers cannot be used.\n" +
                "Sequence of pairs - at least three pairs of consecutive ranks, from 3 up to ace. Twos and jokers cannot be used. For example 10-10-J-J-Q-Q-K-K.\n" +
                "Sequence of triplets - at least two triplets of consecutive ranks from three up to ace. For example 4-4-4-5-5-5.\n" +
                "Sequence of triplets with attached cards - an extra card is added to each triplet. For example 7-7-7-8-8-8-3-6. The attached cards must be different from all the triplets and from each other. Although triplets of twos cannot be included, a two or a joker or one of each can be attached, but not both jokers.\n" +
                "Sequence of triplets with attached pairs - an extra pair is attached to each triplet. Only the triplets have to be in sequence - for example 8-8-8-9-9-9-4-4-J-J. The pairs must be different in rank from each other and from all the triplets. Although triplets of twos cannot be included, twos can be attached. Note that attached single cards and attached pairs cannot be mixed - for example 3-3-3-4-4-4-6-7-7 is not valid.\n" +
                "Bomb - four cards of the same rank. A bomb can beat everything except a rocket, and a higher ranked bomb can beat a lower ranked one.\n" +
                "Rocket - a pair of jokers. It is the highest combination and beats everything else, including bombs.\n" +
                "Quadplex set - there are two types: a quad with two single cards of different ranks attached, such as 6-6-6-6-8-9, or a quad with two pairs of different ranks attached, such as J-J-J-J-9-9-Q-Q. Twos and jokers can be attached, but you cannot use both jokers in one quadplex set. Quadplex sets are ranked according to the rank of the quad. Note that a quadplex set can only beat a lower quadplex set of the same type, and cannot beat any other type of combination. Also a quadplex set can be beaten by a bomb made of lower ranked cards.\n" +
                "Note that passing does not prevent you from playing on a future turn.");
        System.out.println("In this game, you are always the landlord, which means you always go first and always have 3 more cards than others. Have fun!");
        System.out.println("Please enter your name below: ");
        String username = input.nextLine();
            ArrayList<Cards> Deck = Cards.generate();
            for (int i = 0; i < 10; i++) {
                Collections.shuffle(Deck);
            }
            ArrayList<Cards> Deck1 = new ArrayList<>();
            ArrayList<Cards> Deck2 = new ArrayList<>();
            ArrayList<Cards> Deck3 = new ArrayList<>();

            Player NPC1 = new Player("NPC1", Deck1);
            Player NPC2 = new Player("NPC2", Deck2);
            Player Player = new Player("Player1", Deck3);

            Deck1.addAll(Deck.subList(0, 17));
            Deck2.addAll(Deck.subList(17, 34));
            Deck3.addAll(Deck.subList(34, 54));
            Deck1.sort(Comparator.comparingInt(o -> o.getCardValue()));
            Deck2.sort(Comparator.comparingInt(o -> o.getCardValue()));
            Deck3.sort(Comparator.comparingInt(o -> o.getCardValue()));
            ArrayList<String> previousCard = new ArrayList<>();
            int cnt = 1;
            while (Deck1.size() != 0 && Deck2.size() != 0 && Deck3.size() != 0) {
                System.out.println("It is your turn now, what do you want to play?");
                String playCards = input.nextLine();
                JFrame deckWindow = new JFrame();
                JPanel panel = new JPanel();
                JLabel title = new JLabel();
                title.setText("Here is your deck");
                JTextField deckTextField = new JTextField();
                deckTextField.setText();
                deckTextField.setEditable(false);
                panel.add(title);
                panel.add(deckTextField);
                deckWindow.setContentPane(panel);
                deckWindow.pack();
                deckWindow.setVisible(true);

                if(cnt == 1){
                    if(isThisPlayLegal(Deck1, playCards, previousCard.get()))
                }
            }
    }
    public static String whatCombo (String input){
        String[] currentCards = input.split(" ");
        Pattern regexPair = Pattern.compile(".*(\\w)\\1.*#.*");
        Pattern regexThree = Pattern.compile(".*(\\w)\\1\\1.*#.*");
        Pattern regexFour = Pattern.compile(".*(\\w)\\1{3}.*#.*");
        Pattern regexThreeWithTwo = Pattern.compile("((\\w)\\2\\2(\\w)\\3|(\\w)\\4(\\w)\\5\\5)#.*");
        if (currentCards.length == 1){
            return "Single";
        } else if (currentCards.length == 2){
            Matcher matcher = regexPair.matcher(input);
            if (matcher.find()){
                return "Pairs";
            }else if ((currentCards[0].equals("small_joker") || currentCards[1].equals("small_joker")) && (currentCards[0].equals("big_joker") || currentCards[1].equals("big_joker"))){
                return "Rocket";
            }else{
                return "False";
            }
        } else if (currentCards.length == 3){
            Matcher matcher1 = regexThree.matcher(input);
            if(matcher1.find()){
                return "ThreeOfAKind";
            }else{
                return "False";
            }
        } else if (currentCards.length == 4){
            Matcher matcher2 = regexFour.matcher(input);
            if(matcher2.find()){
                return "Bomb";
            }
            Pattern p = Pattern.compile(CANONICAL_XXXY);
            Matcher matcher3 = p.matcher(input);
            if(matcher3.find()){
                return "ThreeWithOne";
            }
        }
        boolean temp = true;
        for (int i = 0; i < currentCards.length; i++) {
            if(Integer.parseInt(currentCards[i+1]) - Integer.parseInt(currentCards[i]) == 1){

            }else{
                temp = false;
                break;
            }
        }
        boolean temp1 = true;
        if(currentCards.length>=5){
            if(temp){
                return "Straight";
            }else if (currentCards.length==5){
                Matcher matcher4 = regexThreeWithTwo.matcher(input);
                if(matcher4.find()){
                    return "ThreeWithTwo";
                }else{
                    return "False";
                }
            }
        }
        return "False";
    }
    public static int getInputValue(String input){
        String[] currentCards = input.split(" ");
        int temp = 0;
        for (int i = 0; i < currentCards.length; i++) {
            temp += CardValue.parse(input).ordinal();
        }
        return temp;
    }
    public static boolean isThisPlayLegal (ArrayList<Cards> playerDeck, String playInput, String previousCard) {
        if (previousCard==null){
            return true;
        } else {
            if(whatCombo(playInput).equals(whatCombo(playInput))){
                return getInputValue(playInput) > getInputValue(previousCard);
            }else if (whatCombo(playInput).equals("Bomb")){
                return !whatCombo(playInput).equals("Rocket");
            }else if (whatCombo(playInput).equals("Rocket")){
                return true;
            }
        }
        return false;
    }
}
