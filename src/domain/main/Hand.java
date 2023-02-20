package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Hand
{
    private final List<Karte> hand = new ArrayList<>();

    public void addKarte(Karte karte)
    {
        hand.add(karte);
    }

    public Karte spielen(int index)
    {
        Karte played =  hand.remove(index);
        hand.sort(Comparator.comparing(Karte::getName));
        return played;
    }

    public Karte discard(int index)
    {
        return hand.remove(index);
    }

    public void printHand()
    {
        hand.forEach(karte -> System.out.println(karte.toString()));;
    }

    public List<Karte> getHand() {
        return Collections.unmodifiableList(hand);
    }
}
