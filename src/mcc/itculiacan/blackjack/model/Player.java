package mcc.itculiacan.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private List<Card> hand = new ArrayList<>();
    public PlayerStatus status = PlayerStatus.PLAYING;

    // Cantidad apostada en el juego actual
    private double bet = 0.0;

    // Cantidad total de dinero que tiene para apostar
    private double totalMoney = 100;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHandPoints() {

        // Calcula los puntos que tiene la mano del jugador

        int value = 0;
        boolean hasAs = false;

        for(Card card: hand) {
            if (card == Card.ClubsA || card == Card.DiamondsA || card == Card.HearthsA || card == Card.SpadesA)
                hasAs = true;
            value += card.getValue();
        }

        /* Si el jugador tiene al menos un AS y si al sumar 10 a sus puntos no se superan los 21, entonces se suman 10
        *  Esto es para representar el doble valor del AS que es 1 u 11
         * El valor default del AS es 1, al sumar 10 se convierte en 11 */
        if ( hasAs && (value+10) <= 21)
            value += 10;

        return value;
    }

    public int getHandLength() {
        // Regresa la cantidad de cartas que tiene el jugador en la mano
        return hand.size();
    }

    public void giveCard(Card card) {
        // Entrega carta al jugador
        hand.add(card);
    }

    public void reset() {
        // Restablece la mano y el estatus del jugador para volver a jugar
        hand.clear();
        status = PlayerStatus.PLAYING;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public boolean placeBet(double bet) {

        // Valida si el dinero alcanza y después hace la apuesta

        boolean isBetValid;

        if (bet <= totalMoney) {
            this.bet = bet;
            this.totalMoney -= bet;
            isBetValid = true;
        } else {
            System.out.println("No tienes suficiente dinero para apostar esta cantidad");
            isBetValid = false;
        }

        return isBetValid;
    }

    public void notifyBetWon() {
        // Notifica al jugador que ganó la apuesta, se suma el doble de la apuesta a su total y se restablece la apuesta.
        this.totalMoney += (bet*2);
        this.bet = 0.0;
    }

    public void notifyBetBack() {
        // Notifica que se devolvió la apuesta
        this.totalMoney += bet;
        this.bet = 0.0;
    }

    public void notifyBetLost() {
        // Notifica que perdió la apuesta
        this.bet = 0.0;
    }

    public void printInfo() {
        // Imprime la información del jugador

        System.out.println("\nJugador: " + name + "\nPuntos: " + getHandPoints()+"\nCartas: ");

        for (Card card :
                hand) {
            System.out.println("\t- "+card.getName());
        }
    }

}
