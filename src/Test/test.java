import main.Deck;
import main.Player;
import main.Spielfeld;

public class test
{
    public static void main(String[] args) {
        Deck d = new Deck();
        Player p1 = new Player(d, "berni");
        Player p2 = new Player(d, "chrisi");
        Spielfeld sf = new Spielfeld(p1,p2);


        System.out.println("player 1 print");
        sf.putCardInPlay(p1.setInPlay(0),p1);
        sf.printSpielfeld(p1,p2);
        p1.printPlayer();

        System.out.println("player 2 print");
        sf.putCardInPlay(p2.getHand().spielen(0),p2);
        sf.printSpielfeld(p2,p1);
        p2.printPlayer();

        sf.fight(0,0,p1,p2);
        sf.printSpielfeld(p1,p2);
    }
}
