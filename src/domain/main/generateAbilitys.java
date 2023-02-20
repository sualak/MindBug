package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class generateAbilitys
{
    public final List<Ability> abilityList = new ArrayList<>();

    public generateAbilitys()
    {
        nothing();
        generateAttackDamage(1, Ability.Trigger.ATTACK);
        generateHpEqual(0, Ability.Trigger.PLAY);
        generatePowerAndRaserei(6, Ability.Trigger.PERMANENT);
        generateAusspielenAblagestapelMeiner(1, Ability.Trigger.PLAY);
        generateKillOnDeath(1, Ability.Trigger.DIE);
        generateNoActive(0, Ability.Trigger.PERMANENT);
        generateDiscard(1, Ability.Trigger.ATTACK);
        generateDiscard(2, Ability.Trigger.PLAY);
        generateKillCreature(7, Ability.Trigger.PLAY, true);
        generateAttackDamage(1, Ability.Trigger.PLAY);
        generateKillCreatures(4, Ability.Trigger.PLAY,false);
        generateBuffMyStärkeMyTurn(2, Ability.Trigger.PERMANENTMYTURN);
        generateAusspielenAblagestapelGegner(1, Ability.Trigger.PLAY);
        generateCantBeBlocked(6, Ability.Trigger.PERMANENT, false);
        generateEffectList(0, Ability.Trigger.PERMANENT);
        generateStealCardsHand(2, Ability.Trigger.DIE);
        generateGetHp(2, Ability.Trigger.PLAY);
        generateLessCreaturesKill(1, Ability.Trigger.ATTACK, false);
        generateLoseAllHp1(1, Ability.Trigger.ATTACK);
        generateCantBlock(4, Ability.Trigger.PERMANENT, false);
        generateGetAllAblage(1, Ability.Trigger.PLAY);
        generateGetControle(1, Ability.Trigger.PLAY,true, 6);
        generateBuffMy(4, Ability.Trigger.PERMANENT, false);
        generateKillCreature(6, Ability.Trigger.ATTACK, true);
        generateGetControle(2, Ability.Trigger.DIE, false, 5);
        generateBuffMyStärke(1, Ability.Trigger.PERMANENT);
        generateBuffMyTurn(6, Ability.Trigger.PERMANENTMYTURN);
    }


    public List<Ability> getAbilityList() {
        return Collections.unmodifiableList(abilityList);
    }

    public void nothing()
    {
        abilityList.add(new Ability("", Ability.Trigger.NOTHING, "", 0));
    }

    private void generateAttackDamage(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("AttackDamage", trigger,"Der Gegner verliert "+anz+" Lebenspunkte", anz));
    }

    private void generateHpEqual(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("HpEqual", trigger,"Setze deine Lebenspunkte gleich mit denen deines Gegners", anz));
    }

    private void generatePowerAndRaserei(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("PowerAndRaserei", trigger, "Solange diese deine einzige verbündete Kreatur ist, hat sie +"+anz+" Stärke und Raserei", anz));
    }

    private void generateAusspielenAblagestapelMeiner(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("AusspielenAblagestapelMeiner", trigger, "Spiele "+anz+" Karte deines Ablagestapels aus", anz));
    }

    private void generateAusspielenAblagestapelGegner(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("AusspielenAblagestapelGegner", trigger, "Spiele "+anz+" Karte des gegnerischen Ablagestapels aus", anz));
    }

    private void generateKillOnDeath(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("KillOnDeath", trigger, "Besiege "+anz+" Kreatur", anz));
    }

    private void generateNoActive(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("NoActive", trigger, "Der Gegner kann keine Ausspieleffekte aktivieren", 0));
    }

    private void generateDiscard(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("Discard", trigger, "Der Gegner muss "+anz+" Karte abwerfen", anz));
    }

    private void generateKillCreature(int anz, Ability.Trigger trigger, boolean mehr)
    {
        abilityList.add(new Ability("KillCreature", trigger, "Besiege eine feindliche Kreatur mit Stärke "+anz+" oder " + (mehr ? "mehr":"weniger"), anz));
    }

    private void generateKillCreatures(int anz, Ability.Trigger trigger, boolean mehr)
    {
        abilityList.add(new Ability("KillCreatures", trigger, "Besiege alle gegnerischen Kreaturen mit "+anz+ " oder "+(mehr ? "mehr":"weniger"), anz));
    }

    private void generateMyTurnBuff(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("MyTurnBuff", trigger, "Während deines Zuges haben andere verbündete Kreaturen +"+anz+" Stärke", anz));
    }

    private void generateCantBeBlocked(int anz, Ability.Trigger trigger, boolean mehr)
    {
        abilityList.add(new Ability("CantBeBlocked", trigger, "Kann nicht von Kreaturen mit Stärke "+anz+" oder "+(mehr?"mehr":"weniger")+" geblocked werden", anz));
    }

    private void generateCantBlock(int anz, Ability.Trigger trigger, boolean mehr)
    {
        abilityList.add(new Ability("CantBlock", trigger, "Der Gegner kann nicht mit Kreaturen mit Stärke "+ anz+" oder "+(mehr?"mehr":"weniger")+" blocken", anz));
    }

    private void generateEffectList(int anz,Ability.Trigger trigger)
    {
        abilityList.add(new Ability("EffectList", trigger, "Besitzt eine gegnerische Kreatur die Fähigkeit Jaeger, Raffiniert, Raserei, oder Giftig, hat Wilde Chimäre diese Fähigkeit ebenso", anz));
    }

    private void generateStealCardsHand(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("StealCardsHand", trigger, "Stehle "+ anz+" zufällige handkarten deines Gegners", anz));
    }

    private void generateGetHp(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("GetHp", trigger, "Erhalte "+anz+" Lebenspunkte", anz));
    }

    private void generateLessCreaturesKill(int anz, Ability.Trigger trigger, boolean mehr)
    {
        abilityList.add(new Ability("LessCreaturesKill", trigger, "Wenn du "+(mehr?"mehr":"weniger")+" Kreaturen als dein Gegner kontrollierst besiege "+anz+" Kreatur", anz));
    }

    private void generateLoseAllHp1(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("LoseAllHp1", trigger, "Der Gegner verliert alle Lebenspunkte bis auf "+ anz, anz));
    }

    private void generateGetAllAblage(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("GetAllAblage", trigger, "Ziehe deinen gesamten Ablagestapel", anz));
    }

    private void generateGetControle(int anz, Ability.Trigger trigger, boolean mehr, int stärke)
    {
        abilityList.add(new Ability("GetControle", trigger, "Übernimm die Kontrolle über "+ anz+ "Kreatur mit Stärke "+stärke+" oder "+(mehr?"mehr":"weniger"), anz));
    }

    private void generateBuffMy(int anz, Ability.Trigger trigger, boolean mehr)
    {
        abilityList.add(new Ability("BuffMy", trigger, "Andere verbündete Kreaturen mit Stärke "+anz+ " oder "+ (mehr?"mehr":"weniger")+" haben Jaeger und Gift", anz));
    }

    private void generateBuffMyStärke(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("BuffMyStärke", trigger, "Andere verbündete Kreaturen haben +"+anz+" Stärke", anz));
    }

    private void generateBuffMyStärkeMyTurn(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("BuffMyStärke", trigger, "Während deines Zuges haben andere verbündete Kreaturen +"+anz+" Stärke", anz));
    }

    private void generateBuffMyTurn(int anz, Ability.Trigger trigger)
    {
        abilityList.add(new Ability("BuffMyTurn", trigger, "Hat während deines Zuges +"+anz+" Stärke", anz));
    }
}
