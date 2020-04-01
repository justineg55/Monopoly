package monop.effect;

import monop.player.Player;

public class GainEffect extends AbstractEffect{

    public GainEffect() {
    }

    public void Do(Player player, int value){
        System.out.println("Vous gagnez "+ value + " F");
        player.solde+=value;
    }
}
