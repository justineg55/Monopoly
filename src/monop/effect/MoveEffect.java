package monop.effect;

import monop.player.Player;

public class MoveEffect extends AbstractEffect {

    public MoveEffect() {
    }

    public void Do(Player player, int value){
        player.position=value;

    }
}
