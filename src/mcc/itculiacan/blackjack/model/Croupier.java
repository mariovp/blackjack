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

        // Reparte dos cartas a cada jugador de la lista

        for (int i = 0; i < 2; i++) {
            for (Player player : playerList) {
                player.giveCard(deck.getCard());
            }
        }

    }

    public void giveNextCard(Player player) {

        //Entrega una carta al jugador

        Card card = deck.getCard();
        System.out.println("\n+\t " + player.getName() + " obtiene la carta " + card.getName()+" \t+");
        player.giveCard(card);
    }

    public PlayerStatus validatePlayerStatus(Player player) {

        int playerHandPoints = player.getHandPoints();
        int playerHandLength = player.getHandLength();

        /*Si el jugador ya llegó a un estatus final (Ganó, Perdió o se Quedó) entonces no validar y regresar el mismo estatus */
        if (player.status != PlayerStatus.PLAYING) return player.status;

        /* Se implementan validaciones diferentes para Jugador croupier */
        if (player instanceof Croupier) return validateCroupierStatus(playerHandPoints, playerHandLength);

        PlayerStatus playerStatus;

        if (playerHandPoints == 21 && playerHandLength == 2)
            playerStatus = PlayerStatus.BLACKJACK;
        else if (playerHandPoints == 21)
            playerStatus = PlayerStatus.WON;
        else if (playerHandPoints < 21)
            playerStatus = PlayerStatus.PLAYING;
        else
            playerStatus = PlayerStatus.LOST;

        return playerStatus;
    }

    private PlayerStatus validateCroupierStatus(int croupierHandValue, int croupierHandLength) {

        // Valida el estatus del croupier por separado porque sus reglas son diferentes a las de un jugador normal

        PlayerStatus playerStatus;

        if (croupierHandValue == 21 && croupierHandLength == 2)
            playerStatus = PlayerStatus.BLACKJACK;
        else if (croupierHandValue == 21)
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
