package monop.buildings;

public abstract class AbstractHouse {
    private int cost;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public AbstractHouse(int cost) {
        this.cost = cost;
    }
}
