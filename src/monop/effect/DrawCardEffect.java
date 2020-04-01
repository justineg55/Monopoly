package monop.effect;

import monop.card.Card;
import monop.player.Player;

import java.util.List;

public class DrawCardEffect extends AbstractEffect {

    private List<Card> cards;
    private int random;

    public DrawCardEffect(List<Card> cards, int random) {
        this.cards = cards;
        this.random = random;
    }

    @Override
    public void Do(Player player, int value) {
        int number=(int)(Math.floor(Math.random()*cards.size()));
        Card card=cards.get(number);
        System.out.println(card);
        card.DoEffect(player);

    }
}
