package mcc.itculiacan.blackjack.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame {

    private Croupier croupier = new Croupier("Blackjack Master");
    private List<Player> playerList = new ArrayList<>();

    public void startGame() {
        System.out.println("----------------Programa iniciado--------------");
        addPlayers();
        dealCards();
    }

    private void addPlayers() {
        System.out.println("¿Cuantas personas van a jugar?");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Ingresa el nombre del Jugador "+ (i+1) + ": ");
            String name = scanner.next();
            Player player = new Player(name);
            playerList.add(player);
        }

        playerList.add(croupier);

        System.out.println("Jugadores agregados: ");
        for (Player player : playerList) {
            System.out.println(player.getName());
        }
    }

    private void dealCards() {
        System.out.println("-------------Repartir cartas----------");
        croupier.shuffleCards();
        croupier.dealCards(playerList);

        for(Player player: playerList) {
            System.out.println(player.getName() + ": " + player.getHandValue());
            System.out.println("Cartas: ");
            for(String cardName: player.getHandCardNames()) {
                System.out.println("\t"+cardName);
            }
        }
    }

}
