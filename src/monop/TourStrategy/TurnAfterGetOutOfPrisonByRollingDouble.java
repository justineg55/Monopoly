package monop.TourStrategy;

import monop.effect.RelativeMoveEffect;
import monop.plateau.AbstractCase;
import monop.plateau.ActionCase;
import monop.plateau.BuildableTerrain;
import monop.plateau.Terrain;
import monop.player.Player;

import java.util.List;
import java.util.Scanner;

public class TurnAfterGetOutOfPrisonByRollingDouble extends AbstractTurnStrategy {
    private RelativeMoveEffect move;

    public RelativeMoveEffect getMove() {
        return move;
    }

    public void setMove(RelativeMoveEffect move) {
        this.move = move;
    }

    public TurnAfterGetOutOfPrisonByRollingDouble() {
        move = new RelativeMoveEffect();
    }

    public void playTurn(Player player, List<AbstractCase> terrains) {

    }

    @Override
    public void playTurn(Player player, List<AbstractCase> plateau, int roll) {
        Scanner scan = new Scanner(System.in);
        int nbMove = roll;

//        System.out.println("Vous avez fait un "+ nbMove);
        if (player.nbDoubles == 3) {
            System.out.println("Vous allez directement en prison sans passer par la case départ");
            player.GoToPrison();
            //on doit arreter la fonction playTurn car son tour s'arrete ici
            return;
        }
        move.Do(player, nbMove);
        if (player.position >= plateau.size()) {
            player.position -= plateau.size();
            System.out.println("Vous passez par la case Départ, vous gagnez 20 000 F");
            player.solde += 20000;
        }

        System.out.println("vous êtes sur la case " + plateau.get(player.position).getName());

        if (plateau.get(player.position).getClass() == ActionCase.class) {
            ((ActionCase) plateau.get(player.position)).applyEffect(player);
            System.out.println("Vous avez " + player.solde + " F");
        } else {
            Terrain t = (Terrain) plateau.get(player.position);
            if (t.getProprio() == null) {
                System.out.println("Vous avez " + player.solde + " F");
                System.out.println("La propriété coûte " + t.getCost() + " F");
                System.out.println("Voulez-vous acheter ? o/n");
                boolean reponse_bool = false;

                String reponse_utilisateur = scan.nextLine();
                System.out.println(reponse_utilisateur);
                while (!reponse_utilisateur.equals("o") && !reponse_utilisateur.equals("n") && !reponse_utilisateur.equals("O") && !reponse_utilisateur.equals("N")) {
                    System.out.println("Voulez-vous acheter ? o/n");
                    reponse_utilisateur = scan.nextLine();
                    System.out.println(reponse_utilisateur);
                }

                if (reponse_utilisateur.equals("o") || reponse_utilisateur.equals("O"))
                    reponse_bool = true;

                if (reponse_bool)
                    t.buy(player);
                System.out.println("Vous avez " + player.solde + " F");
            } else {
                //à tester si le == player fonctionne ou s'il faut mettre .equals
                if (t.getProprio() == player) {
                    if (t.getClass() == BuildableTerrain.class) {
                        System.out.println("Voulez-vous construire ? o/n ");
                        boolean reponse_bool = false;

                        String reponse_utilisateur = scan.nextLine();
                        while (!reponse_utilisateur.equals("o") && !reponse_utilisateur.equals("n") && !reponse_utilisateur.equals("O") && !reponse_utilisateur.equals("N")) {
                            System.out.println("Voulez-vous construire ? o/n");
                            reponse_utilisateur = scan.nextLine();
                        }

                        if (reponse_utilisateur.equals("o") || reponse_utilisateur.equals("O"))
                            reponse_bool = true;

                        if (reponse_bool)
                            ((BuildableTerrain) t).BuildHouse(player);
                    }
                } else {
                    t.payRent(player);
                    System.out.println(player + " a " + player.solde + " F");
                    System.out.println(t.getProprio() + " a " + t.getProprio().solde + " F");
                }
            }
        }
        player.GetOutOfPrison();
    }
}
