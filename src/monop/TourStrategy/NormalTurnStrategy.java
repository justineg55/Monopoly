package monop.TourStrategy;

import monop.effect.RelativeMoveEffect;
import monop.plateau.AbstractCase;
import monop.plateau.ActionCase;
import monop.plateau.BuildableTerrain;
import monop.plateau.Terrain;
import monop.player.Player;
import monop.utils.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class NormalTurnStrategy extends AbstractTurnStrategy {

    private RelativeMoveEffect move;

    public RelativeMoveEffect getMove() {
        return move;
    }

    public void setMove(RelativeMoveEffect move) {
        this.move = move;
    }

    public NormalTurnStrategy() {
        move = new RelativeMoveEffect();
    }

    @Override
    public void playTurn(Player player, List<AbstractCase> plateau) {
        //on regle le format pour ajouter des espaces dans les nombres pour permettre un affichage plus lisible des sommes d'argent
        NumberFormat formatter = DecimalFormat.getNumberInstance(Locale.FRANCE);

        Boolean answer = Utils.getBool("Voulez-vous voir la liste de vos propriétés ? o/n");
        if (answer) {
            seeList(player);
        }

        int nbMove=player.roll;
        if(!player.isRolled){
            nbMove = player.rollDice();
            System.out.println("Vous lancez les dés,vous avez fait un " + nbMove);
        }

        if (player.nbDoubles == 3) {
            System.out.println("Vous allez directement en prison sans passer par la case départ");
            player.GoToPrison();
            //on doit arreter la fonction playTurn car son tour s'arrete ici
            return;
        }
        move.Do(player, nbMove);
        //vérification si le joueur a fait un tour de plateau
        if (player.position >= plateau.size()) {
            //on place le joueur sur la bonne case par rapport au nombre de cases total
            player.position -= plateau.size();
            System.out.println("Vous passez par la case Départ, vous gagnez 20 000 F");
            player.solde += 20000;
        }

        System.out.println("Vous êtes sur la case " + plateau.get(player.position).getName());
        Utils.waitEnter("Appuyer sur Enter pour continuer");

        if (plateau.get(player.position).getClass() == ActionCase.class) {
            ((ActionCase) plateau.get(player.position)).applyEffect(player);
            System.out.println("Vous avez " + formatter.format(player.solde) + " F");
        }
        if (plateau.get(player.position).getClass() != ActionCase.class) {
            Terrain t = (Terrain) plateau.get(player.position);
            if (t.getProprio() == null) {
                System.out.println("Vous avez " + formatter.format(player.solde) + " F");
                System.out.println("La propriété coûte " + formatter.format(t.getCost()) + " F");
                Boolean answer2 = Utils.getBool("Voulez-vous acheter cette propriété ? o/n");
                if (answer2) {
                    t.buy(player);
                }
                System.out.println("Vous avez " + formatter.format(player.solde) + " F");
            } else {
                //à tester si le == player fonctionne ou s'il faut mettre .equals
                if (t.getProprio() == player) {
                    if (t.getClass() == BuildableTerrain.class) {
                        Boolean answer3 = Utils.getBool("Voulez-vous construire sur cette propriété ? o/n ");
                        if (answer3) {
                            ((BuildableTerrain) t).BuildHouse(player);
                        }
                    }
                } else {
                    t.payRent(player);
                    System.out.println(player + " a " + formatter.format(player.solde) + " F");
                    System.out.println(t.getProprio() + " a " + formatter.format(t.getProprio().solde) + " F");
                }
            }
        }
        player.isRolled=false;
    }

        @Override
        public void playTurn (Player player, List < AbstractCase > terrains,int roll){
        }

        public void seeList (Player player){
            if (player.getTerrains().size() > 0) {
                System.out.println("Vos propriétés sont : ");
                for (Terrain terrain : player.getTerrains()) {
                    System.out.println(terrain);
                }
                System.out.println();
            } else {
                System.out.println("Vous n'avez pas de propriétés" + "\n");
            }
        }
    }
