package monop.effect;

import monop.player.Player;

public class PrisonEffect extends AbstractEffect{
    public PrisonEffect() {
    }

    public void Do(Player player, int value){
        System.out.println("Vous allez en prison");
        player.GoToPrison();

    }
}
