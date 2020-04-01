package monop.effect;

import monop.player.Player;

public class GetOutOfPrisonEffect extends AbstractEffect{

    public GetOutOfPrisonEffect() {
    }

    public void Do(Player player, int value){
        player.getOutPrisonCard=this;

    }

}
