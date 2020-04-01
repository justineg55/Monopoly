package monop.effect;

import monop.player.Player;

public abstract class AbstractEffect {
    public AbstractEffect() {
    }

    public abstract void Do(Player player, int value);

}

