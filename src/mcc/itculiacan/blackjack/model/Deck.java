package mcc.itculiacan.blackjack.model;

import java.util.*;

public class Deck {

    private List<Card> cards = new ArrayList<>(Arrays.asList(Card.values()));
    private Deque<Card> cardDeque = new ArrayDeque<>();

    public void shuffle() {
        Collections.shuffle(cards);
        cardDeque.clear();
        cardDeque.addAll(cards);
    }

    public Card getCard() {
        return cardDeque.pop();
    }

}
