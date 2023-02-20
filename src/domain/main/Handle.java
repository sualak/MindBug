package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public abstract class Handle {

    public static void handleClearScreen()
    {
        System.out.println("\n".repeat(40));
    }

    public static void handleClearScreenOnline(PrintWriter out)
    {
        System.out.println("\n".repeat(40));
        out.println("\n".repeat(40));
    }

    public static void handleOutAndSout(String toPrint, PrintWriter out)
    {
        System.out.println(toPrint);
        out.println(toPrint);
    }

    public static void handlePrintPlayers(PrintWriter out, Player serverPlayer, Player clientPlayer)
    {
        System.out.println(serverPlayer.printPlayer());
        out.println(clientPlayer.printPlayer());
    }

    public static void handlePrintSpielfeld(PrintWriter out, Player serverPlayer, Player clientPlayer, Spielfeld spielfeld, boolean yourTurn)
    {

        out.println(spielfeld.printSpielfeld(clientPlayer, serverPlayer)+"\n\nSpieler "+(yourTurn?serverPlayer.getName():clientPlayer.getName())+" ist dran\n");
        System.out.println(spielfeld.printSpielfeld(serverPlayer, clientPlayer)+"\n\nSpieler "+(yourTurn?serverPlayer.getName():clientPlayer.getName())+" ist dran\n");
    }

    public static void handleAlreadyTriggered(Player player, Karte karte, Spielfeld spielfeld)
    {
        player.addToDiscard(spielfeld.removeCardInPlay(player, karte));
        player.removePermanent(karte);
    }

    //fertig
    public static Karte handleDieAbility(Karte karte, Player player, Player player2, Scanner sc, List<Ability> abilityList, Spielfeld spielfeld, List<Karte> alreadyTrigger, PrintWriter out)
    {
        handleOutAndSout(player.getName()+": "+karte.getAbility().effect(), out);
        switch (abilityList.indexOf(karte.getAbility())) {
            case 0:
                break;
            case 5:
                int indexActivePlayer = sc.nextInt();
                spielfeld.spielfeld.get(player2).get(indexActivePlayer).setCanDie(spielfeld.spielfeld.get(player2).get(indexActivePlayer).getCanDie()-spielfeld.spielfeld.get(player2).get(indexActivePlayer).getAbility().anz());
                break;
            case 16:

                for (int i = 0; i < karte.getAbility().anz(); i++) {
                    int floor = (int) Math.floor(Math.random() * (player2.getHand().getHand().size()));
                    player.getHand().getHand().add(player2.removeAndAdd(floor));
                    player2.getHand().getHand().sort(Comparator.comparing(Karte::getName));
                    player2.getDeck().draw();
                }
                break;
            case 25:
                for (int i = 0; i < karte.getAbility().anz(); i++)
                {
                    indexActivePlayer = sc.nextInt();
                    if(spielfeld.spielfeld.get(player2).stream().anyMatch(karte1 -> karte1.getPower() <=5)) {
                        if (spielfeld.spielfeld.get(player2).get(indexActivePlayer).getPower() <= 5)
                            spielfeld.spielfeld.get(player).add(spielfeld.spielfeld.get(player2).remove(indexActivePlayer));
                    }
                }
                break;
            default:
        }
        return karte;
    }


    public static void handleAttackAbility(Karte karte, Player player,Player player2, Scanner sc,Spielfeld spielfeld, List<Ability> abilityList, PrintWriter out, BufferedReader in, Player serverPlayer, Player clientPlayer, boolean yourTurn)
    {
        try {
            switch (abilityList.indexOf(karte.getAbility())) {
                case 1:
                    player2.setHp(player2.getHp() - 1);
                    break;
                case 7:
                    handlePrintPlayers(out, serverPlayer, clientPlayer);
                    int indexActivePlayer = yourTurn ? sc.nextInt() : Integer.parseInt(in.readLine());
                    player2.addToDiscard(player2.getHand().discard(indexActivePlayer));
                    player2.getHand().addKarte(player2.getDeck().draw());
                    break;
                case 18:
                    if (spielfeld.spielfeld.get(player).size() < spielfeld.spielfeld.get(player2).size()) {
                        indexActivePlayer = yourTurn ? sc.nextInt() : Integer.parseInt(in.readLine());
                        spielfeld.spielfeld.get(player2).get(indexActivePlayer).setCanDie(spielfeld.spielfeld.get(player2).get(indexActivePlayer).getCanDie() - 1);
                    }
                    break;
                case 19:
                    player2.setHp(1);
                    break;
                case 24:
                    if (spielfeld.spielfeld.get(player2).stream().anyMatch(karte1 -> karte1.getPower() >= 6)) {
                        indexActivePlayer = yourTurn ? sc.nextInt() : Integer.parseInt(in.readLine());
                        if (spielfeld.spielfeld.get(player2).get(indexActivePlayer).getPower() >= 6) {
                            spielfeld.spielfeld.get(player2).get(indexActivePlayer).setCanDie(spielfeld.spielfeld.get(player2).get(indexActivePlayer).getCanDie() - 1);
                        }
                    }
                    break;
                default:
            }
        }
        catch (Exception e)
        {

        }
    }

    //fertig
    public static void handlePlayAbility(Karte karte, Player player, Player player2, Scanner sc, Spielfeld spielfeld, List<Ability> abilityList, List<Karte> playedThisTurn, PrintWriter out, Player serverPlayer, Player clientPlayer, boolean yourTurn, BufferedReader in)
    {
        try {

            int indexActivePlayer = 0;
            handleOutAndSout(karte.getAbility().effect(), out);
            if (player2.getPermanent().stream().noneMatch(karte1 -> karte1.getAbility() == abilityList.get(6))) {
                switch (abilityList.indexOf(karte.getAbility())) {
                    case 0:
                        break;
                    case 2:
                        player.setHp(player2.getHp());
                        break;
                    case 4:
                        if (player.getDiscardPile().size() > 0) {
                            if (yourTurn) {
                                System.out.println(serverPlayer.printDiscard());
                            } else {
                                out.println(clientPlayer.printDiscard());
                            }
                            indexActivePlayer = yourTurn ? sc.nextInt() : Integer.parseInt(in.readLine());
                            handleOutAndSout(player2.getName() + " möchtest du einen Mindbug einsetzen?", out);
                            char mindBug = sc.next().charAt(0);
                            if (mindBug == 'y' && player2.getMindBug() > 0) {
                                handleComesIntoPlay(spielfeld.putCardInPlay(player.setInPlay(indexActivePlayer), player2), player2, player, sc, abilityList, spielfeld, player2.getPermanent(), true, playedThisTurn,out, serverPlayer, clientPlayer, yourTurn, in);
                                player2.setMindBug(player2.getMindBug() - 1);
                            } else {
                                handleComesIntoPlay(spielfeld.putCardInPlay(player.setInPlay(indexActivePlayer), player), player, player2, sc, abilityList, spielfeld, player.getPermanent(), true, playedThisTurn, out, serverPlayer, clientPlayer, yourTurn, in);
                            }
                        }
                        break;
                    case 8:
                        if (player2.getHand().getHand().size() >= 2) {
                            handlePrintPlayers(out,serverPlayer,clientPlayer);
                            for (int i = 0; i < karte.getAbility().anz(); i++) {
                                indexActivePlayer = !yourTurn?sc.nextInt():Integer.parseInt(in.readLine());
                                player2.addToDiscard(player2.getHand().discard(indexActivePlayer));
                                player2.getHand().addKarte(player2.getDeck().draw());
                                handlePrintPlayers(out,serverPlayer,clientPlayer);
                            }
                        } else {
                            player2.getHand().getHand().forEach(karte1 -> player2.addToDiscard(player2.getHand().discard(player2.getHand().getHand().indexOf(karte1))));
                            player2.getHand().addKarte(player2.getDeck().draw());
                        }
                        break;
                    case 9:
                        indexActivePlayer = yourTurn?sc.nextInt():Integer.parseInt(in.readLine());
                        if (spielfeld.spielfeld.get(player2).get(indexActivePlayer).getPower() >= 7)
                            spielfeld.spielfeld.get(player2).get(indexActivePlayer).setCanDie(spielfeld.spielfeld.get(player2).get(indexActivePlayer).getCanDie() - spielfeld.spielfeld.get(player2).get(indexActivePlayer).getAbility().anz());
                        break;
                    case 10:
                        player2.setHp(player2.getHp() - karte.getAbility().anz());
                        break;
                    case 11:
                        spielfeld.spielfeld.get(player2).forEach(karte1 -> {
                            if (karte1.getPower() <= 4) karte1.setCanDie(karte1.getCanDie() - 1);
                        });
                        break;
                    case 13:
                        if (player2.getDiscardPile().size() > 0) {
                            if (!yourTurn) {
                                System.out.println(serverPlayer.printDiscard());
                            } else {
                                out.println(clientPlayer.printDiscard());
                            }
                            indexActivePlayer = yourTurn ? sc.nextInt() : Integer.parseInt(in.readLine());
                            handleOutAndSout(player2.getName() + " möchtest du einen Mindbug einsetzen?", out);
                            char mindBug = yourTurn ? in.readLine().charAt(0) : sc.next().charAt(0);;
                            if (mindBug == 'y' && player2.getMindBug() > 0) {
                                handleComesIntoPlay(spielfeld.putCardInPlay(player.setInPlay(indexActivePlayer), player2), player2, player, sc, abilityList, spielfeld, player2.getPermanent(), true, playedThisTurn, out, serverPlayer, clientPlayer, yourTurn, in);
                                player2.setMindBug(player2.getMindBug() - 1);
                            } else {
                                handleComesIntoPlay(spielfeld.putCardInPlay(player.setInPlay(indexActivePlayer), player), player, player2, sc, abilityList, spielfeld, player.getPermanent(), true, playedThisTurn,out, serverPlayer, clientPlayer, yourTurn, in);
                            }
                        }
                        break;
                    case 17:
                        player.setHp(player.getHp() + karte.getAbility().anz());
                        break;
                    case 21:
                        player.getDiscardPile().forEach(karte1 -> player.getHand().addKarte(karte1));
                        player.clearDiscard();
                        break;
                    case 22:
                        indexActivePlayer = yourTurn ? sc.nextInt() : Integer.parseInt(in.readLine());
                        if (spielfeld.spielfeld.get(player2).get(indexActivePlayer).getPower() >= 6) {
                            spielfeld.spielfeld.get(player).add(spielfeld.spielfeld.get(player2).get(indexActivePlayer));
                            spielfeld.spielfeld.get(player2).remove(indexActivePlayer);
                        }
                        break;
                    default:
                        break;
                }
            }
        }catch (IOException e)
        {

        }
    }


    //fertig
    public static void handlePermanentMyTurn(Karte karte, Player player, Scanner sc, Spielfeld sf, List<Ability> abilityList, boolean add, List<Karte> playedThisTurn)
    {
        switch (abilityList.indexOf(karte.getAbility())) {
            case 12:
                if(add && !playedThisTurn.contains(karte))
                {
                    sf.spielfeld.get(player).forEach(karten -> {
                        if (karte != karten && !playedThisTurn.contains(karten)) karten.setPower(karten.getPower()+abilityList.get(12).anz());
                    });
                }
                else if(!add && !playedThisTurn.contains(karte))
                {
                    sf.spielfeld.get(player).forEach(karten -> {
                        if (karte != karten && !playedThisTurn.contains(karten)) karten.setPower(karten.getPower()-abilityList.get(12).anz());
                    });
                }
                break;
            case 27:
                if(add && !playedThisTurn.contains(karte))
                {
                    karte.setPower(karte.getPower()+abilityList.get(27).anz());
                }
                else if (!add && !playedThisTurn.contains(karte))
                {
                    karte.setPower(karte.getPower()-abilityList.get(27).anz());
                }
                break;
            default:
        }
    }


    //handlePermanent
    public static void handlePermanent(Karte karte, Player player, Player player2, Scanner sc, List<Ability> abilityList, Spielfeld spielfeld,List<Karte> permanent,boolean add)
    {
        switch (abilityList.indexOf(karte.getAbility())) {
            case 3:
                if (spielfeld.spielfeld.get(player).size() == 1 && !karte.isPermanentIsActive()) {
                    karte.setPower(karte.getPower() + karte.getAbility().anz());
                    karte.setCanAttack(2);
                    karte.setPermanentIsActive(true);
                    permanent.add(karte);
                }
                else if(spielfeld.spielfeld.get(player).size() > 1 && karte.isPermanentIsActive())
                {
                    karte.setPower(karte.getPower() - karte.getAbility().anz());
                    karte.setCanAttack(1);
                    karte.setPermanentIsActive(false);
                }
                break;
            case 6:
                break;
            case 14:
                break;
            case 15:
                permanent.add(karte);
                karte.setRaffiniert(spielfeld.spielfeld.get(player2).stream().anyMatch(Karte::isRaffiniert));
                karte.setPoisones(spielfeld.spielfeld.get(player2).stream().anyMatch(Karte::isPoisones));
                karte.setJaeger(spielfeld.spielfeld.get(player2).stream().anyMatch(Karte::isJaeger));
                if((spielfeld.spielfeld.get(player2).stream().anyMatch(karte1 -> karte1.getCanAttack() == 2)))
                {
                    karte.setCanAttack(2);
                }
                else
                {
                    karte.setCanAttack(1);
                }
            case 20:
                break;
            case 23:
                if(!permanent.contains(karte) && add)
                {
                    permanent.add(karte);
                    spielfeld.spielfeld.get(player).forEach(karte1 -> {
                        if (karte1 != karte) karte1.setPoisones(true);});
                }
                else if (permanent.contains(karte) && !add)
                {
                    permanent.remove(karte);
                    spielfeld.spielfeld.get(player).forEach(karte1 -> {
                        if (karte1 != karte) karte1.setPoisones(false);});
                }
            case 26:
                if(!permanent.contains(karte) && add)
                {
                    permanent.add(karte);
                    spielfeld.spielfeld.get(player).forEach(karte1 -> {
                        if (karte1 != karte) karte1.setPower(karte1.getPower()+1);});
                }
                else if (permanent.contains(karte) && !add)
                {
                    permanent.remove(karte);
                    spielfeld.spielfeld.get(player).forEach(karte1 -> {
                        if (karte1 != karte) karte1.setPower(karte1.getPower()-1);});
                }
            default:
        }
    }

    //fertig
    public static void handleComesIntoPlay(Karte karte, Player player, Player player2, Scanner sc, List<Ability> abilityList, Spielfeld spielfeld,List<Karte> permanent,boolean add, List<Karte> playedThisTurn,PrintWriter out, Player serverPlayer, Player clientPlayer, boolean yourTurn, BufferedReader in)
    {
        if (!permanent.contains(karte) && karte.getAbility().trigger() == Ability.Trigger.PERMANENTMYTURN)
            permanent.add(karte);

        Ability.Trigger trigger = karte.getAbility().trigger();

        playedThisTurn.add(karte);

        handleUpdatePermanent(player, player2, sc, abilityList, spielfeld);

        switch (trigger)
        {
            case PLAY: handlePlayAbility(karte, player, player2, sc, spielfeld, abilityList, playedThisTurn,out, serverPlayer, clientPlayer, yourTurn, in); break;
            case PERMANENT: handlePermanent(karte, player,player2, sc, abilityList, spielfeld, permanent, add); break;
            case PERMANENTMYTURN: handlePermanentMyTurn(karte, player, sc, spielfeld, abilityList, add, playedThisTurn);break;
            default:
        }
    }

    //fertig
    public static Karte handleLeavePlay(Karte karte, Player player, Player player2, Scanner sc, List<Ability> abilityList, Spielfeld spielfeld,List<Karte> permanent,boolean add, List<Karte> alreadyTriggered, List<Karte> playedThisTurn, PrintWriter out)
    {

        Ability.Trigger trigger = handleUpdate(karte, player, player2, sc, abilityList, spielfeld, permanent, alreadyTriggered);;

        switch (trigger)
        {
            case DIE: handleDieAbility(karte, player, player2, sc, abilityList, spielfeld, alreadyTriggered, out); break;
            case PERMANENT: handlePermanent(karte, player,player2, sc, abilityList, spielfeld, permanent, add); break;
            case PERMANENTMYTURN: handlePermanentMyTurn(karte, player, sc, spielfeld, abilityList, add, playedThisTurn); break;
            default:
        }
        return karte;
    }

    private static Ability.Trigger handleUpdate(Karte karte, Player player, Player player2, Scanner sc, List<Ability> abilityList, Spielfeld spielfeld, List<Karte> permanent, List<Karte> alreadyTriggered) {
        Ability.Trigger trigger = Ability.Trigger.NOTHING;
        if (!alreadyTriggered.contains(karte))
            trigger = karte.getAbility().trigger();

        handleUpdatePermanent(player, player2, sc, abilityList, spielfeld);

        permanent.remove(karte);
        return trigger;
    }

    private static void handleUpdatePermanent(Player player, Player player2, Scanner sc, List<Ability> abilityList, Spielfeld spielfeld) {
        spielfeld.spielfeld.get(player).forEach(karte1 -> {
            if(karte1.getAbility() == abilityList.get(15))
                handlePermanent(karte1, player, player2, sc, abilityList, spielfeld, player.getPermanent(), true);});
        spielfeld.spielfeld.get(player2).forEach(karte1 -> {
            if(karte1.getAbility() == abilityList.get(15))
                handlePermanent(karte1, player2, player, sc, abilityList, spielfeld, player.getPermanent(), true);});
    }

    public static Karte handleLeavePlayNotYourTurn(Karte karte, Player player, Player player2, Scanner sc, List<Ability> abilityList, Spielfeld spielfeld,List<Karte> permanent,boolean add, List<Karte> alreadyTriggered, List<Karte> playedThisTurn, PrintWriter out)
    {
        Ability.Trigger trigger = handleUpdate(karte, player, player2, sc, abilityList, spielfeld, permanent, alreadyTriggered);

        switch (trigger)
        {
            case DIE: handleDieAbility(karte, player, player2, sc, abilityList, spielfeld, alreadyTriggered,out); break;
            case PERMANENT: handlePermanent(karte, player,player2, sc, abilityList, spielfeld, permanent, add); break;
            default:
        }
        return karte;
    }

    public static void handleKeywords(Karte karte, List<Keywords> keywords)
    {
        if(karte.getKeywords().contains(keywords.get(1)))
            karte.setCanAttack(2);
        if (karte.getKeywords().contains(keywords.get(2)))
            karte.setJaeger(true);
        if (karte.getKeywords().contains(keywords.get(3)))
            karte.setPoisones(true);
        if (karte.getKeywords().contains(keywords.get(4)))
            karte.setRaffiniert(true);
        if (karte.getKeywords().contains(keywords.get(5)))
            karte.setCanDie(2);
    }
}
