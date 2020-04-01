package monop.plateau;

import monop.NamedElement;
import monop.effect.AbstractEffect;

public abstract class AbstractCase extends NamedElement {
    protected AbstractEffect effect;

    public AbstractCase(String name, AbstractEffect effect) {
        super(name);
        this.effect = effect;
    }

    @Override
    public String toString() {
        return getName();
    }
}
