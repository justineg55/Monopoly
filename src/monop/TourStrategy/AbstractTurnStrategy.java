package monop.TourStrategy;

import monop.plateau.AbstractCase;
import monop.player.Player;

import java.util.List;

public abstract class AbstractTurnStrategy {
    public AbstractTurnStrategy() {
    }

    abstract public void playTurn(Player player, List<AbstractCase> terrains);
    abstract public void playTurn(Player player, List<AbstractCase> terrains, int roll);
}
