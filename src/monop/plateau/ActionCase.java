package monop.plateau;

import monop.effect.AbstractEffect;
import monop.plateau.AbstractCase;
import monop.player.Player;

public class ActionCase extends AbstractCase {
    private int value;

    public ActionCase(String name, AbstractEffect effect, int value) {
        super(name, effect);
        this.value = value;
    }

    public void applyEffect(Player player){
        effect.Do(player,value);
    }
}
