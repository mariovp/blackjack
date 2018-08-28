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

    private void play() {
        for(int i = 0; i<2; i++) {
            for (Player player : playerList) {
                player.status = croupier.validatePlayerStatus(player);
                if (player.status == PlayerStatus.WIN) {
                    showWinner(player);
                } else if (player.status == PlayerStatus.LOST) {
                    showLoser(player);
                } else {

                    if (askIfWantsCard(player)) {
                        croupier.giveNextCard(player);
                    }

                    player.printInfo();

                }
            }
        }

    }

    private boolean askIfWantsCard(Player player) {

        Scanner sc = new Scanner(System.in);

        boolean boolResponse = false;
        boolean hasValidResponse = false;
        do {

            System.out.print(player.getName()+", ¿Quiere otra carta? (Si/No): ");
            String userInput = sc.next();

            if ( userInput.matches("Si|si|yes|1") ) {
                hasValidResponse = true;
                boolResponse = true;
                System.out.println("Ha respondido Si, se entraga una carta");

            } else if ( userInput.matches("No|no|0") ) {
                hasValidResponse = true;
                boolResponse = false;
                System.out.println("Ha respondido No, se queda como está");

            } else
                System.out.println("Debe responder Si o No");

        } while (!hasValidResponse);

        return boolResponse;
    }

    private void showWinner(Player player) {
        System.out.println("Winner: "+player.getName()+"!!!!!");
    }

    private void showLoser(Player player) {
        System.out.println(" >>>>>>>>>>>>>> Player "+player.getName()+" lost :( <<<<<<<<<<<<<<<<<<<<<<");
    }

}
