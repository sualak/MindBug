//package main;
//
//import java.net.ServerSocket;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import static main.Handle.*;
//import static main.Handle.handleClearScreen;
//
//public class PlayOffline
//{
//        public static void mainOffline(String[] args)
//        {
//            Manuel.printManuel();
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Name für spieler 1:");
//            String nameP1 = sc.next();
//            System.out.println("Name für spieler 2:");
//            String nameP2 = sc.next();
//            Deck d = new Deck();
//            Player p1 = new Player(d, nameP1);
//            Player p2 = new Player(d, nameP2);
//            Player playerSwitch;
//            Spielfeld sf = new Spielfeld(p1, p2);
//            List<Ability> abilitys = d.abilityList();
//            List<Keywords> keywords = d.getKeywordsList();
//            boolean gameIsOver = false;
//            List<Karte> alreadyTrigger = new ArrayList<>();
//            List<Karte> playedThisTurn = new ArrayList<>();
//
//
//            while (!gameIsOver) {
//                Player finalMyturn = p1;
//                sf.spielfeld.get(p1).stream().filter(karte -> karte.getAbility().trigger() == Ability.Trigger.PERMANENTMYTURN && !finalMyturn.getPermanent().contains(karte)).forEach(karte -> finalMyturn.getPermanent().add(karte));
//                p1.getPermanent().forEach(k -> handlePermanentMyTurn(k, finalMyturn, sc, sf, abilitys, true, playedThisTurn));
//                Player finalP2Permanent = p2;
//                sf.spielfeld.get(p1).forEach(karte -> handlePermanent(karte, finalMyturn, finalP2Permanent, sc, abilitys, sf, finalP2Permanent.getPermanent(), true));
//                List<Karte> activePlayer = sf.spielfeld.get(p1);
//                List<Karte> defPlayer = sf.spielfeld.get(p2);
//
//
//                sf.printSpielfeld(p1, p2);
//                System.out.println("a = attack p = play X = end");
//                System.out.println(p1.printPlayer());
//                char c = sc.next().charAt(0);
//                switch (c) {
//
//                    case 'a': {
//                        handleClearScreen();
//                        sf.printSpielfeld(p1, p2);
//                        int indexAtt = 999;
//                        int indexDef = 999;
//                        boolean attValid = false;
//                        if (activePlayer.size() > 0) {
//                            while (!attValid) {
//                                System.out.println(p1.getName() + " Attack index eingeben von 0 bis " + (activePlayer.size() - 1) + " oder 999 für keine aktion");
//                                indexAtt = sc.nextInt();
//                                attValid = (indexAtt == 999 || indexAtt >= 0 && indexAtt < activePlayer.size());
//                            }
//                        }
//                        if (indexAtt == 999)
//                            break;
//                        for (int i = 0; i < activePlayer.get(indexAtt).getCanAttack(); i++) {
//                            Karte attackKreatur = activePlayer.get(indexAtt);
//
//                            attValid = false;
//                            if (defPlayer.size() > 0) {
//                                while (!attValid) {
//                                    System.out.println((activePlayer.get(indexAtt).isJaeger() ? p1.getName() : p2.getName()) + " Defence index eingeben von 0 bis " + (defPlayer.size() - 1) + " oder 999 für keine aktion");
//                                    indexDef = sc.nextInt();
//                                    attValid = (indexDef == 999 || (indexDef >= 0 && indexDef < defPlayer.size()));
//                                }
//                            }
//                            handleAttackAbility(activePlayer.get(indexAtt), p1, p2, sc, sf, abilitys);
//                            int finalIndexDef = indexDef;
//                            if (indexDef == 999 || activePlayer.get(indexAtt).isRaffiniert() && !defPlayer.get(indexDef).isRaffiniert()
//                                    || (activePlayer.get(indexAtt).getAbility() == abilitys.get(14) && sf.spielfeld.get(p2).get(indexDef).getPower() <= 6) ||
//                                    (activePlayer.stream().anyMatch(karte -> karte.getAbility() == abilitys.get(20) && defPlayer.get(finalIndexDef).getPower() <= 4))) {
//                                p2.setHp(p2.getHp() - 1);
//                            } else {
//                                sf.fight(indexAtt, indexDef, p1, p2);
//                            }
//                            Player finalP = p1;
//                            boolean fin = true;
//                            Player finalP1 = p2;
//                            while (fin) {
//                                activePlayer.forEach(karte -> {
//                                    if (karte.getCanDie() <= 0 && !alreadyTrigger.contains(karte))
//                                        alreadyTrigger.add(handleLeavePlay(karte, finalP, finalP1, sc, abilitys, sf, finalP.getPermanent(), false, alreadyTrigger, playedThisTurn));
//                                });
//                                alreadyTrigger.forEach(karte -> handleAlreadyTriggered(finalP, karte, sf));
//                                alreadyTrigger.clear();
//                                defPlayer.forEach(karte -> {
//                                    if (karte.getCanDie() <= 0 && !alreadyTrigger.contains(karte))
//                                        alreadyTrigger.add(handleLeavePlayNotYourTurn(karte, finalP1, finalP, sc, abilitys, sf, finalP1.getPermanent(), false, alreadyTrigger, playedThisTurn));
//                                });
//                                alreadyTrigger.forEach(karte -> handleAlreadyTriggered(finalP1, karte, sf));
//                                fin = !(alreadyTrigger.size() == 0);
//                                alreadyTrigger.clear();
//                            }
//                            if (!activePlayer.contains(attackKreatur))
//                                break;
//                        }
//                        break;
//                    }
//                    case 'p': {
//                        int index = 0;
//                        boolean attValid = false;
//                        List<Karte> handActivePlayer = p1.getHand().getHand();
//                        while (!attValid) {
//                            System.out.println(p1.getName() + " play index eingeben von 0 bis " + (handActivePlayer.size() - 1) + " oder 999 für keine aktion");
//                            index = sc.nextInt();
//                            attValid = (index == 999 || index >= 0 && index < handActivePlayer.size());
//                        }
//                        if (index == 999)
//                            break;
//                        handleClearScreen();
//                        sf.printSpielfeld(p1, p2);
//                        System.out.println(p2.getName() + " möchtest du einen Mindbug einsetzen? y/n");
//                        System.out.println(handActivePlayer.get(index));
//                        char mindBug = sc.next().charAt(0);
//                        handleKeywords(handActivePlayer.get(index), keywords);
//                        if (mindBug == 'y' && p2.getMindBug() > 0) {
//                            handleComesIntoPlay(sf.putCardInPlay(p1.setInPlay(index), p2), p2, p1, sc, abilitys, sf, p2.getPermanent(), true, playedThisTurn);
//                            p2.setMindBug(p2.getMindBug() - 1);
//                        } else {
//                            handleComesIntoPlay(sf.putCardInPlay(p1.setInPlay(index), p1), p1, p2, sc, abilitys, sf, p1.getPermanent(), true, playedThisTurn);
//                        }
//                        Player finalP = p1;
//                        boolean fin = true;
//                        Player finalP1 = p2;
//                        while (fin) {
//                            sf.spielfeld.get(p1).forEach(karte -> {
//                                if (karte.getCanDie() <= 0 && !alreadyTrigger.contains(karte))
//                                    alreadyTrigger.add(handleLeavePlay(karte, finalP, finalP1, sc, abilitys, sf, finalP.getPermanent(), false, alreadyTrigger, playedThisTurn));
//                            });
//                            alreadyTrigger.forEach(karte -> finalP.addToDiscard(sf.removeCardInPlay(finalP, karte)));
//                            sf.spielfeld.get(p2).forEach(karte -> {
//                                if (karte.getCanDie() <= 0 && !alreadyTrigger.contains(karte))
//                                    alreadyTrigger.add(handleLeavePlayNotYourTurn(karte, finalP1, finalP1, sc, abilitys, sf, finalP1.getPermanent(), false, alreadyTrigger, playedThisTurn));
//                            });
//                            alreadyTrigger.forEach(karte -> finalP1.addToDiscard(sf.removeCardInPlay(finalP1, karte)));
//                            fin = !(alreadyTrigger.size() == 0);
//                            alreadyTrigger.clear();
//                        }
//                        sf.removeCardsInPlayHp(p1, p2);
//                        break;
//                    }
//                    case 'X': {
//                        gameIsOver = true;
//                        break;
//                    }
//                    default:
//                        System.out.println("a = attack , p = play, X = end");
//                }
//                if (p1.getHp() <= 0 || p2.getHp() <= 0) {
//                    gameIsOver = true;
//                    System.out.println(p1.getHp() <= 0 ? p2.getName() : p1.getName() + " hat gewonnen");
//                }
//                p1.getPermanent().forEach(k -> handlePermanentMyTurn(k, finalMyturn, sc, sf, abilitys, false, playedThisTurn));
//                playedThisTurn.clear();
//                playerSwitch = p1;
//                p1 = p2;
//                p2 = playerSwitch;
//                handleClearScreen();
//                System.out.println(p1.getName() + " ist dran drücke r um fortzusetzen");
//                sc.next();
//            }
//            sc.close();
//        }
//    }
//
//
