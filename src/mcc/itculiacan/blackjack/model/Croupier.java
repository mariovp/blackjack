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
        System.out.println("\n+\t " + player.getName() + " obtiene la carta " + card.getName()+" \t+");
        player.giveCard(card);
    }

    public PlayerStatus validatePlayerStatus(Player player) {

        int playerHandValue = player.getHandPoints();

        /*Si el jugador ya llegó a un estatus final (Ganó, Perdió o se Quedó) entonces no validar y regresar el mismo estatus */
        if (player.status != PlayerStatus.PLAYING) return player.status;

        /* Se implementan validaciones diferentes para Jugador croupier */
        if (player instanceof Croupier) return validateCroupierStatus(playerHandValue);

        PlayerStatus playerStatus;

        if (playerHandValue == 21 && player.getHandLength() == 2)
            playerStatus = PlayerStatus.BLACKJACK;
        else if (playerHandValue == 21)
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
