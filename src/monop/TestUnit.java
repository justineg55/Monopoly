package monop;

import monop.card.Card;
import monop.card.CardFactory;
import monop.engine.MonopolyEngine;

import java.util.ArrayList;
import java.util.List;

public class TestUnit {
    public static void main(String[] args) {
        MonopolyEngine engine=new MonopolyEngine();
        CardFactory cardFactory=new CardFactory();
        List<Card> cartes=new ArrayList<>();

        cartes=engine.loadCardsFromRange(0,0);
        System.out.println(cartes);
        cartes.get(2).getName();


    }
}
