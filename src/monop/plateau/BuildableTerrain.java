package monop.plateau;

import monop.buildings.AbstractHouse;
import monop.buildings.Hotel;
import monop.buildings.House;
import monop.effect.AbstractEffect;
import monop.effect.PayEffect;
import monop.player.Player;

import java.util.*;

public class BuildableTerrain extends Terrain {
    public ArrayList<AbstractHouse> houses;
    public boolean isHotelBuilt;
    public int oneHouseRent;
    public int twoHouseRent;
    public int threeHouseRent;
    public int fourHouseRent;
    public int hotelRent;
    public int houseCost;


    public BuildableTerrain(String name, String type, int cost, int houseCost, int baseRent, int oneHouseRent, int twoHouseRent, int threeHouseRent, int fourHouseRent, int hotelRent) {
        super(name, cost, baseRent, type);
        houses = new ArrayList<>();
        this.oneHouseRent = oneHouseRent;
        this.twoHouseRent = twoHouseRent;
        this.threeHouseRent = threeHouseRent;
        this.fourHouseRent = fourHouseRent;
        this.hotelRent = hotelRent;
        this.houseCost = houseCost;
    }

    public void BuildHouse(Player player) {
        //on vérifie que le joueur a assez d'argent pour acheter une maison
        if(houseCost<=player.solde){
            if (!isHotelBuilt) {
                if (houses.size() == 4) {
                    isHotelBuilt = true;
                    houses.clear();
                    System.out.println("Vous avez construit un hôtel sur "+ this.getName());
                } else
                    houses.add(new House());

                System.out.println("Vous avez construit une maison sur "+ this.getName());

                effect.Do(player, houseCost);
            }
        }
        else{
            System.out.println("Malheureusement, vous n'avez pas assez d'argent pour construire");
        }

    }

    public void payRent(Player player) {
        if (proprio != null) {
            int houseCount = 0;

            for (AbstractHouse h : houses) {
                if (h.getClass() == House.class)
                    houseCount++;
                else if (h.getClass() == Hotel.class)
                    houseCount = 5;
            }
            int value = 0;
            switch (houseCount) {
                case 0:
                    //on récupère le type de loyer à payer donc le coût du loyer en fonction de s'il y a des maisons ou pas
                    value = baseRent;
                    break;
                case 1:
                    value = oneHouseRent;
                    break;
                case 2:
                    value = twoHouseRent;
                    break;
                case 3:
                    value = threeHouseRent;
                    break;
                case 4:
                    value = fourHouseRent;
                    break;
                case 5:
                    value = hotelRent;
                    break;
            }
            System.out.println(player + " doit payer " + value + " F à " + proprio);

            //on vérifie que le joueur a assez d'argent pour payer le loyer
            if(player.solde>=value){
                //le player paie le prix du loyer
                effect.Do(player, value);
                //le proprio recoit le prix du loyer
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


    }
}
