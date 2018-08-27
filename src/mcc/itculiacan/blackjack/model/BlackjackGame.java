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
        Scanner scanner = new Scanner(System.in);
        int n;
        boolean isValidNumber = false;
        do {
            System.out.print("¿Cuantas personas van a jugar? " );
            n = scanner.nextInt();

            if (n > 0 && n < 7)
                isValidNumber = true;
            else
                System.out.println("El número de jugadores debe ser estar entre 1 y 6");

        } while (!isValidNumber);


        for (int i = 0; i < n; i++) {
            System.out.print("Ingresa el nombre del Jugador "+ (i+1) + ": ");
            String name = scanner.next();
            Player player = new Player(name);
            playerList.add(player);
        }

        scanner.close();

        playerList.add(croupier);

        System.out.println("Jugadores agregados: ");
        for (Player player : playerList) {
            System.out.println("\t- "+player.getName());
        }
    }

    private void dealCards() {
        System.out.println("-------------Repartir cartas----------");
        croupier.shuffleCards();
        croupier.dealCards(playerList);

        for(Player player: playerList) {
            System.out.println(">");
            player.printInfo();
        }
    }

}
