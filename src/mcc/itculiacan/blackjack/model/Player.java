package mcc.itculiacan.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private List<Card> hand = new ArrayList<>();
    private PlayerStatus playerStatus = PlayerStatus.PLAYING;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHandValue() {

        int value = 0;

        for(Card card: hand) {
            value += card.getValue();
        }

        return value;
    }

}
