package mcc.itculiacan.blackjack.model;

import java.util.List;

public class Croupier extends Player {

    private Deck deck = new Deck();

    public Croupier(String name) {
        super(name);
    }

    public void shuffleCards() {
        deck.shuffle();
    }

    public void dealCards(List<Player> playerList) {

        for (int i = 0; i < 2; i++) {
            for (Player player : playerList) {
                player.giveCard(deck.getCard());
            }
        }

    }

    public void giveNextCard(Player player) {
        Card card = deck.getCard();
        System.out.println("> " + player.getName() + " obtiene la carta " + card.getName());
        player.giveCard(card);
    }

    public PlayerStatus validatePlayerStatus(Player player) {

        int playerHandValue = player.getHandValue();

        PlayerStatus playerStatus;

        if (player instanceof Croupier) {
            playerStatus = validateCroupierStatus(playerHandValue);
        } else if (playerHandValue == 21)
            playerStatus = PlayerStatus.WON;
        else if (playerHandValue < 21)
            playerStatus = PlayerStatus.PLAYING;
        else
            playerStatus = PlayerStatus.LOST;

        return playerStatus;
    }

    private PlayerStatus validateCroupierStatus(int croupierHandValue) {

        PlayerStatus playerStatus;

        if (croupierHandValue == 21)
            playerStatus = PlayerStatus.WON;
        else if (croupierHandValue < 17)
            playerStatus = PlayerStatus.PLAYING;
        else if (croupierHandValue < 21)
            playerStatus = PlayerStatus.STAYED;
        else
            playerStatus = PlayerStatus.LOST;

        return playerStatus;
    }

}
