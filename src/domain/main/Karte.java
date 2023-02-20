package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Karte
{
    private final String name;
    private int power;
    private final List<Keywords> keywords = new ArrayList<>();
    private final Ability ability;
    private boolean permanentIsActive = false;
    private int canDie = 1;
    private int canAttack = 1;
    private boolean isRaffiniert = false;
    private boolean isPoisones = false;

    private boolean isJaeger = false;


    public static final int with = 32;

    public Karte(String namen, int power, Ability ability, Keywords ...keywords)
    {
        this.name = namen;
        this.power = power;
        this.ability = ability;
        Arrays.stream(keywords).forEach(this::addKeyword);
    }

    public boolean isJaeger() {
        return isJaeger;
    }

    public String getName()
    {
        return name;
    }

    public Integer getPower() {
        return power;
    }

    public Ability getAbility() {
        return ability;
    }

    public int getCanDie() {
        return canDie;
    }

    public boolean isPermanentIsActive() {
        return permanentIsActive;
    }

    public int getCanAttack() {
        return canAttack;
    }

    public boolean isRaffiniert() {
        return isRaffiniert;
    }

    public boolean isPoisones() {
        return isPoisones;
    }

    public void setJaeger(boolean Jaeger) {
        isJaeger = Jaeger;
    }

    public List<Keywords> getKeywords()
    {
        return Collections.unmodifiableList(keywords);
    }

    public void addKeyword(Keywords keyword)
    {
        keywords.add(keyword);
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setCanDie(int canDie) {
        this.canDie = canDie;
    }

    public void setPoisones(boolean poisones) {
        isPoisones = poisones;
    }

    public void setCanAttack(int canAttack) {
        this.canAttack = canAttack;
    }

    public void setRaffiniert(boolean raffiniert) {
        isRaffiniert = raffiniert;
    }

    public void setPermanentIsActive(boolean permanentIsActive) {
        this.permanentIsActive = permanentIsActive;
    }

    public String toString()
    {
        StringBuilder s2 = new StringBuilder();
        int anzEffecte = keywords.size();
        s2.append("_".repeat(with+2));
        s2.append("\n" + "|").append(String.format("%s%d%s%-"+(with-3)+"s",(power<10?0:""),power," ", name)).append("|");
        s2.append("\n" + "|").append(" ".repeat(with)).append("|");
        for (Keywords value : keywords)
        {
            s2.append("\n" + "|").append(String.format("%-"+with+"s", value)).append("|");
        }
        s2.append("\n" + "|").append(" ".repeat(with)).append("|");

        for (int i = 0; i < ability.toString().length(); i+=with) {
            s2.append("\n" + "|").append(String.format("%-"+with+"s", ability.toString().substring(i, (Math.min(i + with, ability.toString().length()))))).append("|");
            anzEffecte++;
        }
        for (int i = 0; i < 6-anzEffecte; i++)
        {
            s2.append("\n" + "|").append(" ".repeat(with)).append("|");
        }
        s2.append("\n").append("_".repeat(with+2));
        return s2.toString();
    }

    public String toStringByRow(int row)
    {
        StringBuilder s2 = new StringBuilder();
        int anzEffecte = keywords.size();
        s2.append("_".repeat(with+2));
        s2.append("\n" + "|").append(String.format("%s%d%s%-"+(with-3)+"s",(power<10?0:""),power," ", name)).append("|");
        s2.append("\n" + "|").append(" ".repeat(with)).append("|");
        for (Keywords value : keywords)
        {
            s2.append("\n" + "|").append(String.format("%-"+with+"s", value)).append("|");
        }
        s2.append("\n" + "|").append(" ".repeat(with)).append("|");

        for (int i = 0; i < ability.toString().length(); i+=with) {
            s2.append("\n" + "|").append(String.format("%-"+with+"s", ability.toString().substring(i, (Math.min(i + with, ability.toString().length()))))).append("|");
            anzEffecte++;
        }
        for (int i = 0; i < 6-anzEffecte; i++)
        {
            s2.append("\n" + "|").append(" ".repeat(with)).append("|");
        }
        s2.append("\n").append("_".repeat(with+2));
        return s2.toString().replaceAll("\n", "     ").substring(row);
    }
}
