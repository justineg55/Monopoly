package monop.plateau;

import monop.AbstractElementFactory;
import monop.card.Card;
import monop.effect.*;

import java.util.List;

public class CaseFactory extends AbstractElementFactory {
    private AbstractEffect none;
    private AbstractEffect gain;
    private AbstractEffect goToPrison;
    private AbstractEffect drawChanceCard;
    private AbstractEffect drawCCCard;
    private AbstractEffect pay;

    public CaseFactory(List<Card> chance, List<Card> cc) {
        this.none = new NoneEffect();

        this.gain = new GainEffect();
        this.goToPrison = new PrisonEffect();
        //on s'en fiche de la valeur de random car elle sera redéfinie dans la classe DrawCardEffect
        //on crée une valeur random pour pouvoir instancier DrawCardEffect
        int random = (int) Math.floor(Math.random());
        this.drawChanceCard = new DrawCardEffect(chance, random);
        this.drawCCCard = new DrawCardEffect(cc, random);
        this.pay = new PayEffect();
    }


    @Override
    public Object create(String datas) {
        AbstractEffect effect = none;
        int value = 0;
        AbstractCase casePlateau;
        String[] data = datas.split(";");
        if (data[3].equals("terrain") || data[3].equals("monopole")) {
            String nom=data[1];
            String couleurTerrain=data[4];
            int coutAchat = Integer.parseInt(data[5]);
            int baseRent=Integer.parseInt(data[7]);


            if (data[3].equals("terrain")) {
                int houseCost=Integer.parseInt(data[6]);
                int oneHouseRent=Integer.parseInt(data[8]);
                int twoHouseRent=Integer.parseInt(data[9]);
                int threeHouseRent=Integer.parseInt(data[10]);
                int fourHouseRent=Integer.parseInt(data[11]);
                int hotelRent=Integer.parseInt(data[12]);
                casePlateau = new BuildableTerrain(nom,couleurTerrain,coutAchat,houseCost,baseRent,oneHouseRent,twoHouseRent,threeHouseRent,fourHouseRent,hotelRent);
            } else {
                casePlateau = new Terrain(nom,coutAchat,baseRent,couleurTerrain);
            }
        } else {
            String[] dataEffect = data[2].split(",");
            switch (dataEffect[0]) {
                case "recette":
                    effect = gain;
                    value = Integer.parseInt(dataEffect[1]);
                    break;
                case "carte":
                    effect = dataEffect[1].equals("CC") ? drawCCCard : drawChanceCard;
                    break;
                case "prison":
                    effect=goToPrison;
                    break;
                case "dépense":
                    effect=pay;
                    value=Integer.parseInt(dataEffect[1]);
                    break;

            }
            casePlateau = new ActionCase(data[1], effect, value);
        }
        return casePlateau;

    }
}
