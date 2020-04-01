package monop.effect;

import monop.player.Player;

public class PayEffect extends AbstractEffect {

    public PayEffect() {
    }

    public void Do(Player player, int value){
        player.solde-=value;
    }
}
