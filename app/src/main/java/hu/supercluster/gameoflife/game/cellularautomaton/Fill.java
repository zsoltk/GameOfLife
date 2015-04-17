package hu.supercluster.gameoflife.game.cellularautomaton;

public class Fill {
    private final float percentage;
    private final int state;

    public Fill(float percentage, int state) {
        this.percentage = percentage;
        this.state = state;
    }

    public float getPercentage() {
        return percentage;
    }

    public int getState() {
        return state;
    }
}
