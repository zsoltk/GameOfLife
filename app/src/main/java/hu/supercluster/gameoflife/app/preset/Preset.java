package hu.supercluster.gameoflife.app.preset;

import hu.supercluster.gameoflife.game.rule.NeighborCountBasedRule;

public class Preset {
    private String name;
    private NeighborCountBasedRule rule;

    public Preset(String name, String rule) {
        this.name = name;
        this.rule = rule == null ? null : new NeighborCountBasedRule(rule);
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
