package main;

public record Ability(String name, main.Ability.Trigger trigger, String effect, int anz) {

    @Override
    public String toString() {
        return trigger +
                ": " + effect;
    }

    public enum Trigger {
        ATTACK, PERMANENTMYTURN, PERMANENT, PLAY, DIE, NOTHING
    }
}
