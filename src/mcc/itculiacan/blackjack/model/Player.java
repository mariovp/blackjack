package mcc.itculiacan.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private List<Card> hand = new ArrayList<>();
    public PlayerStatus status = PlayerStatus.PLAYING;

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

    public void giveCard(Card card) {
        hand.add(card);
    }

    public List<String> getHandCardNames() {
        List<String> nameList = new ArrayList<>();

        for (Card card: hand) {
            nameList.add(card.getName());
        }

        return nameList;
    }

    public void printInfo() {
        System.out.println("\nJugador: " + name + "\nPuntos: " + getHandValue()+"\nCartas: ");

        for (Card card :
                hand) {
            System.out.println("\t- "+card.getName());
        }
    }

}
