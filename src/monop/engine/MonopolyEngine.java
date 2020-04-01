package monop.engine;

import monop.TourStrategy.NormalTurnStrategy;
import monop.TourStrategy.PrisonTurnStrategy;
import monop.card.Card;
import monop.card.CardFactory;
import monop.dice.Dice;
import monop.plateau.AbstractCase;
import monop.plateau.CaseFactory;
import monop.plateau.Terrain;
import monop.player.Player;
import monop.utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MonopolyEngine {
    private List<Player> players;
    private List<Dice> dices;
    private List<Card> chanceCards;
    private List<Card> ccCards;
    private List<AbstractCase> plateau;
    private File fichierCards;
    private File fichierCases;
    private CardFactory cardFactory;
    private CaseFactory caseFactory;
    private NormalTurnStrategy normalTurnStrategy;
    private PrisonTurnStrategy prisonTurnStrategy;

    public MonopolyEngine() {
        players = new ArrayList<>();
        dices = new ArrayList<>();
        chanceCards = new ArrayList<>();
        ccCards = new ArrayList<>();
        plateau = new ArrayList<>();
        cardFactory = new CardFactory();
        normalTurnStrategy = new NormalTurnStrategy();
        prisonTurnStrategy = new PrisonTurnStrategy();
    }

    public void init() {
        fichierCards = new File("cards.txt");
        fichierCases = new File("plateau.txt");
        chanceCards = loadChanceCards();
        ccCards = loadCcCards();

        caseFactory = new CaseFactory(chanceCards, ccCards);

        plateau = loadCases();

        dices.add(new Dice(6));
        dices.add(new Dice(6));

        System.out.println("Debut du jeu");
//        Scanner scan = new Scanner(System.in);
        int nbPlayers;
        do {
//            System.out.println("A combien voulez-vous jouer ? ");
//            nbPlayers = scan.nextInt();
            nbPlayers = Utils.getInt("A combien voulez-vous jouer ? ");
        } while (nbPlayers < 2 || nbPlayers > 8);

//        scan.nextLine();

        for (int i = 1; i <= nbPlayers; i++) {
//            System.out.println("Tapez le nom du joueur");
//            String playerName = scan.nextLine();
            String playerName=Utils.getString("Tapez le nom du joueur");
            players.add(new Player(playerName));
            players.get(i - 1).dice = dices;
            players.get(i - 1).solde = 150000;
            players.get(i - 1).turn = normalTurnStrategy;
//            players.get(i-1).position=34;
        }

    }

    public void run() {
//        Scanner scan=new Scanner(System.in);
        int activePlayer = 0;
        //tant qu'il y a encore au moins 2 joueurs en jeu, on continue de jouer, on enlève de la liste les joueurs éliminés
        while (players.size() > 1) {
            while (activePlayer < players.size()) {
                System.out.println("\n"+ "Au tour du joueur " + players.get(activePlayer).getName());
                Utils.waitEnter("Appuyez sur Entrée quand vous êtes prêt à jouer, "+players.get(activePlayer).getName());
//                System.out.println("Appuyez sur Entrée pour continuer");
//                scan.nextLine();

                // il joue son tour
                players.get(activePlayer).Play(plateau);
                while (players.get(activePlayer).isDouble){
                    if (players.get(activePlayer).isRuined()) {
                        break;
                    }
                    System.out.println("Tu as fait un double, c'est reparti pour un tour");
                    players.get(activePlayer).isDouble=false;
                    players.get(activePlayer).Play(plateau);
                }

                if (players.get(activePlayer).isRuined()) {
                    System.out.println(players.get(activePlayer).getName() + " est ruiné");

                    removePlayer(players.get(activePlayer));
                    //si il ne reste plus qu'un joueur en jeu, on le fait gagner directement
                    if(players.size()==1){
//                        break;
                    }
                } else {
                    //on passe au joueur suivant
                    System.out.println("Fin du tour du joueur "+ players.get(activePlayer).getName());
                    activePlayer += 1;

                }
                //on recommence un tour entier car tous les joueurs ont joué
//                activePlayer=0;
            }
            activePlayer = 0;
        }
        System.out.println(players.get(0).getName() + " a gagné");
    }

    public List<Card> loadChanceCards() {
        return loadCardsFromRange(0, 14);
    }

    public List<Card> loadCcCards() {
        return loadCardsFromRange(14, 30);
    }

    public List<Card> loadCardsFromRange(int start, int end) {

        //on crée une liste où on souhaite ajouter chaque ligne de notre fichier de cartes pour l'envoyer dans le cardfactory
        List<Card> listeParLigneDeCartes = new ArrayList<>();
        List<Card> listeFinale=new ArrayList<>();
        String csvFile = "cards.txt";
        BufferedReader br = null;
        String line = "";

        try {

            br = new BufferedReader(new FileReader(new File(csvFile)));
            //on lit notre fichier de texte ligne par ligne tant qu'il y en a encore
            while ((line = br.readLine()) != null) {
                //on souhaite appeler la méthode create dans caseFactory qui prend une ligne de notre fichier en parametre
                //la méthode create renvoie un type objet donc on doit caster en type objet Card
                //notre liste de cartes prend des objets de type carte dans sa liste donc on caste en type Card
                Card card = (Card) cardFactory.create(line);

                //on ajoute l'objet de type carte qui correspond à chaque ligne du fichier dans la liste de cartes
                listeParLigneDeCartes.add(card);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            for(int i=start;i<end;i++){
//                listeFinale.add(listeParLigneDeCartes.get(i));
//            }
//            return listeFinale;

            //pour les tests avec une carte spécifique
        Card card=(Card)cardFactory.create("30;CC;Vous êtes libéré de prison. Cette carte peut être conservée jusqu'a ce qu'elle soit utilisée ou vendue;bonus;0;");
        listeFinale.add(card);
        return listeFinale;
        }

    }

    public List<AbstractCase> loadCases() {
        List<AbstractCase> listeCasesDuPlateau = new ArrayList<>();
        String csvFile = "plateau.txt";
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                AbstractCase box=(AbstractCase) caseFactory.create(line);
                listeCasesDuPlateau.add(box);
            }

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return listeCasesDuPlateau;

    }

    public void removePlayer(Player player){
        for(AbstractCase element:plateau){
            if (player.getTerrains().contains(element)) {
                ((Terrain) element).setProprio(null);
            }
        }
        players.remove(player);
    }
}
