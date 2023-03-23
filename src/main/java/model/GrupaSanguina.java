package model;

public enum GrupaSanguina {
    O,
    A,
    B,
    AB;

    public static GrupaSanguina fromInteger(int x){
        return switch (x) {
            case 1 -> O;
            case 2 -> A;
            case 3 -> B;
            case 4 -> AB;
            default -> null;
        };
    }

    public int toInteger(){
        return switch (this){
            case O -> 1;
            case A -> 2;
            case B -> 3;
            case AB -> 4;
        };
    }
}
