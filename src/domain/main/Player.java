package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player
{
    private int hp = 3;
    private String name;
    private final Hand hand = new Hand();
    private final List<Karte> inPlay = new ArrayList<>();
    private final List<Karte> permanent = new ArrayList<>();
    private int MindBug = 2;
    private final List<Karte> discardPile = new ArrayList<>();

    private Deck deck;

    public Player(Deck deck, String name) {
        this.deck = new Deck(this, deck);
        this.name = name;
    }

    public Karte setInPlay(int index)
    {
        Karte k = hand.spielen(index);
        if (deck.getDeck().size() > 0)
            hand.addKarte(deck.draw());
        return k;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public Hand getHand() {
        return hand;
    }

    public List<Karte> getInPlay() {
        return inPlay;
    }

    public int getMindBug() {
        return MindBug;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Karte> getPermanent() {
        return permanent;
    }

    public void addPermanent(Karte karte)
    {
        permanent.add(karte);
    }

    public void removePermanent(Karte karte)
    {
        permanent.remove(karte);
    }

    public void setMindBug(int mindBug) {
        MindBug = mindBug;
    }

    public Karte removeAndAdd(int karte)
    {
        return hand.getHand().remove(karte);
    }

    public void addToHand(Karte karte)
    {
        hand.addKarte(karte);
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public List<Karte> getDiscardPile() {
        return discardPile;
    }

    public Karte removeFromDiscard(int index)
    {
        return discardPile.remove(index);
    }

    public void addToDiscard(Karte karte)
    {
        discardPile.add(karte);
    }

    public void clearDiscard()
    {
        discardPile.clear();
    }

    public String printPlayer()
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 11; i++)
        {
            int finalIstart = i*Karte.with+6*i;
            int finalIend = finalIstart+Karte.with+2;
            int finalI = i;
            hand.getHand().forEach(karte -> s.append(karte.toStringByRow(finalI), finalIstart, finalIend));
            s.append("\n");
        }
        return s.toString();
    }

    public String printDiscard()
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 11; i++)
        {
            int finalIstart = i*Karte.with+6*i;
            int finalIend = finalIstart+Karte.with+2;
            int finalI = i;
            discardPile.forEach(karte -> s.append(karte.toStringByRow(finalI), finalIstart, finalIend));
            s.append("\n");
        }
        return s.toString();
    }
}
