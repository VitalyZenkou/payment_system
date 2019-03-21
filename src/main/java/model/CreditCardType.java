package model;

public enum CreditCardType {

    VISA("visa", 1), MASTER_CARD("master cards", 2);

    private final String name;
    private final int id;

    CreditCardType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("[credit card type]: %s", name);
    }
}
