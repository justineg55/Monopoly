package monop.TourStrategy;

import monop.effect.GetOutOfPrisonEffect;
import monop.effect.PayEffect;
import monop.plateau.AbstractCase;
import monop.player.Player;
import monop.utils.Utils;

import java.util.List;
import java.util.Scanner;

public class PrisonTurnStrategy extends AbstractTurnStrategy {
    private PayEffect payToGetOutPrison;

    public PrisonTurnStrategy() {
        payToGetOutPrison = new PayEffect();
    }

    @Override
    public void playTurn(Player player, List<AbstractCase> terrains) {
        Scanner scan = new Scanner(System.in);
        int nbMove = 0;

        if (player.getOutPrisonCard != null) {
//            System.out.println("Voulez-vous jouer votre carte pour sortir de prison ? o/n ");
//            boolean reponse_bool = false;
//            String reponse_utilisateur = scan.nextLine();
//            while (!reponse_utilisateur.equals("o") && !reponse_utilisateur.equals("n") && !reponse_utilisateur.equals("O") && !reponse_utilisateur.equals("N")) {
//                System.out.println("Voulez-vous jouer votre carte pour sortir de prison ? o/n ");
//                reponse_utilisateur = scan.nextLine();
//            }
//            if (reponse_utilisateur == "o")
//                reponse_bool = true;
//
//            if (reponse_bool) {
//                player.GetOutOfPrison();
//                player.getOutPrisonCard.Do(player,0);
//                player.Play(terrains);
//            }
            Boolean answer = Utils.getBool("Voulez-vous jouer votre carte pour sortir de prison ? o/n ");
            if (answer) {
                player.GetOutOfPrison();
                player.getOutPrisonCard.Do(player, 0);
                player.getOutPrisonCard = null;
                player.Play(terrains);
                return;
            }

        }
        Boolean answer2 = Utils.getBool("Voulez-vous payer 5000 F pour sortir de prison immédiatement ? o/n");
        if (answer2) {
            player.solde -= 5000;
            System.out.println("Vous sortez de prison");
            player.GetOutOfPrison();
            player.Play(terrains);
        } else {
            nbMove = player.rollDice();
            System.out.println("Vous faites un " + nbMove);
            if (player.nbDoubles > 0) {
                System.out.println("Vous avez fait un double, vous sortez de prison");
//                    player.GetOutOfPrisonByRollingDouble();
                player.GetOutOfPrison();
                player.Play(terrains);
            } else if (player.getNbDiceRolls() == 3) {
                System.out.println("Vous devez payer 5000F pour sortir de prison");
                player.solde -= 5000;
                player.GetOutOfPrison();

            } else
                System.out.println("Il faudra retenter le coup au prochain tour");

        }

    }

    @Override
    public void playTurn(Player player, List<AbstractCase> terrains, int roll) {

    }
}
