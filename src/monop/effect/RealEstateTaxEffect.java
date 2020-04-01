package monop.effect;

import monop.plateau.BuildableTerrain;
import monop.plateau.Terrain;
import monop.player.Player;

public class RealEstateTaxEffect extends AbstractEffect {

    public RealEstateTaxEffect() {
    }

    public void Do(Player player, int value) {
        int nbHousesPerPlayer = 0;
        int nbHotelsPerPlayer = 0;

        for (Terrain t : player.getTerrains()) {
            if (t.getClass() == BuildableTerrain.class) {
                nbHousesPerPlayer += ((BuildableTerrain) t).houses.size();

                if (((BuildableTerrain) t).isHotelBuilt) {
                    nbHotelsPerPlayer++;
                }
            }

        }
        System.out.println("Vous avez "+nbHousesPerPlayer + " maisons et "+ nbHotelsPerPlayer + " hôtels");
        //les frais pour un hotel sont 2 fois plus chers que pour une maison
        int frais=nbHousesPerPlayer*value + nbHotelsPerPlayer*value*2;
        player.solde-=frais;
        System.out.println("Vous devez payer "+ frais + " F");

    }
}
