package main;

import java.util.*;

public class Spielfeld
{
    Map<Player, List<Karte>> spielfeld = new HashMap<>();

    public Spielfeld(Player p1, Player p2)
    {
        spielfeld.put(p1, new ArrayList<>());
        spielfeld.put(p2, new ArrayList<>());
    }

    public Karte putCardInPlay(Karte karten, Player comesIntoPlay)
    {
        spielfeld.get(comesIntoPlay).add(karten);
        return karten;
    }

    public Karte removeCardInPlay(Player player, Karte karten)
    {
        spielfeld.get(player).remove(karten);

        return karten;
    }

    public void removeCardsInPlayHp(Player p1, Player p2)
    {
        spielfeld.get(p1).removeIf(karte -> karte.getCanDie() <= 0);
        spielfeld.get(p2).removeIf(karte -> karte.getCanDie() <= 0);
    }

    public void fight(int attacker, int defender, Player attackerP, Player defenderP)
    {
        Karte aM = spielfeld.get(attackerP).get(attacker);
        Karte dM = spielfeld.get(defenderP).get(defender);
        int i = aM.getPower().compareTo(dM.getPower());
        if(aM.isPoisones())
            dM.setCanDie(dM.getCanDie()-1);

        if(dM.isPoisones())
            aM.setCanDie(aM.getCanDie()-1);

        if(i == 0) {
            aM.setCanDie(aM.getCanDie() - 1);
            dM.setCanDie(dM.getCanDie() - 1);
        }
        else if (i < 0)
        {
            aM.setCanDie(aM.getCanDie()-1);
        }
        else
        {
            dM.setCanDie(dM.getCanDie()-1);
        }
    }

    public String printSpielfeld(Player onTurn, Player notOnTurn)
    {
        StringBuilder s = new StringBuilder();
        s.append(notOnTurn.getName()).append(" ".repeat(5)).append("HP:").append(notOnTurn.getHp()).append(" ".repeat(5)).append("MindBugs: ").append(notOnTurn.getMindBug()).append("\n");
        printPlayerSpielfeld(notOnTurn, s);

        s.append("_".repeat(Karte.with+2).repeat(5));

        printPlayerSpielfeld(onTurn, s);
        s.append(onTurn.getName()).append(" ".repeat(5)).append("HP:").append(onTurn.getHp()).append(" ".repeat(5)).append("MindBugs: ").append(onTurn.getMindBug());
        return s.toString();
    }

    private String printPlayerSpielfeld(Player onTurn, StringBuilder s) {
        if(spielfeld.get(onTurn).size() > 0)
        {
            for (int i = 0; i < 11; i++)
            {
                int finalIstart = i* Karte.with+6*i;
                int finalIend = finalIstart+Karte.with+2;
                int finalI = i;
                spielfeld.get(onTurn).forEach(karte -> s.append(karte.toStringByRow(finalI), finalIstart, finalIend));
                if (s.toString().length() == 0)
                    break;

                s.append("\n");
            }
        }
        else
        {
            s.append("\n".repeat(11));
        }
        return s.toString();
    }

}
