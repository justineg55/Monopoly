package monop.effect;

import monop.player.Player;

public class AvancerCheckPassageDepart extends AbstractEffect {

    public AvancerCheckPassageDepart() {
    }

    @Override
    public void Do(Player player, int value) {
        //on compare la position du joueur par rapport au numéro de la case sur laquelle il doit se déplacer
        //si sa position est supérieure à la case où il doit se rendre, cela veut dire qu'il passera forcément par la case départ
        if(player.position>value){
            player.solde+=20000;
            System.out.println("Vous passez par la case Départ, vous recevez 20 000 F");
        }
        //on n'oublie pas de le téléporter sur la case où il doit se rendre
        player.position=value;

    }
}
