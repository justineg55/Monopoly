package monop;


import monop.engine.MonopolyEngine;
import monop.utils.Utils;

public class Main {
    static MonopolyEngine engine;
    public static void main(String[] args) {
        System.out.println("On s'ferait pas une partie de Monopoly ?");
        Utils.waitEnter("Appuyer sur Enter pour continuer");
        engine=new MonopolyEngine();
        engine.init();
        engine.run();

        System.out.println("A bientôt");

    }
}
