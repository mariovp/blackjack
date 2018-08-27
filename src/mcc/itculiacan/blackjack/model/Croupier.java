package mcc.itculiacan.blackjack.model;

import java.util.List;

public class Croupier extends Player {

    private Deck deck = new Deck();

    public Croupier(String name) {
        super(name);
    }

    public void dealCards(List<Player> playerList) {

    }

    public PlayerStatus validatePlayerStatus(Player player) {

        int playerHandValue = player.getHandValue();

        if (playerHandValue == 21)
            return PlayerStatus.WIN;
        else if (playerHandValue < 21)
            return PlayerStatus.PLAYING;
        else return PlayerStatus.LOST;
    }

}
