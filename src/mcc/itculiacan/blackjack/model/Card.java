package mcc.itculiacan.blackjack.model;

public enum Card {

    ClubsA("Trébol A", 1),
    Clubs2("Trébol 2", 2),
    Clubs3("Trébol 3", 3),
    Clubs4("Trébol 4", 4),
    Clubs5("Trébol 5", 5),
    Clubs6("Trébol 6", 6),
    Clubs7("Trébol 7", 7),
    Clubs8("Trébol 8", 8),
    Clubs9("Trébol 9", 9),
    Clubs10("Trébol 10", 10),
    ClubsJ("Trébol J", 10),
    ClubsQ("Trébol Q", 10),
    ClubsK("Trébol K", 10),

    DiamondsA("Diamantes A", 1),
    Diamonds2("Diamantes 2", 2),
    Diamonds3("Diamantes 3", 3),
    Diamonds4("Diamantes 4", 4),
    Diamonds5("Diamantes 5", 5),
    Diamonds6("Diamantes 6", 6),
    Diamonds7("Diamantes 7", 7),
    Diamonds8("Diamantes 8", 8),
    Diamonds9("Diamantes 9", 9),
    Diamonds10("Diamantes 10", 10),
    DiamondsJ("Diamantes J", 10),
    DiamondsQ("Diamantes Q", 10),
    DiamondsK("Diamantes K", 10),

    HearthsA("Corazones A", 1),
    Hearths2("Corazones 2", 2),
    Hearths3("Corazones 3", 3),
    Hearths4("Corazones 4", 4),
    Hearths5("Corazones 5", 5),
    Hearths6("Corazones 6", 6),
    Hearths7("Corazones 7", 7),
    Hearths8("Corazones 8", 8),
    Hearths9("Corazones 9", 9),
    Hearths10("Corazones 10", 10),
    HearthsJ("Corazones J", 10),
    HearthsQ("Corazones Q", 10),
    HearthsK("Corazones K", 10),

    SpadesA("Picas A", 1),
    Spades2("Picas 2", 2),
    Spades3("Picas 3", 3),
    Spades4("Picas 4", 4),
    Spades5("Picas 5", 5),
    Spades6("Picas 6", 6),
    Spades7("Picas 7", 7),
    Spades8("Picas 8", 8),
    Spades9("Picas 9", 9),
    Spades10("Picas 10", 10),
    SpadesJ("Picas J", 10),
    SpadesQ("Picas Q", 10),
    SpadesK("Picas K", 10);

    private String name;
    private int value;

    Card(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

}
