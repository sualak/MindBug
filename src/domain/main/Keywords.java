package main;

public record Keywords(String name, String effect) {

    @Override
    public String toString() {
        return name;
    }
}
