package monop.effect;

import monop.player.Player;

public class RelativeMoveEffect extends AbstractEffect {
    public RelativeMoveEffect() {
    }

    public void Do(Player player, int value){
        player.position+=value;

    }
}
