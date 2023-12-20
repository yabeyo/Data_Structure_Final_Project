import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    static ArrayList<Cards> Deck1 = new ArrayList<>();
    static ArrayList<Cards> Deck2 = new ArrayList<>();
    static ArrayList<Cards> Deck3 = new ArrayList<>();
    static ArrayList<String> previousCard = new ArrayList<>();
    static JPanel panel1;
    static JPanel panel2;
    static JPanel panel3;
    static JLabel playerTurn;
    static JLabel playerTurn1;
    static JLabel playerDeck;

    static JTextField inputTextField;
    static JTextArea textArea;
    static JScrollPane scrollPane;
    static JButton playCard;
    public static void main(String[] args) {
        System.out.println("Here is the rule of this game: This game uses a 54-card pack including two jokers, red and black. The cards rank from high to low:\n" +
                "Big_joker(W), Small_joker(w), 2, A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3.\n" +
                "Suits are irrelevant. ");
        System.out.println("Player 1 plays first, and may play a single card or any legal combination. Each subsequent player must either pass (play no card) or beat the previous play by playing a higher combination of the same number of cards and same type. There are just two exceptions to this: a rocket can beat any combination, and a bomb can beat any combination except a higher bomb or rocket - see definitions below. The play continues around the table for as many circuits as necessary until two consecutive players pass. The played cards are then turned face down and put aside, and the person who played the last card(s) begins again, leading any card or legal combination.\n" +
                "\n" +
                "In this game, there are thirteen types of combination that can be played:\n" +
                "Single card - ranking from three (low) up to red joker (high) as explained above\n" +
                "Pair - two cards of the same rank, from three (low) up to two (high)\n" +
                "Triplet - three cards of the same rank\n" +
                "Triplet with an attached pair - a triplet with a pair added, like a full house in poker, the ranking being determined by the rank of the triplet - for example Q-Q-Q-6-6 beats 10-10-10-K-K.\n" +
                "Straight - at least five cards of consecutive rank, from 3 up to ace - for example 8-9-10-J-Q. Twos and jokers cannot be used.\n" +
                "Bomb - four cards of the same rank. A bomb can beat everything except a rocket, and a higher ranked bomb can beat a lower ranked one.\n" +
                "Rocket - a pair of jokers. It is the highest combination and beats everything else, including bombs.\n" +
                "Note that passing does not prevent you from playing on a future turn.");
        System.out.println("In this game, player 1 is always the landlord, which means player 1 always go first and always have 3 more cards than others. Have fun!");

        ArrayList<Cards> Deck = Cards.generate();
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(Deck);
        }

        Deck1.addAll(Deck.subList(0, 17));
        Deck2.addAll(Deck.subList(17, 34));
        Deck3.addAll(Deck.subList(34, 54));
        Deck1.sort(Comparator.comparingInt(o -> o.getCardValue()));
        Deck2.sort(Comparator.comparingInt(o -> o.getCardValue()));
        Deck3.sort(Comparator.comparingInt(o -> o.getCardValue()));

        JFrame window = new JFrame();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        playerTurn = new JLabel();
        playerTurn1 = new JLabel();
        playerDeck = new JLabel();
        inputTextField = new JTextField(10);
        textArea = new JTextArea(5, 20);
        scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        textArea.setVisible(true);
        playCard = new JButton("Play");
        String playerInputs = "";

        playerDeck.setText(Deck1.toString());
        playerTurn.setText("Player 1");
        playerTurn1.setText("Player 1");


        panel1.add(playerTurn1);
        panel1.add(playerDeck);
        panel2.add(textArea);
        panel2.add(scrollPane);
        panel3.add(playerTurn,BorderLayout.WEST);
        panel3.add(inputTextField,BorderLayout.CENTER);
        panel3.add(playCard,BorderLayout.EAST);

        window.add(panel1,BorderLayout.NORTH);
        window.add(panel2,BorderLayout.CENTER);
        window.add(panel3,BorderLayout.SOUTH);

        window.pack();
        window.setVisible(true);
        playCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Play")){
                    playTurn();
                }
            }
        });
    }

    private static int cnt = 1;
    private static int passCnt = 0;
    public static void playTurn () {
        String playCards = inputTextField.getText();
        String[] playCardsArray = playCards.split(" ");
        if (cnt == 4) {
            cnt -= 3;
        }
        if(passCnt==2){
            previousCard.clear();
            passCnt=0;
        }
        if (cnt == 1) {
            playerDeck.setText(Deck2.toString());
            playerTurn.setText("Player 2");
            playerTurn1.setText("Player 2");
            if (playCards.equals("pass")) {
                passCnt++;
                cnt++;
            } else if (isThisPlayLegal(Deck1, playCards, getPrevious(previousCard))) {
                cnt++;
                previousCard.add(playCards);
                textArea.append("Player 1: ");
                textArea.append(playCards+"\n");
                for (String s : playCardsArray) {
                    for (int j = 0; j < Deck1.size(); j++) {
                        if (CardValue.parse(s) == Deck1.get(j).value()) {
                            Deck1.remove(j);
                            break;
                        }
                    }
                }
            } else {
                textArea.append("Player 1: ");
                textArea.append("Illegal Move, please be smart\n");
                playerDeck.setText(Deck1.toString());
                playerTurn.setText("Player 1");
                playerTurn1.setText("Player 1");
            }
            inputTextField.setText("");
        } else if (cnt == 2) {
            playerDeck.setText(Deck3.toString());
            playerTurn.setText("Player 3");
            playerTurn1.setText("Player 3");
            if (playCards.equals("pass")) {
                passCnt++;
                cnt++;
            }else if (isThisPlayLegal(Deck2, playCards, getPrevious(previousCard))) {
                cnt++;
                previousCard.add(playCards);
                textArea.append("Player 2: ");
                textArea.append(playCards+"\n");
                for (String s : playCardsArray) {
                    for (int j = 0; j < Deck2.size(); j++) {
                        if (CardValue.parse(s) == Deck2.get(j).value()) {
                            Deck2.remove(j);
                            break;
                        }
                    }
                }
            }else{
                textArea.append("Player 2: ");
                textArea.append("Illegal Move, please be smart\n");
                playerDeck.setText(Deck2.toString());
                playerTurn.setText("Player 2");
                playerTurn1.setText("Player 2");
            }
            inputTextField.setText("");
        } else if (cnt == 3) {
            playerDeck.setText(Deck1.toString());
            playerTurn.setText("Player 1");
            playerTurn1.setText("Player 1");
            if (playCards.equals("pass")) {
                passCnt++;
                cnt++;
            } else if (isThisPlayLegal(Deck3, playCards, getPrevious(previousCard))) {
                cnt++;
                previousCard.add(playCards);
                textArea.append("Player 3: ");
                textArea.append(playCards+"\n");
                for (String s : playCardsArray) {
                    for (int j = 0; j < Deck3.size(); j++) {
                        if (CardValue.parse(s) == Deck3.get(j).value()) {
                            Deck3.remove(j);
                            break;
                        }
                    }
                }
            } else {
                textArea.append("Player 3: ");
                textArea.append("Illegal Move, please be smart\n");
                playerDeck.setText(Deck3.toString());
                playerTurn.setText("Player 3");
                playerTurn1.setText("Player 3");
            }
            inputTextField.setText("");
        }
        if (Deck1.isEmpty()) {
            textArea.setText("Winner is Player 1");
        }
        if (Deck2.isEmpty()) {
            textArea.setText("Winner is Player 2");
        }
        if (Deck3.isEmpty()) {
            textArea.setText("Winner is Player 3");
        }
    }
    public static String getPrevious(ArrayList<String> deck){
        if(deck.isEmpty()){
            return null;
        }
        return deck.get(deck.size()-1);
    }
    public static String whatCombo (String input){
        String[] currentCards = input.split(" ");
        String inputWithoutSpace = input.replaceAll(" ","");
        Pattern regexPair = Pattern.compile("([2-9xjqka])\\1");
        Pattern regexThree = Pattern.compile("([2-9xjqka])\\1\\1");
        Pattern regexFour = Pattern.compile("([2-9xjqka])\\1\\1\\1");
        Pattern regexThreeWithOne = Pattern.compile("([2-9xjqka])\\1\\1([2-9xjqka])");
        Pattern regexThreeWithTwo = Pattern.compile("([2-9xjqka])\\1\\1([2-9xjqka])\\2");
        if (currentCards.length == 1){
            return "Single";
        } else if (currentCards.length == 2){
            Matcher matcher = regexPair.matcher(inputWithoutSpace);
            if (matcher.find()){
                return "Pairs";
            }else if ((currentCards[0].equals("w") || currentCards[1].equals("w")) && (currentCards[0].equals("W") || currentCards[1].equals("W"))){
                return "Rocket";
            }else{
                return "False";
            }
        } else if (currentCards.length == 3){
            Matcher matcher1 = regexThree.matcher(inputWithoutSpace);
            if(matcher1.find()){
                return "ThreeOfAKind";
            }else{
                return "False";
            }
        } else if (currentCards.length == 4){
            Matcher matcher2 = regexFour.matcher(inputWithoutSpace);
            Matcher matcherTemp = regexThreeWithOne.matcher(inputWithoutSpace);
            if(matcher2.find()){
                return "Bomb";
            }else if(matcherTemp.find()){
                return "ThreeWithOne";
            }
        }
        boolean temp = true;
        for (int i = 0; i < currentCards.length-1; i++) {
            if(CardValue.parse(currentCards[i+1]).ordinal() - CardValue.parse(currentCards[i]).ordinal() == 1){

            }else{
                temp = false;
                break;
            }
        }
        if(currentCards.length>=5){
            if(temp){
                return "Straight";
            }else if (currentCards.length==5){
                Matcher matcher4 = regexThreeWithTwo.matcher(inputWithoutSpace);
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
        String inputWithoutSpace = input.replaceAll(" ", "");
        int temp = 0;
        for (String currentCard : currentCards) {
            temp += CardValue.parse(currentCard).ordinal();
        }
        if (currentCards.length<=3) {
            return temp;
        }else{
            if (whatCombo(inputWithoutSpace).equals("ThreeWithOne") || whatCombo(inputWithoutSpace).equals("ThreeWithTwo") || whatCombo(inputWithoutSpace).equals("Straight")){
                return  CardValue.parse(currentCards[0]).ordinal();
            } else if (whatCombo(inputWithoutSpace).equals("Bomb")){
                return temp;
            }
        }
        return temp;
    }
    public static boolean isThisPlayLegal (ArrayList<Cards> playerDeck, String playInput, String previousCard) {
        if (whatCombo(playInput).equals("False")) {
            return false;
        }
        if (previousCard == null) {
            return true;
        }
        String[] temp = playInput.split(" ");
        outer:
        for (String s : temp) {
            for (Cards cards : playerDeck) {
                if (CardValue.parse(s) == cards.value()) {
                    continue outer;
                }
            }
            return false;
        }

        if (whatCombo(playInput).equals(whatCombo(previousCard))) {
            return getInputValue(playInput) > getInputValue(previousCard);
        } else if (whatCombo(playInput).equals("Bomb")) {
            if (!whatCombo(previousCard).equals("Rocket")) {
                if (whatCombo(previousCard).equals("Bomb")) {
                    return getInputValue(playInput) > getInputValue(previousCard);
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else if (whatCombo(playInput).equals("Rocket")) {
            return true;
        } else{
            return false;
        }
    }
}
