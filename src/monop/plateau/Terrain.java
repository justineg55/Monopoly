package monop.plateau;

import monop.effect.AbstractEffect;
import monop.effect.PayEffect;
import monop.player.Player;

public class Terrain extends AbstractCase {
    private int cost;
    public Player proprio;
    public int baseRent;
    public String type;

    public Terrain(String name, int cost, int baseRent, String type) {
        super(name, new PayEffect());
        this.cost = cost;
        this.baseRent = baseRent;
        this.type = type;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Player getProprio() {
        return proprio;
    }

    public void setProprio(Player proprio) {
        this.proprio = proprio;
    }

    public void payRent(Player player) {
        if (proprio != null) {
            if (type.equals("gares")) {
                int nbGares = 0;
                for (Terrain terrain : proprio.getTerrains()) {
                    if (terrain.type.equals("gares"))
                        nbGares++;
                }
                payRentGare(player, nbGares);

            } else if (type.equals("compagnies")) {
                int nbCompagnies = 0;
                for (Terrain terrain : proprio.getTerrains()) {
                    if (terrain.type.equals("compagnies"))
                        nbCompagnies++;
                }
                payRentCompagnie(player, nbCompagnies);
            }
        }
    }

    public void payRentGare(Player player, int nbGares) {
        int value = 0;
        switch (nbGares) {
            case 1:
                value = baseRent;
                break;
            case 2:
                value = 5000;
                break;
            case 3:
                value = 10000;
                break;
            case 4:
                value = 20000;
                break;
        }
        System.out.println(player + " doit payer " + value + " F à " + proprio);

        //on vérifie que le joueur a assez d'argent pour payer le loyer
        if(player.solde>=value){
            effect.Do(player, value);
            effect.Do(proprio, -value);
//      s'il a pas assez d'argent pour payer le loyer : il restitue l'argent qu'il lui reste au proprio et il est ruiné et a perdu
        } else{
            System.out.println("Malheureusement, Vous n'avez pas assez d'argent pour payer le loyer");
            System.out.println("Vous êtes ruiné");

            //on restitue l'argent restant du joueur au proprio
            effect.Do(proprio,-player.solde);
            //on fait payer le loyer au joueur pour qu'il soit ruiné : la somme restante a été transférée au proprio
            effect.Do(player,value);
            player.isRuined();
        }
    }

    public void payRentCompagnie(Player player, int nbCompagnies) {
        int value = 0;
        value = (nbCompagnies == 1) ? player.roll * 400 : player.roll * 1000;

        System.out.println(player + " doit payer " + value + " F à " + proprio);

        //on vérifie que le joueur a assez d'argent pour payer le loyer
        if(player.solde>=value){
            effect.Do(player, value);
            effect.Do(proprio, -value);

            // s'il a pas assez d'argent pour payer le loyer : il restitue l'argent qu'il lui reste au proprio et il est ruiné et a perdu
        } else{
            System.out.println("Malheureusement, Vous n'avez pas assez d'argent pour payer le loyer");
            System.out.println("Vous êtes ruiné");

            //on restitue l'argent restant du joueur au proprio
            effect.Do(proprio,-player.solde);
            //on enleve l'argent restant du joueur pour le faire tomber à 0 : la somme a été transférée au proprio
            effect.Do(player,value);
            player.isRuined();
        }
    }

    public void buy(Player player) {
        //on vérifie que le joueur a assez d'argent pour acheter le terrain
        if(cost<=player.solde){
            effect.Do(player, cost);
            //le joueur devient propriétaire du terrain
            proprio = player;
            //on ajoute le terrain acheté qui est this à la liste des terrains du joueur : méthode buy appelé avec le terrain à acheter
            player.getTerrains().add(this);
            System.out.println("Félicitations, vous venez d'acquérir "+ this.getName());
        }
        else{
            System.out.println("Malheureusement, Vous n'avez pas assez d'argent pour acheter ce terrain");
        }

    }

}
