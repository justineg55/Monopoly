package monop.player;

import monop.NamedElement;
import monop.TourStrategy.AbstractTurnStrategy;
import monop.TourStrategy.NormalTurnStrategy;
import monop.TourStrategy.PrisonTurnStrategy;
import monop.TourStrategy.TurnAfterGetOutOfPrisonByRollingDouble;
import monop.dice.Dice;
import monop.effect.GetOutOfPrisonEffect;
import monop.plateau.AbstractCase;
import monop.plateau.Terrain;

import java.util.ArrayList;
import java.util.List;

public class Player extends NamedElement {
    public int solde;
    public int position;
    public List <Dice> dice;
    private List<Terrain> terrains;
    public int nbDoubles;
    private int nbDiceRolls;
    public int roll;
    public GetOutOfPrisonEffect getOutPrisonCard;
    public AbstractTurnStrategy turn;
    public AbstractTurnStrategy normalTurnStrategy;
    public AbstractTurnStrategy prisonTurnStrategy;
    public AbstractTurnStrategy turnAfterGetOutOfPrisonByRollingDouble;
    public boolean isDouble;
    public boolean isRolled;

    public Player(String name) {
        super(name);
        dice=new ArrayList<>();
        terrains=new ArrayList<>();
        position=0;
        nbDoubles=0;
        nbDiceRolls=0;
        getOutPrisonCard=null;
        normalTurnStrategy=new NormalTurnStrategy();
        prisonTurnStrategy=new PrisonTurnStrategy();
        turnAfterGetOutOfPrisonByRollingDouble=new TurnAfterGetOutOfPrisonByRollingDouble();
        isDouble=false;
        isRolled=false;
    }

    public int rollDice(){
        nbDiceRolls++;
        isRolled=true;
        int valueDice1=dice.get(0).rollDice();
        int valueDice2=dice.get(1).rollDice();

        nbDoubles=valueDice1==valueDice2?nbDoubles+1:0;
        if(nbDoubles>=1){
            isDouble=true;
        }

        roll=valueDice1+valueDice2;
        return roll;
    }

    public boolean isRuined(){
//        return (terrains.size()==0 && solde<=0);
        //le joueur est ruiné quand son solde passe en-dessous de 0
        return (solde<0);
    }

    public void Play(List<AbstractCase> plateau){
        turn.playTurn(this,plateau);
    }

    public void Play(List<AbstractCase> plateau, int roll){
        turn.playTurn(this,plateau,roll);
    }

    public void GoToPrison(){
        position=10;
        nbDoubles=0;
        isDouble=false;
        nbDiceRolls=0;
        turn= prisonTurnStrategy;
    }

    public void GetOutOfPrison(){
        turn=normalTurnStrategy;
//        nbDoubles=0;
        nbDiceRolls=0;
    }

    public void GetOutOfPrisonByRollingDouble(){
        turn=turnAfterGetOutOfPrisonByRollingDouble;
        nbDoubles=0;
        nbDiceRolls=0;

    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
    }


    public int getNbDiceRolls() {
        return nbDiceRolls;
    }

    public void setNbDiceRolls(int nbDiceRolls) {
        this.nbDiceRolls = nbDiceRolls;
    }

}
