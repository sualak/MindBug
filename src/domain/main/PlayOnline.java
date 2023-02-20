package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static main.Handle.*;

public class PlayOnline
{
    public static void main(String[] args)
    {
        try {
            ServerSocket socket = new ServerSocket(4999);
            Socket s = socket.accept();
            System.out.println("client connected");
            InputStreamReader client = new InputStreamReader(s.getInputStream());
            BufferedReader in = new BufferedReader(client);

            String str = in.readLine();
            System.out.println("client : " + str);

            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("yes");
            try {

                boolean yourTurn = true;
                handleOutAndSout(Manuel.printManuel()+"\nBitte warten Spieler 1 gibt seinen namen ein...", out);
                Scanner sc = new Scanner(System.in);
                System.out.println("Name für spieler 1:");
                String nameP1 = sc.next();
                System.out.println("Bitte warten Spieler 2 gibt seinen Namen ein");;
                out.println("Name für spieler 2:");
                String nameP2 = in.readLine();
                Deck d = new Deck();
                Player p1 = new Player(d, nameP1);
                Player p2 = new Player(d, nameP2);
                Player playerSwitch;
                Spielfeld sf = new Spielfeld(p1, p2);
                List<Ability> abilitys = d.abilityList();
                List<Keywords> keywords = d.getKeywordsList();
                boolean gameIsOver = false;
                List<Karte> alreadyTrigger = new ArrayList<>();
                List<Karte> playedThisTurn = new ArrayList<>();
                Player serverPlayer = p1;
                Player clientPlayer = p2;


                while (!gameIsOver) {
                    Player finalMyturn = p1;
                    sf.spielfeld.get(p1).stream().filter(karte -> karte.getAbility().trigger() == Ability.Trigger.PERMANENTMYTURN && !finalMyturn.getPermanent().contains(karte)).forEach(karte -> finalMyturn.getPermanent().add(karte));
                    p1.getPermanent().forEach(k -> handlePermanentMyTurn(k, finalMyturn, sc, sf, abilitys, true, playedThisTurn));
                    Player finalP2Permanent = p2;
                    sf.spielfeld.get(p1).forEach(karte -> handlePermanent(karte, finalMyturn, finalP2Permanent, sc, abilitys, sf, finalP2Permanent.getPermanent(), true));
                    List<Karte> activePlayer = sf.spielfeld.get(p1);
                    List<Karte> defPlayer = sf.spielfeld.get(p2);

                    handlePrintSpielfeld(out, serverPlayer, clientPlayer, sf, yourTurn);
                    handleOutAndSout("a = attack p = play X = end", out);
                    handlePrintPlayers(out, serverPlayer, clientPlayer);
                    char c = yourTurn ? sc.next().charAt(0) : in.readLine().charAt(0);
                    switch (c) {

                        case 'a': {
                            handleClearScreenOnline(out);
                            handlePrintSpielfeld(out, serverPlayer, clientPlayer, sf,yourTurn);
                            int indexAtt = 999;
                            int indexDef = 999;
                            boolean attValid = false;
                            if (activePlayer.size() > 0) {
                                while (!attValid) {
                                    handleOutAndSout(p1.getName() + " Attack index eingeben von 0 bis " + (activePlayer.size() - 1) + " oder 999 für keine aktion", out);
                                    indexAtt = yourTurn ? sc.nextInt() : Integer.parseInt(in.readLine());
                                    attValid = (indexAtt == 999 || indexAtt >= 0 && indexAtt < activePlayer.size());
                                }
                            }
                            if (indexAtt == 999)
                                break;
                            for (int i = 0; i < activePlayer.get(indexAtt).getCanAttack(); i++) {
                                Karte attackKreatur = activePlayer.get(indexAtt);

                                attValid = false;
                                if (defPlayer.size() > 0) {
                                    while (!attValid) {
                                        handleOutAndSout(activePlayer.get(indexAtt).isJaeger() ? (yourTurn ? serverPlayer.getName() : clientPlayer.getName()) : (yourTurn ? clientPlayer.getName() : serverPlayer.getName()) + " Defence index eingeben von 0 bis " + (defPlayer.size() - 1) + " oder 999 für keine aktion", out);
                                        indexDef = !yourTurn ? sc.nextInt() : Integer.parseInt(in.readLine());
                                        attValid = (indexDef == 999 || (indexDef >= 0 && indexDef < defPlayer.size()));
                                    }
                                }
                                handleAttackAbility(activePlayer.get(indexAtt), p1, p2, sc, sf, abilitys,out,in,serverPlayer,clientPlayer,yourTurn);
                                int finalIndexDef = indexDef;
                                if (indexDef == 999 || activePlayer.get(indexAtt).isRaffiniert() && !defPlayer.get(indexDef).isRaffiniert()
                                        || (activePlayer.get(indexAtt).getAbility() == abilitys.get(14) && sf.spielfeld.get(p2).get(indexDef).getPower() <= 6) ||
                                        (activePlayer.stream().anyMatch(karte -> karte.getAbility() == abilitys.get(20) && defPlayer.get(finalIndexDef).getPower() <= 4))) {
                                    p2.setHp(p2.getHp() - 1);
                                } else {
                                    sf.fight(indexAtt, indexDef, p1, p2);
                                }
                                Player finalP = p1;
                                boolean fin = true;
                                Player finalP1 = p2;
                                while (fin) {
                                    activePlayer.forEach(karte -> {
                                        if (karte.getCanDie() <= 0 && !alreadyTrigger.contains(karte))
                                            alreadyTrigger.add(handleLeavePlay(karte, finalP, finalP1, sc, abilitys, sf, finalP.getPermanent(), false, alreadyTrigger, playedThisTurn,out));
                                    });
                                    alreadyTrigger.forEach(karte -> handleAlreadyTriggered(finalP, karte, sf));
                                    alreadyTrigger.clear();
                                    defPlayer.forEach(karte -> {
                                        if (karte.getCanDie() <= 0 && !alreadyTrigger.contains(karte))
                                            alreadyTrigger.add(handleLeavePlayNotYourTurn(karte, finalP1, finalP, sc, abilitys, sf, finalP1.getPermanent(), false, alreadyTrigger, playedThisTurn,out));
                                    });
                                    alreadyTrigger.forEach(karte -> handleAlreadyTriggered(finalP1, karte, sf));
                                    fin = !(alreadyTrigger.size() == 0);
                                    alreadyTrigger.clear();
                                }
                                if (!activePlayer.contains(attackKreatur))
                                    break;
                            }
                            break;
                        }
                        case 'p': {
                            int index = 0;
                            boolean attValid = false;
                            List<Karte> handActivePlayer = p1.getHand().getHand();
                            while (!attValid) {
                                handleOutAndSout(p1.getName() + " play index eingeben von 0 bis " + (handActivePlayer.size() - 1) + " oder 999 für keine aktion", out);
                                index = yourTurn?sc.nextInt():Integer.parseInt(in.readLine());
                                attValid = (index == 999 || index >= 0 && index < handActivePlayer.size());
                            }
                            if (index == 999)
                                break;
                            handleClearScreen();
                            handlePrintSpielfeld(out, serverPlayer, clientPlayer, sf, yourTurn);
                            handleOutAndSout(p2.getName() + " möchtest du einen Mindbug einsetzen? y/n", out);
                            handleOutAndSout(handActivePlayer.get(index).toString(), out);
                            char mindBug = yourTurn ? in.readLine().charAt(0) : sc.next().charAt(0);
                            handleKeywords(handActivePlayer.get(index), keywords);
                            if (mindBug == 'y' && p2.getMindBug() > 0) {
                                handleComesIntoPlay(sf.putCardInPlay(p1.setInPlay(index), p2), p2, p1, sc, abilitys, sf, p2.getPermanent(), true, playedThisTurn,out,serverPlayer,clientPlayer,yourTurn,in);
                                p2.setMindBug(p2.getMindBug() - 1);
                            } else {
                                handleComesIntoPlay(sf.putCardInPlay(p1.setInPlay(index), p1), p1, p2, sc, abilitys, sf, p1.getPermanent(), true, playedThisTurn,out,serverPlayer,clientPlayer,yourTurn,in);
                            }
                            Player finalP = p1;
                            boolean fin = true;
                            Player finalP1 = p2;
                            while (fin) {
                                sf.spielfeld.get(p1).forEach(karte -> {
                                    if (karte.getCanDie() <= 0 && !alreadyTrigger.contains(karte))
                                        alreadyTrigger.add(handleLeavePlay(karte, finalP, finalP1, sc, abilitys, sf, finalP.getPermanent(), false, alreadyTrigger, playedThisTurn,out));
                                });
                                alreadyTrigger.forEach(karte -> finalP.addToDiscard(sf.removeCardInPlay(finalP, karte)));
                                sf.spielfeld.get(p2).forEach(karte -> {
                                    if (karte.getCanDie() <= 0 && !alreadyTrigger.contains(karte))
                                        alreadyTrigger.add(handleLeavePlayNotYourTurn(karte, finalP1, finalP1, sc, abilitys, sf, finalP1.getPermanent(), false, alreadyTrigger, playedThisTurn,out));
                                });
                                alreadyTrigger.forEach(karte -> finalP1.addToDiscard(sf.removeCardInPlay(finalP1, karte)));
                                fin = !(alreadyTrigger.size() == 0);
                                alreadyTrigger.clear();
                            }
                            sf.removeCardsInPlayHp(p1, p2);
                            break;
                        }
                        case 'X': {
                            gameIsOver = true;
                            handleOutAndSout("disconnect all sockeds closed", out);
                            break;
                        }
                        default:
                            handleOutAndSout("a = attack , p = play, X = end", out);
                    }
                    if (p1.getHp() <= 0 || p2.getHp() <= 0)
                    {
                        handleOutAndSout(p1.getHp() <= 0 ? p2.getName() : p1.getName() + " hat gewonnen", out);
                        handleOutAndSout("disconnect all sockeds closed", out);
                        break;
                    }
                    p1.getPermanent().forEach(k -> handlePermanentMyTurn(k, finalMyturn, sc, sf, abilitys, false, playedThisTurn));
                    playedThisTurn.clear();
                    playerSwitch = p1;
                    p1 = p2;
                    p2 = playerSwitch;
                    handleClearScreenOnline(out);
                    yourTurn = !yourTurn;
                }
                sc.close();
                s.close();
                socket.close();
                in.close();
                out.close();
                client.close();
            }
            catch (Exception e) {
                s.close();
                socket.close();
                in.close();
                out.close();
                client.close();
            }
        }
         catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}
