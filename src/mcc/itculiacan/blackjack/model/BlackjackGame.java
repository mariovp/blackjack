package mcc.itculiacan.blackjack.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame {

    private Croupier croupier = new Croupier("Blackjack Master");
    private List<Player> playerList = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);

    public void startGame() {
        // Inicia el juego
        System.out.println("----------------Programa iniciado--------------");
        addPlayers();
        play();

        // Pregunta si quiere jugar de nuevo
        while(askPlayAgain()) {
            resetAllPlayers();
            play();
        }
    }

    private void addPlayers() {
        int n;
        boolean isValidNumber = false;

        // Pregunta y valida la cantidad de jugadores
        do {
            System.out.print("¿Cuantas personas van a jugar? " );
            n = scanner.nextInt();

            if (n > 0 && n < 7)
                isValidNumber = true;
            else
                System.out.println("El número de jugadores debe ser estar entre 1 y 6");

        } while (!isValidNumber);

        System.out.println();

        // Pregunta el nombre de cada jugador
        for (int i = 0; i < n; i++) {
            System.out.print("Ingresa el nombre del Jugador "+ (i+1) + ": ");
            String name = scanner.next();
            Player player = new Player(name);
            playerList.add(player);
        }

        // Muestra la lista de jugadores agregados
        System.out.println("\nJugadores agregados: ");
        for (Player player : playerList) {
            System.out.println("\t- "+player.getName());
        }
    }

    private void askBetAmount(Player player) {

        // Pregunta al jugador la cantidad que desea apostar, repite la pregunta si la apuesta no es válida

        boolean isBetCorrect = false;
        while (!isBetCorrect) {
            System.out.print("\n"+player.getName()+" ($ "+player.getTotalMoney()+"), ¿Cuanto quiere apostar?: ");

            double betAmount = scanner.nextDouble();

            isBetCorrect = player.placeBet(betAmount);
        }
    }

    private void dealCards() {
        System.out.println("\n>>\tRepartir cartas\t<<");

        // Barajea cartas y las reparte a los jugadores
        croupier.shuffleCards();
        croupier.dealCards(playerList);

        // Imprime información de cada jugador, incluyendo su mano
        for(Player player: playerList) {
            player.printInfo();
        }

        // Croupier agarra una carta y muestra su información
        croupier.giveNextCard(croupier);
        croupier.printInfo();
    }

    private void play() {

        // Método principal del juego

        // Pregunta a cada jugador la cantidad que desea apostar
        for (Player player : playerList) {
            askBetAmount(player);
        }

        // Reparte cartas
        dealCards();

        for (Player player : playerList) {

            // Mientras el jugador siga jugando
            while (player.status == PlayerStatus.PLAYING) {

                /* Valida el estatus del jugador y muestra si hay cambios de estatus
                *  Se valida antes de jugar por si tiene BlackJack desde el principio*/
                player.status = croupier.validatePlayerStatus(player);
                showIfStatusChanged(player);

                // Si el jugador sigue jugando y quiere carta, se el entrega una
                if (player.status == PlayerStatus.PLAYING && askIfWantsCard(player))
                    croupier.giveNextCard(player);
            }
        }

        // Juega el croupier
        while(croupier.status == PlayerStatus.PLAYING) {
            playCroupierTurn(croupier);
            croupier.status = croupier.validatePlayerStatus(croupier);
        }

        // Imprime información del croupier cuando termina de jugar
        croupier.printInfo();

        // Calcula y muestra resultados del juego
        showGameResults();
    }

    private boolean askIfWantsCard(Player player) {

        // Pregunta al jugador si desea una carta o se queda como está

        boolean boolResponse = false;
        boolean hasValidResponse = false;
        do {

            // Muestra el nombre del jugador, los puntos que tiene y hace la pregunta
            System.out.print("\n"+player.getName()+" ("+player.getHandPoints()+" pts), ¿Quiere otra carta? (Si/No): ");
            String userInput = scanner.next();

            // Filtra las respuestas de si
            if ( userInput.matches("Si|si|yes|1") ) {
                hasValidResponse = true;
                boolResponse = true;
                System.out.println("Ha respondido Si, se entraga una carta");

            } else if ( userInput.matches("No|no|0") ) { // Filtra las respuesta de No
                hasValidResponse = true;
                boolResponse = false;
                player.status = PlayerStatus.STAYED;
                System.out.println("Ha respondido No, se queda como está");

            } else
                System.out.println("Debe responder Si o No"); // Regaña al jugador si la respuesta no cae en los filtros

        } while (!hasValidResponse); // Repite la pregunta mientras no tenga una respuesta válida

        return boolResponse;
    }

    private void playCroupierTurn(Croupier croupierPlayer) {

        // Simula comportamiento del Croupier jugando

        /* El croupier no puede pedir más cartas si ya tiene 17 puntos */
        if (croupierPlayer.getHandPoints() < 17) {
            croupier.giveNextCard(croupierPlayer);
            try {
                // Agrega un delay al sacar cartas para hacerlo más emocionante
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showGameResults() {

        // Calcula resultados del juego y los muestra

        // Template para mostrar resultados del juego utilizando String.format()
        String template = "%1$s (%2$d pts) vs %3$s (%4$d pts): %5$s";

        // Lista de jugadores que se quedaron sin dinero
        List<Player> playersWithoutMoney = new ArrayList<>();

        int croupierScore = croupier.getHandPoints();
        String croupierName = croupier.getName();

        System.out.println();

        for (Player player : playerList) {

            int playerScore = player.getHandPoints();
            String playerName = player.getName();

            // Se obtiene resultado
            GameWinner gameWinner = determineGameResult(croupier, player);

            String gameResultString = "";

            // Se genera string de resultado de juego
            switch (gameWinner) {
                case CROUPIER: {
                    player.notifyBetLost();
                    gameResultString = "Gana "+croupierName;
                }
                break;
                case PLAYER: {
                    player.notifyBetWon();
                    gameResultString = "Gana " + playerName;
                }
                break;
                case DRAW: {
                    player.notifyBetBack();
                    gameResultString = "Empate";
                }
                break;
            }

            // Se arma el string con toda la información de nombre de jugadores, puntos y resultado del juego
            String message = String.format(template, croupierName, croupierScore, playerName, playerScore, gameResultString);
            System.out.println(message);

            // Muestra el dinero que le queda al jugador
            System.out.println(playerName+" tiene $ "+player.getTotalMoney()+"\n");

            // Si el jugador ya no tiene dinero, entonces se agrega la lista de jugadores sin dinero y se muestra mensaje
            if (player.getTotalMoney() == 0.0) {
                playersWithoutMoney.add(player);
                System.out.println(playerName+" se quedó sin dinero, queda fuera del juego\n");
            }

        }

        /* Se eliminan todos los jugadores que se quedaron sin dinero al final del juego actual
         * Se ejecuta fuera del loop porque no se puede modificar una collection mientras se está iterando */
        playerList.removeAll(playersWithoutMoney);
    }

    private GameWinner determineGameResult(Croupier croupier, Player player) {

        // Determina al ganador del juego y lo regresa en un enum de tipo GameWinner

        int croupierScore = croupier.getHandPoints();
        int playerScore = player.getHandPoints();

        GameWinner gameWinner;

        if (croupier.status == PlayerStatus.LOST) {

            if (player.status == PlayerStatus.LOST)
                gameWinner = GameWinner.CROUPIER;
            else
                gameWinner = GameWinner.PLAYER;

        } else if (player.status == PlayerStatus.LOST)
            gameWinner = GameWinner.CROUPIER;
        else if (croupierScore > playerScore)
            gameWinner = GameWinner.CROUPIER;
        else if (croupierScore < playerScore)
            gameWinner = GameWinner.PLAYER;
        else
            gameWinner = GameWinner.DRAW;

        return gameWinner;
    }

    private void showIfStatusChanged(Player player) {

        // Muestra el estatus del jugador si ya no se encuentra jugando (PLAYING)

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

    private boolean askPlayAgain() {

        // Pregunta al usuario si desea jugar de nuevo y regresa la respuesta como un booleano

        boolean boolResponse = false;
        boolean hasValidResponse = false;
        do {

            System.out.print("\n¿Desea jugar de nuevo? (Si/No): ");
            String userInput = scanner.next();

            if ( userInput.matches("Si|si|yes|1") ) {
                hasValidResponse = true;
                boolResponse = true;
                System.out.println("Comienza nuevo juego");

            } else if ( userInput.matches("No|no|0") ) {
                hasValidResponse = true;
                boolResponse = false;
                System.out.println("Fin del juego.");

            } else
                System.out.println("Debe responder Si o No");

        } while (!hasValidResponse);

        return boolResponse;
    }

    private void resetAllPlayers() {

        // Restablece el estatus del croupier y todos los jugadores para comenzar otro juego

        croupier.reset();
        for (Player player: playerList)
            player.reset();

    }

}
