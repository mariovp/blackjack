package mcc.itculiacan.blackjack.model;

import java.util.*;

public class Deck {

    // Lista base que contiene todas las cartas
    private List<Card> cards = Arrays.asList(Card.values());

    // Estructura de cola doble que simula el mazo de cartas
    private Deque<Card> cardDeque = new ArrayDeque<>();

    public void shuffle() {
        // Revolver cartas base
        Collections.shuffle(cards);

        // Eliminar todas las cartas del mazo
        cardDeque.clear();

        // Agrega las cartas revueltas al mazo
        cardDeque.addAll(cards);
    }

    public Card getCard() {
        // Regresa la carta de arriba del mazo
        return cardDeque.pop();
    }

}
