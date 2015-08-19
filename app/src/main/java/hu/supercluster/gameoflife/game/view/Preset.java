package hu.supercluster.gameoflife.game.view;

import hu.supercluster.gameoflife.game.rule.NeighborCountBasedRule;

public class Preset {
    private String name;
    private NeighborCountBasedRule rule;

    public Preset(String name, NeighborCountBasedRule rule) {
        this.name = name;
        this.rule = rule;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public NeighborCountBasedRule getRule() {
        return rule;
    }
}
