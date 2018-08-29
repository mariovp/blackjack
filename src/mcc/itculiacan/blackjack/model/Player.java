package mcc.itculiacan.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private List<Card> hand = new ArrayList<>();
    public PlayerStatus status = PlayerStatus.PLAYING;

    private double bet = 0.0;
    private double totalMoney = 100;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHandPoints() {

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

    public int getHandLength() {
        return hand.size();
    }

    public void giveCard(Card card) {
        hand.add(card);
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public boolean placeBet(double bet) {

        if (bet <= totalMoney) {
            this.bet = bet;
            this.totalMoney -= bet;
            return true;
        } else {
            System.out.println("No tienes suficiente dinero para apostar esta cantidad");
            return false;
        }

    }

    public void notifyBetWon() {
        this.totalMoney += (bet*2);
        this.bet = 0.0;
    }

    public void notifyBetBack() {
        this.totalMoney += bet;
        this.bet = 0.0;
    }

    public void notifyBetLost() {
        this.bet = 0.0;
    }

    public void printInfo() {
        System.out.println("\nJugador: " + name + "\nPuntos: " + getHandPoints()+"\nCartas: ");

        for (Card card :
                hand) {
            System.out.println("\t- "+card.getName());
        }
    }

}
