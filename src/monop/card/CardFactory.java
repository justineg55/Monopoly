package monop.card;

import monop.AbstractElementFactory;
import monop.card.Card;
import monop.effect.*;

public class CardFactory extends AbstractElementFactory {

//    private AbstractEffect drawCardEffect;
    private AbstractEffect noneEffect;
    private AbstractEffect payEffect;
    private AbstractEffect gainEffect;
    private AbstractEffect getOutOfPrisonEffect;
    private AbstractEffect moveEffect;
    private AbstractEffect realEstateTaxEffect;
    private AbstractEffect prisonEffect;
    private AbstractEffect relativeMoveEffect;

    //effet ajouté pour checker si il passe par la case de départ en se déplacant avec une carte
    private AbstractEffect avancerCheckDepart;

    public CardFactory() {
//        drawCardEffect = new DrawCardEffect();
        noneEffect = new NoneEffect();
        payEffect = new PayEffect();
        gainEffect = new GainEffect();
        getOutOfPrisonEffect = new GetOutOfPrisonEffect();
        moveEffect = new MoveEffect();
        realEstateTaxEffect = new RealEstateTaxEffect();
        prisonEffect = new PrisonEffect();
        relativeMoveEffect = new RelativeMoveEffect();
        avancerCheckDepart=new AvancerCheckPassageDepart();
    }

    @Override
    public Object create(String lineFromCardDoc) {
        Card card;
        AbstractEffect effect=noneEffect;
        int value=0;

        String[] separateElements=lineFromCardDoc.split(";");
        switch (separateElements[3]){
            case "dépense":
                effect=payEffect;
                value=Integer.parseInt(separateElements[4]);
                break;
            case "recette":
                effect=gainEffect;
                value=Integer.parseInt(separateElements[4]);
                break;
            case "frais immo":
                effect=realEstateTaxEffect;
                value=Integer.parseInt(separateElements[4]);
                break;
            case "aller":
                effect=moveEffect;
                value=Integer.parseInt(separateElements[4])-1;
                break;
            case "bonus":
                effect=getOutOfPrisonEffect;
                break;
            case "prison":
                effect=prisonEffect;
                break;
            case "déplacement relatif":
                effect=relativeMoveEffect;
                value=Integer.parseInt(separateElements[4]);
                break;
            case "revenir" :
                effect=moveEffect;
                value=Integer.parseInt(separateElements[4])-1;
                break;
            case "rendez-vous" :
                effect=avancerCheckDepart;
                value=Integer.parseInt(separateElements[4])-1;
        }

        card=new Card(separateElements[2],effect,value);
        return card;

    }
}
