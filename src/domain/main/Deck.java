package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck
{
    private final List<Karte> deck = new ArrayList<>();
    private  final List<Ability> abilityList = new generateAbilitys().getAbilityList();
    private final List<Keywords> keywordsList = new generateKeyWords().getKeywordsList();

    public Deck()
    {
        deck.add(new Karte("Gorillöwe", 10, abilityList.get(0), keywordsList.get(0)));
        deck.add(new Karte("Chamäleonschütze", 1, abilityList.get(1), keywordsList.get(4)));
        deck.add(new Karte("Ködernixe", 7, abilityList.get(2), keywordsList.get(0)));
        deck.add(new Karte("Einsamer Yeti", 5, abilityList.get(3), keywordsList.get(5)));
        deck.add(new Karte("KompostDrache", 3, abilityList.get(4), keywordsList.get(3)));
        deck.add(new Karte("KompostDrache", 3, abilityList.get(4), keywordsList.get(3)));
        deck.add(new Karte("Knallfrosch", 5, abilityList.get(5), keywordsList.get(2)));
        deck.add(new Karte("Knallfrosch", 5, abilityList.get(5), keywordsList.get(2)));
        deck.add(new Karte("Panzernashorn", 8, abilityList.get(0), keywordsList.get(2),keywordsList.get(5)));
        deck.add(new Karte("Panzernashorn", 8, abilityList.get(0), keywordsList.get(2),keywordsList.get(5)));
        deck.add(new Karte("Todesweberin", 2, abilityList.get(6), keywordsList.get(4)));
        deck.add(new Karte("Steuereintröter", 8, abilityList.get(7), keywordsList.get(0)));
        deck.add(new Karte("Steuereintröter", 8, abilityList.get(7), keywordsList.get(0)));
        deck.add(new Karte("Frettchenbomber", 2, abilityList.get(8), keywordsList.get(4)));
        deck.add(new Karte("Frettchenbomber", 2, abilityList.get(8), keywordsList.get(4)));
        deck.add(new Karte("Tiegerhörnchen", 3, abilityList.get(9), keywordsList.get(4)));
        deck.add(new Karte("Tiegerhörnchen", 3, abilityList.get(9), keywordsList.get(4)));
        deck.add(new Karte("Killerbiene", 5, abilityList.get(10), keywordsList.get(3)));
        deck.add(new Karte("Killerbiene", 5, abilityList.get(10), keywordsList.get(3)));
        deck.add(new Karte("Kängusaurus Rex", 7, abilityList.get(11), keywordsList.get(0)));
        deck.add(new Karte("Kängusaurus Rex", 7, abilityList.get(11), keywordsList.get(0)));
        deck.add(new Karte("Titanoskorp", 2, abilityList.get(0), keywordsList.get(5), keywordsList.get(3)));
        deck.add(new Karte("Titanoskorp", 2, abilityList.get(0), keywordsList.get(5), keywordsList.get(3)));
        deck.add(new Karte("Seeigel-Bola", 5, abilityList.get(12), keywordsList.get(2)));
        deck.add(new Karte("Grabräuber", 7, abilityList.get(13), keywordsList.get(5)));
        deck.add(new Karte("Grabräuber", 7, abilityList.get(13), keywordsList.get(5)));
        deck.add(new Karte("El Tauro", 9, abilityList.get(0), keywordsList.get(1)));
        deck.add(new Karte("El Tauro", 9, abilityList.get(0), keywordsList.get(1)));
        deck.add(new Karte("Brummbär", 8, abilityList.get(14), keywordsList.get(0)));
        deck.add(new Karte("Eulenspinne", 3, abilityList.get(0), keywordsList.get(4), keywordsList.get(3)));
        deck.add(new Karte("Eulenspinne", 3, abilityList.get(0), keywordsList.get(4), keywordsList.get(3)));
        deck.add(new Karte("Wilde Chimäre", 5, abilityList.get(15), keywordsList.get(0)));
        deck.add(new Karte("Befremdlicher Bottich", 6, abilityList.get(16), keywordsList.get(0)));
        deck.add(new Karte("Axolotl-Heilerin", 4, abilityList.get(17),keywordsList.get(3)));
        deck.add(new Karte("Axolotl-Heilerin", 4, abilityList.get(17),keywordsList.get(3)));
        deck.add(new Karte("Schneckenhydra", 9, abilityList.get(18), keywordsList.get(0)));
        deck.add(new Karte("Schneckenhydra", 9, abilityList.get(18), keywordsList.get(0)));
        deck.add(new Karte("Turbokäfer", 4, abilityList.get(19), keywordsList.get(0)));
        deck.add(new Karte("Elefantopus", 7, abilityList.get(20), keywordsList.get(5)));
        deck.add(new Karte("Giraffodil", 7, abilityList.get(21), keywordsList.get(0)));
        deck.add(new Karte("Hirnfliege", 4, abilityList.get(22), keywordsList.get(0)));
        deck.add(new Karte("Schneckenschleuder", 1, abilityList.get(23), keywordsList.get(4)));
        deck.add(new Karte("Haihund", 4, abilityList.get(24), keywordsList.get(2)));
        deck.add(new Karte("Harpyienmutter", 5, abilityList.get(25), keywordsList.get(0)));
        deck.add(new Karte("Schildwanzen", 4, abilityList.get(26), keywordsList.get(5)));
        deck.add(new Karte("Schildwanzen", 4, abilityList.get(26), keywordsList.get(5)));
        deck.add(new Karte("Goblinwerwolf", 2, abilityList.get(27), keywordsList.get(2)));
        deck.add(new Karte("Goblinwerwolf", 2, abilityList.get(27), keywordsList.get(2)));

        schuffel();
    }

    public Deck(Player player, Deck deck)
    {
        for (int i = 0; i < 10; i++) {
            this.deck.add(deck.draw());
        }

        for (int i = 0; i < 5; i++) {
            player.getHand().addKarte((draw()));
        }
    }

    public List<Karte> getDeck() {
        return Collections.unmodifiableList(deck);
    }

    public Karte draw()
    {
        if(deck.size() > 0) {
            return deck.remove(0);
        }
        return null;
    }

    public void schuffel()
    {
        for (int i = 0; i < 10; i++)
        {
            Collections.shuffle(deck);
        }
    }

    public List<Ability> abilityList()
    {
        return abilityList;
    }

    public List<Keywords> getKeywordsList() {
        return keywordsList;
    }

    public void printDeck()
    {
        deck.forEach(karte -> System.out.println(karte.toString()));
    }
}
