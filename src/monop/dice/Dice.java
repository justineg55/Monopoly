package monop.dice;

import monop.NamedElement;

public class Dice {
    int numberFaces;

    public Dice(int numberFaces) {
        this.numberFaces = numberFaces;
    }

    public int rollDice() {
//       return (int) (Math.floor(Math.random() * (numberFaces) + 1));
        return 1;
    }

}


