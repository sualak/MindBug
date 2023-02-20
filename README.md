# MindBug
MindBug ist ein Kartenspiel.s
                        
Ziel des spiels ist es die Gegnerischen Lebenspunkte auf 0 zu bringen.
                        
Ablauf des Spiels:
    Zu begin des Spiels erhält jeder Spieler
    3   Leben
    10  Spielkarten (5 Handkarten und 5 Karten im Deck)
    2   MindBugs
                                    
    Zuerst muss man den Namen des ersten Spielers eingeben und danach des zweiten Spielers.
                            
    Man kann einmal pro Zug entweder eine Karte ausspielen oder mit einer Karte angreifen.
                          
Ausspielen:
    Wenn man eine Karte ausspielen möchte muss man zuerst "p" drücken und danach den gewünschten index oder "999" zum überspringen.
    Wählt man einen index so hat der verteidigende Spieler die möglichkeit einen MindBug einzusetzen und so die Kontrolle der Karte zu erhalten.
                        
Angreifen:
  Wenn man Angreifen möchte muss man zuerst "a" drücken und danach den gewünschten index oder "999" zum überspringen.
  Wählt man einen index so wird mit dieser Karte angegriffen danach kann der verteidigende Spieler einen index angeben um mit einer Kreatur zu blocken oder "999" um nicht zu blocken.
      ->Sollte der verteidigende Spieler einen index auswählen so Kämpfen die Kreaturen die stärkere Kreatur gewinnt und überlebt die andere Kreatur stirbt ist die Kraft gleich so serben beide.
      ->Sollte der verteidigende Spieler "999" eingeben haben oder der blockenden Kreatur ist es nicht möglich zu blocken so verliert der verteidigende Spieler 1 Leben.
                        
                        __________________________________
                        |Stärke  Name                    |
                        |                                |
                        |Keywords                        |
                        |                                |
                        |                                |
                        |AbilityTrigger: Ability         |
                        |                                |
                        |                                |
                        |                                |
                        __________________________________
                                             
Karte:
    Stärke  -> gibt die aktuelle Stärke der Kreatur an.
                            
    Name    -> gibt den Namen der Kreatur an.
                            
Keywords-> Jede Kreatur kann unbegrenzt viele Keywords haben
    Raserei     -> Diese Kreatur kann 2 mal angreifen
    Jaeger      -> Der angreifende spieler kann auswählen welche Kreatur blocked
    Gift        -> Die Kreatur besiegt die andere Kreatur auch wenn sie weniger Stärke hat
    Raffiniert  -> Die Kreatur kann nur von Kreaturen geblocked werden die auch Raffiniert sind
    Robust      -> Die Kreatur muss 2 mal sterben um wirklich zu serben
                                
AbilityTrigger-> gibt man wann die Ability aktiviert wird
    Attack          -> wird aktiviert wenn die Kreatur angreift
    PermanentMyTurn -> ist nur während des eigenen zuges aktiv
    Permanent       -> ist permanent aktiv
    Play            -> wird aktiviert wenn die Kreatur ins Spiel kommt
    Nothing         -> nichts
                                
Ability -> Jede Kreatur kann nur eine Ability haben und ist auf der Karte beschrieben
