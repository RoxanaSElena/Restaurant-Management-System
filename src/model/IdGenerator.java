package model;

public class IdGenerator {

    public static int counter = 1;

    private IdGenerator() {

    }

    public static int getNextId() {
        return counter++;
    }
}