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
        play();
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
            player.printInfo();
        }

        croupier.giveNextCard(croupier);
        croupier.printInfo();
    }

    private void play() {
        for (Player player : playerList) {



            while (player.status == PlayerStatus.PLAYING) {

                player.status = croupier.validatePlayerStatus(player);
                showIfStatusChanged(player);

                if (player.status == PlayerStatus.PLAYING && askIfWantsCard(player))
                    croupier.giveNextCard(player);

            }

        }

        while(croupier.status == PlayerStatus.PLAYING) {
            playCroupierTurn(croupier);
            croupier.status = croupier.validatePlayerStatus(croupier);
        }

        croupier.printInfo();

        showGameResults();
    }

    private boolean askIfWantsCard(Player player) {

        Scanner sc = new Scanner(System.in);

        boolean boolResponse = false;
        boolean hasValidResponse = false;
        do {

            System.out.print("\n"+player.getName()+" ("+player.getHandPoints()+" pts), ¿Quiere otra carta? (Si/No): ");
            String userInput = sc.next();

            if ( userInput.matches("Si|si|yes|1") ) {
                hasValidResponse = true;
                boolResponse = true;
                System.out.println("Ha respondido Si, se entraga una carta");

            } else if ( userInput.matches("No|no|0") ) {
                hasValidResponse = true;
                boolResponse = false;
                player.status = PlayerStatus.STAYED;
                System.out.println("Ha respondido No, se queda como está");

            } else
                System.out.println("Debe responder Si o No");

        } while (!hasValidResponse);

        return boolResponse;
    }

    private void playCroupierTurn(Croupier croupierPlayer) {

        /* El croupier no puede pedir más cartas si ya tiene 17 puntos */
        if (croupierPlayer.getHandPoints() < 17) {
            croupier.giveNextCard(croupierPlayer);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showGameResults() {
        int croupierScore = croupier.getHandPoints();
        String croupierName = croupier.getName();

        System.out.println();

        for (Player player : playerList) {
            String template = "%1$s (%2$d pts) vs %3$s (%4$d pts): %5$s";
            int playerScore = player.getHandPoints();
            String playerName = player.getName();

            String gameResult;

            if (croupier.status == PlayerStatus.LOST) {
                if (player.status == PlayerStatus.LOST)
                    gameResult = "Gana "+croupierName+" (Croupier) :(";
                else
                    gameResult = "Gana " + playerName;

            } else if (player.status == PlayerStatus.LOST) {
                gameResult = "Gana "+croupierName+" (Croupier) :(";

            } else if (croupierScore > playerScore)
                gameResult = "Gana "+croupierName+" (Croupier) :(";

            else if (croupierScore < playerScore)
                gameResult = "Gana "+playerName;

            else
                gameResult = "Empate";

            String message = String.format(template, croupierName, croupierScore, playerName, playerScore, gameResult);

            System.out.println(message);
        }
    }

    private void showIfStatusChanged(Player player) {

        switch (player.status) {
            case BLACKJACK: showPlayerHasBlackjack(player);
                break;
            case WON: showPlayerWon(player);
                break;
            case LOST: showPlayerLost(player);
                break;
            case STAYED:
                showPlayerStayed(player);
                break;
        }

    }

    private void showPlayerHasBlackjack(Player player) {
        System.out.println("\n> \t"+player.getName()+" tiene Blackjack! \t<");
    }

    private void showPlayerWon(Player player) {
        System.out.println("\n> \t"+player.getName()+" llegó a 21 puntos (Gana) \t<");
    }

    private void showPlayerLost(Player player) {
        System.out.println("\n> \t"+player.getName()+" perdió con "+player.getHandPoints()+" puntos \t<");
    }

    private void showPlayerStayed(Player player) {
        System.out.println("\n> \t"+player.getName()+" se quedó con "+player.getHandPoints()+" puntos \t<");
    }

}
