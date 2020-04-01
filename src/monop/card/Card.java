package monop.card;

import monop.NamedElement;
import monop.effect.AbstractEffect;
import monop.player.Player;

public class Card extends NamedElement {
    private AbstractEffect effect;
    private int value;

    public Card(String name, AbstractEffect effect, int value) {
        super(name);
        this.effect = effect;
        this.value = value;
    }

    public void DoEffect(Player player){
        effect.Do(player,value);
    }
}
