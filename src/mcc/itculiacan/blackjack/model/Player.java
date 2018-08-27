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
        boolean hasAs = false;

        for(Card card: hand) {
            if (card == Card.ClubsA || card == Card.DiamondsA || card == Card.HearthsA || card == Card.SpadesA)
                hasAs = true;
            value += card.getValue();
        }

        if ( hasAs && (value+10) <= 21)
            value += 10;

        return value;
    }

}
